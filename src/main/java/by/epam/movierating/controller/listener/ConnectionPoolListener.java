package by.epam.movierating.controller.listener;

import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener on a servlet context creation.
 * Initialize and destroys the Connection Pool.
 */
public class ConnectionPoolListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Can not init a pool", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.dispose();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Can not dispose a pool", e);
        }
    }
}
