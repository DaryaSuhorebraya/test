package by.epam.movierating.service.impl;

import by.epam.movierating.bean.Movie;
import by.epam.movierating.dao.MovieDAO;
import by.epam.movierating.dao.UserDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.MovieService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongDataException;
import by.epam.movierating.service.util.Validator;

import java.util.List;

/**
 * Provides a business-logic with the {@link Movie} entity.
 */
public class MovieServiceImpl implements MovieService {
    /**
     * Returns all movies
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getAllMovies(String language) throws ServiceException {
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getAllMovies(language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns all limited number of movies
     * @param language a language for data selection
     * @param currentPageNumber number of current page for pagination
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getLimitedMovies(String language, int currentPageNumber)
            throws ServiceException {
        Validator.validateIntData(currentPageNumber);
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getLimitedMovies(language, currentPageNumber);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns movies ordered by its rating
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getTopMovies(String language) throws ServiceException {
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getTopMovies(language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns movies by genre id
     * @param idGenre a genre id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getMoviesByGenre(int idGenre, String language)
            throws ServiceException {
        Validator.validateIntData(idGenre);
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getMoviesByGenre(idGenre,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns movies by country code
     * @param countryCode a required country code
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getMoviesByCountry(String countryCode, String language)
            throws ServiceException {
        Validator.validateStringData(countryCode);
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getMoviesByCountry(countryCode,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Deletes a movie from data storage
     * @param idMovie movie id that has to be deleted
     * @return {@code true} if movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteMovie(int idMovie)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.deleteMovie(idMovie);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a movie by its id
     * @param idMovie a movie id for search
     * @param language a language for data selection
     * @return {@link Movie} object
     * @throws ServiceException
     */
    @Override
    public Movie getMovieById(int idMovie, String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        Validator.validateIntData(idMovie);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.getMovieById(idMovie,language);
        } catch (DAOException e){
            throw new ServiceException(e);
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
     * @throws ServiceException
     */
    @Override
    public boolean editMovieField(int idMovie, String field, String value, String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        Validator.validateIntData(idMovie);
        Validator.validateStringData(field,value);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.editMovieField(idMovie,field,value,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes a genre for movie in data storage
     * @param genreName a genre name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a genre
     * @return {@code true} if genre for movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteGenreForMovie(String genreName, String language, int movieId)
            throws ServiceException {
        Validator.validateStringData(genreName);
        Validator.validateLanguage(language);
        Validator.validateIntData(movieId);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.deleteGenreForMovie(genreName, language, movieId);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Adds a genre for movie in data storage
     * @param idMovie a movie id that contains a genre
     * @param genreName a genre name that has to be added
     * @param language a language of genre name
     * @return {@code true} if genre for movie was added
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean addGenreForMovie(int idMovie, String genreName, String language)
            throws ServiceException {
        Validator.validateStringData(genreName);
        Validator.validateLanguage(language);
        Validator.validateIntData(idMovie);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.addGenreForMovie(idMovie, genreName, language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Returns movies by genre name
     * @param genreName a genre name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getMoviesByGenreName(String genreName, String language)
            throws ServiceException {
        Validator.validateStringData(genreName);
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getMoviesByGenreName(genreName,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns movies by country name
     * @param countryName a country name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getMoviesByCountryName(String countryName, String language)
            throws ServiceException {
        Validator.validateStringData(countryName);
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getMoviesByCountryName(countryName,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns movies by actor first name and last name
     * @param firstName an actor first name for search
     * @param lastName an actor last name for search
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getMoviesByActorInitial(String firstName, String lastName, String language)
            throws ServiceException {
        Validator.validateStringData(firstName, lastName);
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getMoviesByActorInitial(firstName,lastName,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Deletes a country for movie in data storage
     * @param countryName a country name that has to be deleted
     * @param language a language of genre name
     * @param movieId a movie id of a movie that contains a country
     * @return {@code true} if a genre for movie was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteCountryForMovie(String countryName, String language, int movieId)
            throws ServiceException {
        Validator.validateStringData(countryName);
        Validator.validateLanguage(language);
        Validator.validateIntData(movieId);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.deleteCountryForMovie(countryName, language, movieId);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Adds a country for movie in data storage
     * @param idMovie a movie id of a movie that contains a country
     * @param countryName a country name that has to be added
     * @param language a language of country name
     * @return {@code true} if a country for movie was added
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean addCountryForMovie(int idMovie, String countryName, String language)
            throws ServiceException {
        Validator.validateStringData(countryName);
        Validator.validateLanguage(language);
        Validator.validateIntData(idMovie);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.addCountryForMovie(idMovie, countryName, language);
        } catch (DAOException e){
            throw new ServiceException(e);
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
     * @throws ServiceException
     */
    @Override
    public boolean deleteActorForMovie(String firstName, String lastName,
                                       String language, int movieId)
            throws ServiceException {
        Validator.validateStringData(firstName,lastName);
        Validator.validateLanguage(language);
        Validator.validateIntData(movieId);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.deleteActorForMovie(firstName,lastName, language, movieId);
        } catch (DAOException e){
            throw new ServiceException(e);
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
     * @throws ServiceException
     */
    @Override
    public boolean addActorForMovie(int idMovie, String firstName,
                                    String lastName, String language)
            throws ServiceException {
        Validator.validateStringData(firstName,lastName);
        Validator.validateLanguage(language);
        Validator.validateIntData(idMovie);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.addActorForMovie(idMovie, firstName,lastName, language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

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
    @Override
    public int addMovie(String nameEn, String nameRu,
                          int releaseYear, String descrEn,
                          String descrRu)
            throws ServiceException {
        Validator.validateIntData(releaseYear);
        Validator.validateStringData(nameEn, nameRu, descrEn, descrRu);
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.addMovie(nameEn, nameRu, releaseYear, descrEn, descrRu);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Updates image path of a movie's poster
     * @param idMovie an id of a movie that has to be updated
     * @param imgPath a new image path of a movie
     * @return {@code true} if a movie was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean uploadMoviePoster(int idMovie, String imgPath)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateStringData(imgPath);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            return movieDAO.uploadMoviePoster(idMovie, imgPath);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getNewestMovies(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getNewestMovies(language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }

    /**
     * Returns limited movies ordered by release year
     * @param language a language for data selection
     * @return {@link List} of {@link Movie} objects
     * @throws ServiceException
     */
    @Override
    public List<Movie> getNewestLimitedMovies(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<Movie> movieList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            MovieDAO movieDAO=daoFactory.getMovieDAO();
            movieList=movieDAO.getNewestLimitedMovies(language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return movieList;
    }
}
