package by.epam.movierating.dao.impl;

import by.epam.movierating.bean.Movie;
import by.epam.movierating.dao.MovieDAO;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.util.DAOUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link MovieDAO} implementation, provided DAO-logic for {@link Movie} entity
 */
public class MovieDAOImpl implements MovieDAO {
    //in get all movies  changed inner to left
    private static final String SQL_GET_ALL_MOVIES= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie\n" +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "WHERE movie.is_deleted=0 " +
            "GROUP BY movie.id_movie";
    private static final String SQL_MOVIE_BY_ID= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie\n" +
            "INNER JOIN rating ON movie.id_movie=rating.id_movie " +
            "WHERE movie.id_movie=?";
    private static final String SQL_GET_MOVIE_BY_TITLE_EN= "SELECT movie.id_movie, movie.title_en, " +
            "movie.release_year, movie.description_en, avg(rating.mark) as avg_mark, movie.poster_path FROM movie\n" +
            "INNER JOIN rating ON movie.id_movie=rating.id_movie " +
            "WHERE movie.title_en=?";
    private static final String SQL_GET_MOVIES_BY_GENRE= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie\n" +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "INNER JOIN movie_genre ON movie.id_movie=movie_genre.id_movie "+
            "WHERE movie_genre.id_genre=? AND movie.is_deleted=0 "+
            " GROUP BY movie.id_movie";
    private static final String SQL_GET_MOVIES_BY_COUNTRY= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie\n" +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "INNER JOIN movie_country ON movie.id_movie=movie_country.id_movie "+
            "WHERE movie_country.country_code=? AND movie.is_deleted=0 "+
            " GROUP BY movie.id_movie";
    private static final String SQL_DELETE_MOVIE= "UPDATE movie SET is_deleted=1 WHERE id_movie=?";
    private static final String SQL_UPDATE_MOVIE_FIELD= "UPDATE movie SET @=? WHERE id_movie=?";
    private static final String RELEASE_YEAR="release_year";
    private static final String SQL_DELETE_GENRE_FOR_MOVIE="DELETE movie_genre FROM movie_genre " +
            "INNER JOIN genre ON movie_genre.id_genre=genre.id_genre " +
            "WHERE movie_genre.id_movie=? AND genre.name_ =?";
    private static final String SQL_ADD_GENRE_FOR_MOVIE="INSERT INTO movie_genre (id_movie, id_genre) " +
            "SELECT ? ,genre.id_genre FROM genre " +
            "WHERE genre.name_=?";
    private static final String SQL_GET_MOVIES_BY_GENRE_NAME= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie " +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "INNER JOIN movie_genre ON movie.id_movie=movie_genre.id_movie "+
            "INNER JOIN genre ON movie_genre.id_genre=genre.id_genre "+
            "WHERE genre.name_ =? AND movie.is_deleted=0 "+
            " GROUP BY movie.id_movie";
    private static final String SQL_GET_MOVIES_BY_COUNTRY_NAME= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie " +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "INNER JOIN movie_country ON movie.id_movie=movie_country.id_movie "+
            "INNER JOIN country ON movie_country.country_code=country.code "+
            "WHERE country.name_ =? AND movie.is_deleted=0 "+
            " GROUP BY movie.id_movie";
    private static final String SQL_GET_MOVIES_BY_ACTOR_INITIAL="SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie " +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "INNER JOIN movie_actor ON movie.id_movie=movie_actor.id_movie "+
            "INNER JOIN actor ON movie_actor.id_actor=actor.id_actor "+
            "WHERE actor.first_name_ =? AND actor.last_name_ =? AND movie.is_deleted=0 "+
            " GROUP BY movie.id_movie";
    private static final String SQL_DELETE_COUNTRY_FOR_MOVIE="DELETE movie_country FROM movie_country " +
            "INNER JOIN country ON movie_country.country_code=country.code " +
            "WHERE movie_country.id_movie=? AND country.name_ =? ";
    private static final String SQL_ADD_COUNTRY_FOR_MOVIE="INSERT INTO movie_country (id_movie, country_code) " +
            "SELECT ? ,country.code FROM country " +
            "WHERE country.name_=?";
    private static final String SQL_DELETE_ACTOR_FOR_MOVIE="DELETE movie_actor FROM movie_actor " +
            "INNER JOIN actor ON movie_actor.id_actor=actor.id_actor " +
            "WHERE movie_actor.id_movie=? AND actor.first_name_ =? AND actor.last_name_ =?";
    private static final String SQL_ADD_ACTOR_FOR_MOVIE="INSERT INTO movie_actor (id_movie, id_actor) " +
            "SELECT ? ,id_actor FROM actor " +
            "WHERE actor.first_name_=? AND actor.last_name_=?";
    private static final String SQL_ADD_MOVIE="INSERT INTO movie (title_ru, title_en, release_year, description_ru, " +
            "description_en) VALUES (?,?,?,?,?)";
    private static final String SQL_GET_TOP_MOVIES= "SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie " +
            "INNER JOIN rating ON movie.id_movie=rating.id_movie " +
            "WHERE movie.is_deleted=0 " +
            "GROUP BY movie.id_movie "+
            "ORDER BY avg_mark DESC";
    private static final String SQL_GET_NEWEST_MOVIES="SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_ , avg(rating.mark) as avg_mark, movie.poster_path FROM movie " +
            "INNER JOIN rating ON movie.id_movie=rating.id_movie " +
            "WHERE movie.is_deleted=0 and movie.poster_path is not null " +
            "GROUP BY movie.id_movie " +
            "ORDER BY movie.release_year DESC";
    private static final String SQL_UPDATE_MOVIE_POSTER="UPDATE movie SET poster_path =? WHERE id_movie=?";
    private static final String SQL_GET_ALL_LIMITED_MOVIES="SELECT movie.id_movie, movie.title_, " +
            "movie.release_year, movie.description_, avg(rating.mark) as avg_mark, movie.poster_path FROM movie " +
            "LEFT JOIN rating ON movie.id_movie=rating.id_movie " +
            "WHERE movie.is_deleted=0 " +
            "GROUP BY movie.id_movie "+
            "LIMIT ?,?";
    private static final String MAIN_LIMIT_NEWEST_MOVIE=" LIMIT 4";
    private static final String LIMIT_NEWEST_MOVIE=" LIMIT 6";
    private static final int LIMIT_MOVIE_COUNT = 6;

    /**
     * Returns all movies
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getAllMovies(String language)
            throws DAOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_ALL_MOVIES,language));
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns all limited number of movies
     * @param language a language for data selection
     * @param currentPageNumber number of current page for pagination
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getLimitedMovies(String language, int currentPageNumber)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_ALL_LIMITED_MOVIES, language));
            preparedStatement.setInt(1, LIMIT_MOVIE_COUNT * (currentPageNumber - 1));
            preparedStatement.setInt(2, LIMIT_MOVIE_COUNT);
            resultSet = preparedStatement.executeQuery();
            return setMovieInfo(resultSet);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection", e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Returns movies ordered by its rating
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getTopMovies(String language)
            throws DAOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_TOP_MOVIES,language));
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns movies that match by genre ig
     * @param idGenre a genre id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByGenre(int idGenre, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.localizeStatement(SQL_GET_MOVIES_BY_GENRE,language));
            statement.setInt(1,idGenre);
            resultSet=statement.executeQuery();
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns movies by country code
     * @param countryCode a required country code
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByCountry(String countryCode, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.localizeStatement(SQL_GET_MOVIES_BY_COUNTRY,language));
            statement.setString(1,countryCode);
            resultSet=statement.executeQuery();
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Deletes a movie from data storage
     * @param idMovie movie id that has to be deleted
     * @return {@code true} if movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteMovie(int idMovie) throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(SQL_DELETE_MOVIE);
            statement.setInt(1,idMovie);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Returns a movie by its id
     * @param idMovie a movie id for search
     * @param language a language for data selection
     * @return {@link Movie} object
     * @throws DAOException
     */
    @Override
    public Movie getMovieById(int idMovie, String language) throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        Movie movie=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.localizeStatement(SQL_MOVIE_BY_ID,language));
            statement.setInt(1,idMovie);
            resultSet=statement.executeQuery();
            if (resultSet.next()){
                movie=createMovie(resultSet);
            }
            return movie;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Updates a required movie's field
     * @param idMovie a movie id for updating
     * @param field a required field for updating
     * @param value a new value of field
     * @param language a language for data selection
     * @return {@code true} if movie was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean editMovieField(int idMovie, String field, String value, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            String preparedSqlMovieEditStatement=
                    DAOUtil.prepareMovieEditStatement(SQL_UPDATE_MOVIE_FIELD,field);
            statement= connection.prepareStatement(DAOUtil.localizeStatement(preparedSqlMovieEditStatement,language));
            statement.setInt(2,idMovie);
            if (field.equals(RELEASE_YEAR)){
                int year=Integer.parseInt(value);
                statement.setInt(1,year);
            } else {
                statement.setString(1,value);
            }
            int updatedRows=statement.executeUpdate();
            return updatedRows>0;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Deletes a genre for movie in data storage
     * @param genreName a genre name that has to be deleted
     * @param language a language of genre name
     * @param idMovie a movie id of a movie that contains a genre
     * @return {@code true} if genre for movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteGenreForMovie(String genreName, String language, int idMovie)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.localizeStatement(SQL_DELETE_GENRE_FOR_MOVIE,language));
            statement.setInt(1,idMovie);
            statement.setString(2, genreName);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Adds a genre for movie in data storage
     * @param idMovie a movie id that contains a genre
     * @param genreName a genre name that has to be added
     * @param language a language of genre name
     * @return {@code true} if genre for movie was added
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean addGenreForMovie(int idMovie, String genreName, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.localizeStatement(SQL_ADD_GENRE_FOR_MOVIE,language));
            statement.setInt(1,idMovie);
            statement.setString(2, genreName);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Returns movies by genre name
     * @param genreName a genre name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByGenreName(String genreName, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_MOVIES_BY_GENRE_NAME,language));
            statement.setString(1,genreName);
            resultSet=statement.executeQuery();
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns movies by country name
     * @param countryName a country name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByCountryName(String countryName, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_MOVIES_BY_COUNTRY_NAME,language));
            statement.setString(1,countryName);
            resultSet=statement.executeQuery();
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns movies by actor first name and last name
     * @param firstName an actor first name for search
     * @param lastName an actor last name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getMoviesByActorInitial(String firstName,
                                               String lastName, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_MOVIES_BY_ACTOR_INITIAL,language));
            statement.setString(1,firstName);
            statement.setString(2, lastName);
            resultSet=statement.executeQuery();
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Deletes a country for movie in data storage
     * @param countryName a country name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a country
     * @return {@code true} if a genre for movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteCountryForMovie(String countryName, String language, int movieId)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_DELETE_COUNTRY_FOR_MOVIE,language));
            statement.setInt(1,movieId);
            statement.setString(2, countryName);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Adds a country for movie in data storage
     * @param idMovie a movie id of a movie that contains a country
     * @param countryName a country name that has to be added
     * @param language a language of country name
     * @return {@code true} if a country for movie was added
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean addCountryForMovie(int idMovie, String countryName, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_ADD_COUNTRY_FOR_MOVIE,language));
            statement.setInt(1,idMovie);
            statement.setString(2, countryName);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Deletes an actor for movie in data storage
     * @param firstName a first name of an actor that has to be deleted
     * @param lastName a last name of an actor that has to be deleted
     * @param language a language of first name and last name
     * @param movieId a movie id of a movie that contains an actor
     * @return {@code true} if an actor for movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean deleteActorForMovie(String firstName, String lastName,
                                       String language, int movieId)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_DELETE_ACTOR_FOR_MOVIE,language));
            statement.setInt(1,movieId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Adds an actor for movie in data storage
     * @param idMovie a movie id of a movie that contains an actor
     * @param firstName a first name of an actor that has to be added
     * @param lastName a last name of an actor that has to be added
     * @param language a language of first name and last name
     * @return {@code true} if an actor for movie was added
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean addActorForMovie(int idMovie, String firstName,
                                    String lastName, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_ADD_ACTOR_FOR_MOVIE,language));
            statement.setInt(1,idMovie);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            int updatedRow=statement.executeUpdate();
            return updatedRow == 1;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
    }

    /**
     * Creates a new movie in data storage
     * @param titleEn a title of a movie in English
     * @param titleRu a title of a movie in Russian
     * @param releaseYear a release year of a movie
     * @param descrEn a description of a movie in English
     * @param descrRu a description of a movie in Russian
     * @return an id of a new inserted movie
     * @throws DAOException
     */
    @Override
    public int addMovie(String titleEn, String titleRu,
                          int releaseYear, String descrEn,
                          String descrRu)
            throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(SQL_ADD_MOVIE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,titleRu);
            statement.setString(2, titleEn);
            statement.setInt(3, releaseYear);
            statement.setString(4, descrRu);
            statement.setString(5, descrEn);
            int updatedRows=statement.executeUpdate();
            if (updatedRows>0){
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return (int)generatedKeys.getLong(1);
                }
            }
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement);
        }
        return 0;
    }

    /**
     * Returns movie that matches by title in English
     * @param titleEn a title of a movie in English
     * @return {@link Movie} object
     * @throws DAOException
     */
    @Override
    public Movie getMovieByTitleEn(String titleEn) throws DAOException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        Movie movie=null;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.prepareStatement(SQL_GET_MOVIE_BY_TITLE_EN);
            statement.setString(1,titleEn);
            resultSet=statement.executeQuery();
            if (resultSet.next()){
                movie=createMovie(resultSet);
            }
            return movie;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Updates image path of a movie's poster
     * @param idMovie an id of a movie that has to be updated
     * @param imgPath a new image path of a movie
     * @return {@code true} if a movie was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    @Override
    public boolean uploadMoviePoster(int idMovie, String imgPath)
            throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_MOVIE_POSTER);
            preparedStatement.setString(1, imgPath);
            preparedStatement.setInt(2, idMovie);
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
     * Returns movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getNewestMovies(String language) throws DAOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_NEWEST_MOVIES+LIMIT_NEWEST_MOVIE,language));
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns limited movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    @Override
    public List<Movie> getNewestLimitedMovies(String language) throws DAOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<Movie> movieList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_NEWEST_MOVIES+MAIN_LIMIT_NEWEST_MOVIE,language));
            movieList=setMovieInfo(resultSet);
            return movieList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Setups movies' information
     * @param resultSet {@link ResultSet} object contains movies' data
     * @return {@link List} of {@link Movie} objects
     * @throws SQLException
     */
    private List<Movie> setMovieInfo(ResultSet resultSet) throws SQLException{
        List<Movie> movieList=new ArrayList<>();
        while (resultSet.next()){
            Movie movie=createMovie(resultSet);
            movieList.add(movie);
        }
        return movieList;
    }

    /**
     * Instantiates movie
     * @param resultSet resultSet {@link ResultSet} object
     *                  contains movie's data
     * @return {@link Movie} object
     * @throws SQLException
     */
    private Movie createMovie(ResultSet resultSet) throws SQLException{
        Movie movie=new Movie();
        movie.setId(resultSet.getInt(1));
        movie.setTitle(resultSet.getString(2));
        movie.setReleaseYear(resultSet.getInt(3));
        movie.setDescription(resultSet.getString(4));
        movie.setRating(resultSet.getDouble(5));
        movie.setPosterPath(resultSet.getString(6));
        return movie;
    }

}
