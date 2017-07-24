package by.epam.movierating.dao.connectionpool;


import by.epam.movierating.dao.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Stores a limit number of connections
 */
public class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();

    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;
    private final static Lock lock = new ReentrantLock();

    private volatile boolean isInit = false;

    private ConnectionPool() {
    }

    /**
     * Returns an instance of ConnectionPool
     * @return {@link ConnectionPool} object
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Creates a limit number of connections
     * @throws ConnectionPoolException
     */
    public void init() throws ConnectionPoolException {
        try {
            lock.lock();
            if (!isInit) {
                DBResourceManager dbResourceManager = DBResourceManager.getInstance();
                int poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
                availableConnections = new ArrayBlockingQueue<Connection>(poolSize);
                usedConnections = new ArrayBlockingQueue<Connection>(poolSize);
                try {
                    Class.forName(dbResourceManager.getValue(DBParameter.DB_DRIVER));
                    for (int i = 0; i < poolSize; i++) {
                        Connection connection = DriverManager.getConnection(
                                dbResourceManager.getValue(DBParameter.DB_URL),
                                dbResourceManager.getValue(DBParameter.DB_USER),
                                dbResourceManager.getValue(DBParameter.DB_PASSWORD)
                        );
                        availableConnections.add(connection);
                    }
                    isInit = true;

                } catch (ClassNotFoundException e) {
                    throw new ConnectionPoolException("Database driver is not found", e);
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Can not get a connection", e);
                }
            } else {
                throw new ConnectionPoolException("Try to initialize already initialized pool");
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gives free connection
     * @return {@link Connection} object
     * @throws ConnectionPoolException
     */
    public Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = availableConnections.take();
            usedConnections.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Can not take available connection", e);
        }
    }

    /**
     * Takes the connection from used connections collection
     * and put is to available connection collection
     * @param connection {@link Connection} object to free
     * @throws ConnectionPoolException
     */
    public void freeConnection(Connection connection) throws ConnectionPoolException {
        if (usedConnections.contains(connection)) {
            usedConnections.remove(connection);
            try {
                availableConnections.put(connection);
            } catch (InterruptedException e) {
                throw new ConnectionPoolException("Can not free a connection", e);
            }
        } else {
            throw new ConnectionPoolException("Try to free not used connection");
        }
    }

    /**
     * Close all stored connections
     * @throws ConnectionPoolException
     */
    public void dispose() throws ConnectionPoolException {
        try {
            lock.lock();
            if (isInit) {
                try {
                    for (Connection connection : availableConnections) {
                        connection.close();
                    }
                    for (Connection connection : usedConnections) {
                        connection.close();
                    }
                    isInit = false;
                } catch (SQLException e) {
                    throw new ConnectionPoolException("Can not close connections", e);
                }
            } else {
                throw new ConnectionPoolException("Try to dispose not initialized pool");
            }
        } finally {
            lock.unlock();
        }
    }

}

