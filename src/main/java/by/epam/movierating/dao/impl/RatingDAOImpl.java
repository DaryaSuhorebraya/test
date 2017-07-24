package by.epam.movierating.dao.impl;

import by.epam.movierating.bean.Rating;
import by.epam.movierating.dao.RatingDAO;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.util.DAOUtil;

import java.sql.*;

/**
 * {@link RatingDAO} implementation, provided DAO-logic for {@link Rating} entity
 */
public class RatingDAOImpl implements RatingDAO {
    private static final String SQL_RATE_MOVIE = "INSERT INTO rating (id_movie, id_user, mark) VALUES(?,?,?)";
    private static final String SQL_CHECK_RATE_OPPORTUNITY = "SELECT * FROM rating WHERE id_movie=? and id_user=?";
    private static final String SQL_DELETE_RATING="DELETE FROM rating WHERE id_movie=? and id_user=?";

    /**
     * Creates a new rating in data storage
     * @param idMovie an id of the movie that has to be rated
     * @param idUser an id of the user that has rated the movie
     * @param mark a mark of rating
     * @return {@code true} new rating was created
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean rateMovie(int idMovie, int idUser, int mark)
            throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_RATE_MOVIE);
            statement.setInt(1, idMovie);
            statement.setInt(2, idUser);
            statement.setInt(3, mark);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement);
        }
    }

    /**
     * Defines rate opportunity of a user on a concrete movie
     * @param idMovie an id of a movie that has to be checked
     * @param idUser an id of a user that has to be checked
     * @return {@code true} if user has not such an opportunity
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean checkRateOpportunity(int idMovie, int idUser)
            throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_CHECK_RATE_OPPORTUNITY);
            statement.setInt(1, idMovie);
            statement.setInt(2, idUser);
            resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement, resultSet);
        }
    }

    /**
     * Returns a rating of a movie by user id
     * @param idMovie an id of a movie
     * @param idUser an id of a movie
     * @return {@link Rating} object
     * @throws DAOException
     */
    @Override
    public Rating getRatingOnMovieByUserId(int idMovie, int idUser)
            throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Rating rating = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_CHECK_RATE_OPPORTUNITY);
            statement.setInt(1, idMovie);
            statement.setInt(2, idUser);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rating = new Rating();
                rating.setMark(resultSet.getInt("mark"));
            }
            return rating;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement, resultSet);
        }
    }

    /**
     * Deletes rating in data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an id of a user who owns a rating
     * @return {@code true} if a rating was deleted from a data storage
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteRating(int idMovie, int idUser)
            throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_RATING);
            statement.setInt(1, idMovie);
            statement.setInt(2, idUser);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement);
        }
    }
}
