package by.epam.movierating.dao.impl;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.dao.ActorDAO;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.util.DAOUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ActorDAO} implementation, provided DAO-logic for {@link Actor} entity
 */
public class ActorDAOImpl implements ActorDAO {
    private static final String SQL_GET_ACTORS_BY_ID_MOVIE = "SELECT actor.id_actor, actor.first_name_, actor.last_name_ ," +
            "actor.image_path " +
            "FROM actor " +
            "INNER JOIN movie_actor ON actor.id_actor=movie_actor.id_actor " +
            "WHERE actor.is_deleted=0 and movie_actor.id_movie=? " +
            "GROUP BY actor.id_actor";
    private static final String SQL_GET_ACTORS_NOT_IN_MOVIE = "SELECT actor.id_actor, actor.first_name_ , actor.last_name_," +
            "actor.image_path " +
            "FROM actor " +
            "WHERE actor.is_deleted=0 and actor.id_actor NOT IN " +
            "( SELECT movie_actor.id_actor FROM movie_actor WHERE movie_actor.id_actor=? ) " +
            "GROUP BY actor.id_actor";
    private static final String SQL_GET_ALL_ACTORS = "SELECT actor.id_actor, actor.first_name_, actor.last_name_, actor.image_path " +
            "FROM actor " +
            "WHERE actor.is_deleted=0";
    private static final String SQL_GET_ACTOR_BY_ID = "SELECT actor.id_actor, actor.first_name_, actor.last_name_, actor.image_path " +
            "FROM actor " +
            "WHERE actor.is_deleted=0 and actor.id_actor=?";
    private static final String SQL_GET_ALL_LIMITED_ACTORS = "SELECT actor.id_actor, actor.first_name_, actor.last_name_, actor.image_path " +
            "FROM actor " +
            "WHERE actor.is_deleted=0 " +
            "LIMIT ?,?";
    private static final String SQL_DELETE_ACTOR = "UPDATE actor SET is_deleted=1 WHERE id_actor=?";
    private static final String SQL_EDIT_ACTOR = "UPDATE actor SET first_name_ =?, last_name_=? WHERE id_actor=?";
    private static final String SQL_UPDATE_ACTOR_IMAGE = "UPDATE actor SET image_path =? WHERE id_actor=?";
    private static final String SQL_ADD_ACTOR = "INSERT INTO actor (first_name_ru,last_name_ru,first_name_en, last_name_en) VALUES (?,?,?,?)";
    private static final int LIMIT_ACTOR_COUNT = 4;

    /**
     * Returns {@link List} of actors which has reference to the required movie id
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    @Override
    public List<Actor> getActorsByMovieId(int idMovie, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_ACTORS_BY_ID_MOVIE, language));
            preparedStatement.setInt(1, idMovie);
            resultSet = preparedStatement.executeQuery();
            return setActorInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Returns {@link List} of {@link Actor} objects
     * which do not have reference to the concrete movie
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    @Override
    public List<Actor> getActorsNotInMovie(int idMovie, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_ACTORS_NOT_IN_MOVIE, language));
            preparedStatement.setInt(1, idMovie);
            resultSet = preparedStatement.executeQuery();
            return setActorInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Returns all actors by given language
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    @Override
    public List<Actor> getAllActors(String language) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_ALL_ACTORS, language));
            resultSet = preparedStatement.executeQuery();
            return setActorInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Deletes an actor from the data storage
     * @param idActor actor id that has to be deleted
     * @return {@code true} if actor was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteActor(int idActor) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_ACTOR);
            preparedStatement.setInt(1, idActor);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement);
        }
    }

    /**
     * Updates the actor in data storage
     * @param idActor actor id which has to be updated
     * @param firstName a new first name of an actor
     * @param lastName a new last name of an actor
     * @param language a language of data
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean editActor(int idActor, String firstName,
                             String lastName, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_EDIT_ACTOR, language));
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, idActor);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement);
        }
    }

    /**
     * Returns actor which matches by its id
     * @param idActor an id of this actor to search
     * @param language specify concrete one for search
     * @return {@link Actor} object
     * @throws DAOException
     */
    @Override
    public Actor getActorById(int idActor, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_ACTOR_BY_ID, language));
            preparedStatement.setInt(1, idActor);
            resultSet = preparedStatement.executeQuery();
            List<Actor> actorList=setActorInfo(resultSet);
            return actorList.get(0);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement,resultSet);
        }
    }

    /**
     * Sets a new image path to the {@link Actor} object
     * @param idActor actor id which has to be updated
     * @param filePath a new file path to an image
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean uploadActorImage(int idActor, String filePath)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ACTOR_IMAGE);
            preparedStatement.setString(1, filePath);
            preparedStatement.setInt(2, idActor);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows > 0;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement);
        }
    }

    /**
     * Creates a new actor in data storage
     * @param firstNameEn first name in English
     * @param lastNameEn last name in English
     * @param firstNameRu first name in Russian
     * @param lastNameRu last name in Russian
     * @return id of a new inserted actor in data storage
     * @throws DAOException
     */
    @Override
    public int addActor(String firstNameEn, String lastNameEn,
                        String firstNameRu, String lastNameRu)
            throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_ADD_ACTOR, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, firstNameRu);
            statement.setString(2, lastNameRu);
            statement.setString(3, firstNameEn);
            statement.setString(4, lastNameEn);
            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return (int) generatedKeys.getLong(1);
                }
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement);
        }
        return 0;
    }

    /**
     * Returns {@link List} of 4 {@link Actor} objects
     * required by current page number
     * @param language specify concrete data's language
     * @param currentPageNumber a number of current page with actors
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    @Override
    public List<Actor> getAllLimitedActors(String language, int currentPageNumber)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_ALL_LIMITED_ACTORS, language));
            preparedStatement.setInt(1, LIMIT_ACTOR_COUNT * (currentPageNumber - 1));
            preparedStatement.setInt(2, LIMIT_ACTOR_COUNT);
            resultSet = preparedStatement.executeQuery();
            return setActorInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Setups actors' information
     * @param resultSet {@link ResultSet} object contains actors' data
     * @return {@link List} of {@link Actor} objects
     * @throws SQLException
     */
    private List<Actor> setActorInfo(ResultSet resultSet) throws SQLException {
        List<Actor> actorList = new ArrayList<>();
        while (resultSet.next()) {
            Actor actor = new Actor();
            actor.setId(resultSet.getInt(1));
            actor.setFirstName(resultSet.getString(2));
            actor.setLastName(resultSet.getString(3));
            actor.setImagePath(resultSet.getString(4));
            actorList.add(actor);
        }
        return actorList;
    }
}
