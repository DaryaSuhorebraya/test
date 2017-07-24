package by.epam.movierating.dao.factory;

import by.epam.movierating.dao.*;
import by.epam.movierating.dao.impl.*;

/**
 * Provides logic of instancing DAO objects.
 */
public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private CountryDAO countryDAO = new CountryDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();
    private MovieDAO movieDAO = new MovieDAOImpl();
    private GenreDAO genreDAO = new GenreDAOImpl();
    private ActorDAO actorDAO = new ActorDAOImpl();
    private ReviewDAO reviewDAO = new ReviewDAOImpl();
    private RatingDAO ratingDAO = new RatingDAOImpl();

    public DAOFactory() {
    }

    /**
     * Returns an instance of DAOFactory
     * @return {@link DAOFactory} object
     */
    public static DAOFactory getInstance() {
        return instance;
    }

    /**
     * Returns an implementation of a CountryDAO interface.
     * @return {@link CountryDAO} object
     */
    public CountryDAO getCountryDAO() {
        return countryDAO;
    }

    /**
     * Returns an implementation of a UserDAO interface.
     * @return {@link UserDAO} object
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Returns an implementation of a MovieDAO interface.
     * @return {@link MovieDAO} object
     */
    public MovieDAO getMovieDAO() {
        return movieDAO;
    }

    /**
     * Returns an implementation of a GenreDAO interface.
     * @return {@link GenreDAO} object
     */
    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    /**
     * Returns an implementation of a ActorDAO interface.
     * @return {@link ActorDAO} object
     */
    public ActorDAO getActorDAO() {
        return actorDAO;
    }

    /**
     * Returns an implementation of a ReviewDAO interface.
     * @return {@link ReviewDAO} object
     */
    public ReviewDAO getReviewDAO() {
        return reviewDAO;
    }

    /**
     * Returns an implementation of a RatingDAO interface.
     * @return {@link RatingDAO} object
     */
    public RatingDAO getRatingDAO() {
        return ratingDAO;
    }
}
