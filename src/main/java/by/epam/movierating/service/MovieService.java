package by.epam.movierating.service;

import by.epam.movierating.bean.Movie;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides work with {@link Movie} entity
 */
public interface MovieService {
    /**
     * Returns all movies
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getAllMovies(String language) throws ServiceException;

    /**
     * Returns all limited number of movies
     * @param language a language for data selection
     * @param currentPageNumber number of current page for pagination
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getLimitedMovies(String language, int currentPageNumber) throws ServiceException;

    /**
     * Returns movies ordered by its rating
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getTopMovies(String language) throws ServiceException;

    /**
     * Returns movies by genre id
     * @param idGenre a genre id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getMoviesByGenre(int idGenre, String language) throws ServiceException;

    /**
     * Returns movies by country code
     * @param countryCode a required country code
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getMoviesByCountry(String countryCode, String language) throws ServiceException;

    /**
     * Deletes a movie from data storage
     * @param idMovie movie id that has to be deleted
     * @return {@code true} if movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteMovie(int idMovie) throws ServiceException;

    /**
     * Returns a movie by its id
     * @param idMovie a movie id for search
     * @param language a language for data selection
     * @return {@link Movie} object
     * @throws ServiceException
     */
    Movie getMovieById(int idMovie, String language) throws ServiceException;

    /**
     * Updates a required movie's field
     * @param idMovie a movie id for updating
     * @param field a required field for updating
     * @param value a new value of field
     * @param language a language for data selection
     * @return {@code true} if movie was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean editMovieField(int idMovie, String field, String value,String language) throws ServiceException;

    /**
     * Deletes a genre for movie in data storage
     * @param genreName a genre name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a genre
     * @return {@code true} if genre for movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteGenreForMovie(String genreName, String language, int movieId) throws ServiceException;

    /**
     * Adds a genre for movie in data storage
     * @param idMovie a movie id that contains a genre
     * @param genreName a genre name that has to be added
     * @param language a language of genre name
     * @return {@code true} if genre for movie was added
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean addGenreForMovie(int idMovie, String genreName, String language) throws ServiceException;

    /**
     * Returns movies by genre name
     * @param genreName a genre name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getMoviesByGenreName(String genreName, String language) throws ServiceException;

    /**
     * Returns movies by country name
     * @param countryName a country name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getMoviesByCountryName(String countryName, String language) throws ServiceException;

    /**
     * Returns movies by actor first name and last name
     * @param firstName an actor first name for search
     * @param lastName an actor last name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getMoviesByActorInitial(String firstName, String lastName, String language) throws ServiceException;

    /**
     * Deletes a country for movie in data storage
     * @param countryName a country name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a country
     * @return {@code true} if a genre for movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteCountryForMovie(String countryName, String language, int movieId) throws ServiceException;

    /**
     * Adds a country for movie in data storage
     * @param idMovie a movie id of a movie that contains a country
     * @param countryName a country name that has to be added
     * @param language a language of country name
     * @return {@code true} if a country for movie was added
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean addCountryForMovie(int idMovie, String countryName, String language) throws ServiceException;

    /**
     * Deletes an actor for movie in data storage
     * @param firstName a first name of an actor that has to be deleted
     * @param lastName a last name of an actor that has to be deleted
     * @param language a language of first name and last name
     * @param movieId a movie id of a movie that contains an actor
     * @return {@code true} if an actor for movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteActorForMovie(String firstName, String lastName, String language, int movieId) throws ServiceException;

    /**
     * Adds an actor for movie in data storage
     * @param idMovie a movie id of a movie that contains an actor
     * @param firstName a first name of an actor that has to be added
     * @param lastName a last name of an actor that has to be added
     * @param language a language of first name and last name
     * @return {@code true} if an actor for movie was added
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean addActorForMovie(int idMovie, String firstName, String lastName, String language) throws ServiceException;

    /**
     * Creates a new movie in data storage
     * @param nameEn a name of a movie in English
     * @param nameRu a name of a movie in Russian
     * @param releaseYear a release year of a movie
     * @param descrEn a description of a movie in English
     * @param descrRu a description of a movie in Russian
     * @return an id of a new inserted movie
     * @throws ServiceException
     */
    int addMovie(String nameEn, String nameRu, int releaseYear, String descrEn, String descrRu) throws ServiceException;

    /**
     * Updates image path of a movie's poster
     * @param idMovie an id of a movie that has to be updated
     * @param imgPath a new image path of a movie
     * @return {@code true} if a movie was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean uploadMoviePoster(int idMovie, String imgPath) throws ServiceException;

    /**
     * Returns movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getNewestMovies(String language) throws ServiceException;

    /**
     * Returns limited movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    List<Movie> getNewestLimitedMovies(String language) throws ServiceException;
}
