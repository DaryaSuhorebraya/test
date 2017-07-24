package by.epam.movierating.dao;

import by.epam.movierating.bean.Movie;
import by.epam.movierating.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link Movie} entity in data storage
 */
public interface MovieDAO {
    /**
     * Returns all movies
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getAllMovies(String language) throws DAOException;

    /**
     * Returns all limited number of movies
     * @param language a language for data selection
     * @param currentPageNumber number of current page for pagination
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getLimitedMovies(String language, int currentPageNumber) throws DAOException;

    /**
     * Returns movies ordered by its rating
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getTopMovies(String language) throws DAOException;

    /**
     * Returns movies that match by genre ig
     * @param idGenre a genre id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getMoviesByGenre(int idGenre, String language) throws DAOException;

    /**
     * Returns movies by country code
     * @param countryCode a required country code
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getMoviesByCountry(String countryCode, String language) throws DAOException;

    /**
     * Deletes a movie from data storage
     * @param idMovie movie id that has to be deleted
     * @return {@code true} if movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteMovie(int idMovie) throws DAOException;

    /**
     * Returns a movie by its id
     * @param idMovie a movie id for search
     * @param language a language for data selection
     * @return {@link Movie} object
     * @throws DAOException
     */
    Movie getMovieById(int idMovie, String language) throws DAOException;

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
    boolean editMovieField(int idMovie, String field, String value, String language) throws DAOException;

    /**
     * Deletes a genre for movie in data storage
     * @param genreName a genre name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a genre
     * @return {@code true} if genre for movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteGenreForMovie(String genreName, String language, int movieId) throws DAOException;

    /**
     * Adds a genre for movie in data storage
     * @param idMovie a movie id that contains a genre
     * @param genreName a genre name that has to be added
     * @param language a language of genre name
     * @return {@code true} if genre for movie was added
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean addGenreForMovie(int idMovie, String genreName, String language) throws DAOException;

    /**
     * Returns movies by genre name
     * @param genreName a genre name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getMoviesByGenreName(String genreName, String language) throws DAOException;

    /**
     * Returns movies by country name
     * @param countryName a country name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getMoviesByCountryName(String countryName, String language) throws DAOException;

    /**
     * Returns movies by actor first name and last name
     * @param firstName an actor first name for search
     * @param lastName an actor last name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getMoviesByActorInitial(String firstName, String lastName, String language) throws DAOException;

    /**
     * Deletes a country for movie in data storage
     * @param countryName a country name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a country
     * @return {@code true} if a genre for movie was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteCountryForMovie(String countryName, String language, int movieId) throws DAOException;

    /**
    * Adds a country for movie in data storage
    * @param idMovie a movie id of a movie that contains a country
    * @param countryName a country name that has to be added
    * @param language a language of country name
    * @return {@code true} if a country for movie was added
    *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean addCountryForMovie(int idMovie, String countryName, String language) throws DAOException;

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
    boolean deleteActorForMovie(String firstName, String lastName, String language, int movieId) throws DAOException;

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
    boolean addActorForMovie(int idMovie, String firstName, String lastName, String language) throws DAOException;

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
    int addMovie(String titleEn, String titleRu, int releaseYear, String descrEn, String descrRu) throws DAOException;

    /**
     * Returns movie that matches by title in English
     * @param titleEn a title of a movie in English
     * @return {@link Movie} object
     * @throws DAOException
     */
    Movie getMovieByTitleEn(String titleEn) throws DAOException;

    /**
     * Updates image path of a movie's poster
     * @param idMovie an id of a movie that has to be updated
     * @param imgPath a new image path of a movie
     * @return {@code true} if a movie was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean uploadMoviePoster(int idMovie, String imgPath) throws DAOException;

    /**
     * Returns movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getNewestMovies(String language) throws DAOException;

    /**
     * Returns limited movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws DAOException
     */
    List<Movie> getNewestLimitedMovies(String language) throws DAOException;
}
