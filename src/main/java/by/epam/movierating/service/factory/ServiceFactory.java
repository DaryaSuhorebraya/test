package by.epam.movierating.service.factory;

import by.epam.movierating.service.*;
import by.epam.movierating.service.impl.*;

/**
 * Provides logic of instancing Service objects.
 */
public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private UserService userService = new UserServiceImpl();
    private MovieService movieService = new MovieServiceImpl();
    private GenreService genreService = new GenreServiceImpl();
    private CountryService countryService = new CountryServiceImpl();
    private ActorService actorService = new ActorServiceImpl();
    private ReviewService reviewService = new ReviewServiceImpl();
    private RatingService ratingService = new RatingServiceImpl();

    public ServiceFactory() {
    }

    /**
     * Returns an instance of ServiceFactory
     * @return {@link ServiceFactory} object
     */
    public static ServiceFactory getInstance() {
        return instance;
    }

    /**
     * Returns an implementation of a UserService interface.
     * @return {@link UserService} object
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Returns an implementation of a MovieService interface.
     * @return {@link MovieService} object
     */
    public MovieService getMovieService() {
        return movieService;
    }

    /**
     * Returns an implementation of a GenreService interface.
     * @return {@link GenreService} object
     */
    public GenreService getGenreService() {
        return genreService;
    }

    /**
     * Returns an implementation of a CountryService interface.
     * @return {@link CountryService} object
     */
    public CountryService getCountryService() {
        return countryService;
    }

    /**
     * Returns an implementation of a ActorService interface.
     * @return {@link ActorService} object
     */
    public ActorService getActorService() {
        return actorService;
    }

    /**
     * Returns an implementation of a ReviewService interface.
     * @return {@link ReviewService} object
     */
    public ReviewService getReviewService() {
        return reviewService;
    }

    /**
     * Returns an implementation of a RatingService interface.
     * @return {@link RatingService} object
     */
    public RatingService getRatingService() {
        return ratingService;
    }
}
