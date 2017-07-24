package by.epam.movierating.dao.impl;

import by.epam.movierating.bean.Genre;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.dao.GenreDAO;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.util.DAOUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link GenreDAO} implementation, provided DAO-logic for {@link Genre} entity
 */
public class GenreDAOImpl implements GenreDAO {
    private static final String SQL_GET_ALL_GENRES = "SELECT id_genre,name_ FROM genre WHERE is_deleted=0";
    private static final String SQL_GET_GENRE_BY_ID = "SELECT id_genre,name_ FROM genre WHERE is_deleted=0 and id_genre=?";
    private static final String SQL_GET_ALL_ACTIVE_GENRES = "SELECT genre.id_genre, genre.name_ FROM genre " +
            "INNER JOIN movie_genre ON genre.id_genre=movie_genre.id_genre " +
            "WHERE genre.is_deleted=0 " +
            "GROUP BY genre.id_genre";
    private static final String SQL_GET_GENRES_BY_ID_MOVIE = "SELECT genre.id_genre, genre.name_ FROM genre " +
            "INNER JOIN movie_genre ON genre.id_genre=movie_genre.id_genre " +
            "WHERE genre.is_deleted=0 and movie_genre.id_movie=? " +
            "GROUP BY genre.id_genre";
    private static final String SQL_DELETE_GENRE = "UPDATE genre SET is_deleted=1 WHERE id_genre=?";
    private static final String SQL_UPDATE_GENRE = "UPDATE genre SET name_=? WHERE id_genre=?";
    private static final String SQL_GET_GENRES_NOT_IN_MOVIE = "SELECT genre.id_genre, genre.name_ FROM genre " +
            "WHERE genre.is_deleted=0 and genre.id_genre NOT IN " +
            "( SELECT movie_genre.id_genre FROM movie_genre WHERE movie_genre.id_movie=? ) " +
            "GROUP BY genre.id_genre";
    private static final String SQL_GET_GENRE_STATISTICS = "SELECT genre.name_ , count(movie_genre.id_movie) " +
            "FROM movie_genre " +
            "INNER JOIN genre ON movie_genre.id_genre=genre.id_genre " +
            "GROUP BY movie_genre.id_genre";
    private static final String SQL_ADD_GENRE = "INSERT INTO genre (name_ru,name_en) VALUES (?,?)";

    /**
     * Returns all genres
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    @Override
    public List<Genre> getAllGenres(String language) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Genre> genreList;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_ALL_GENRES, language));
            genreList = setGenreInfo(resultSet);
            return genreList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection ", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement, resultSet);
        }
    }

    /**
     * Returns genres that are in use
     * (there are relations between genres and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    @Override
    public List<Genre> getAllActiveGenres(String language) throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Genre> genreList;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_ALL_ACTIVE_GENRES, language));
            genreList = setGenreInfo(resultSet);
            return genreList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement, resultSet);
        }
    }

    /**
     * Deletes a genre from the data storage
     * @param idGenre genre id that has to be deleted
     * @return {@code true} if genre was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteGenre(int idGenre) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_GENRE);
            preparedStatement.setInt(1, idGenre);
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
     * Updates the genre in data storage
     * @param idGenre genre id which has to be updated
     * @param name a new name of a genre
     * @param language a language of data
     * @return {@code true} if genre was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean editGenre(int idGenre, String name, String language) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_UPDATE_GENRE, language));
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, idGenre);
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
     * Returns {@link List} of genres which has reference
     * to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    @Override
    public List<Genre> getGenresByIdMovie(int idMovie, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_GENRES_BY_ID_MOVIE, language));
            preparedStatement.setInt(1, idMovie);
            resultSet = preparedStatement.executeQuery();
            return setGenreInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Returns {@link List} of genres which has not
     * reference to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    @Override
    public List<Genre> getGenresNotInMovie(int idMovie, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_GENRES_NOT_IN_MOVIE, language));
            preparedStatement.setInt(1, idMovie);
            resultSet = preparedStatement.executeQuery();
            return setGenreInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Returns a distribution between genres
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws DAOException
     */
    @Override
    public List<StaticticsDTO> getGenreStatistics(String language)
            throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<StaticticsDTO> staticticsDTOList = new ArrayList<>();
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_GENRE_STATISTICS, language));
            while (resultSet.next()) {
                StaticticsDTO staticticsDTO = new StaticticsDTO();
                staticticsDTO.setLabel(resultSet.getString(1));
                staticticsDTO.setValue(resultSet.getInt(2));
                staticticsDTOList.add(staticticsDTO);
            }
            return staticticsDTOList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, statement, resultSet);
        }
    }

    /**
     * Creates a new genre in data storage
     * @param nameRu a name of new genre in Russian
     * @param nameEn a name of new genre in English
     * @return id of a new inserted genre in data storage
     * @throws DAOException
     */
    @Override
    public int addGenre(String nameRu, String nameEn) throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_ADD_GENRE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, nameRu);
            statement.setString(2, nameEn);
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
     * Returns genre object by its id in data storage
     * @param idGenre an id of the genre for search
     * @param language a language for data selection
     * @return {@link Genre} object
     * @throws DAOException
     */
    @Override
    public Genre getGenreById(int idGenre, String language)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_GENRE_BY_ID, language));
            preparedStatement.setInt(1, idGenre);
            resultSet = preparedStatement.executeQuery();
            return setGenreInfo(resultSet).get(0);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Setups genres' information
     * @param resultSet {@link ResultSet} object contains genres' data
     * @return {@link List} of {@link Genre} objects
     * @throws SQLException
     */
    private List<Genre> setGenreInfo(ResultSet resultSet) throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        while (resultSet.next()) {
            Genre genre = new Genre();
            genre.setId(resultSet.getInt(1));
            genre.setName(resultSet.getString(2));
            genreList.add(genre);
        }
        return genreList;
    }

}
