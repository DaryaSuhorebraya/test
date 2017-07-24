package by.epam.movierating.service;

import by.epam.movierating.bean.Rating;
import by.epam.movierating.service.exception.ServiceException;

/**
 * Provides work with {@link Rating} entity
 */
public interface RatingService {
    /**
     * Creates a new rating in data storage
     * @param idMovie an id of the movie that has to be rated
     * @param idUser an id of the user that has rated the movie
     * @param mark a mark of rating
     * @return {@code true} new rating was created
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean rateMovie(int idMovie, int idUser, int mark) throws ServiceException;

    /**
     * Defines rate opportunity of a user on a concrete movie
     * @param idMovie an id of a movie that has to be checked
     * @param idUser an id of a user that has to be checked
     * @return {@code true} if user has not such an opportunity
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean checkRateOpportunity(int idMovie, int idUser) throws ServiceException;

    /**
     * Returns a rating of a movie by user id
     * @param idMovie an id of a movie
     * @param idUser an id of a movie
     * @return {@link Rating} object
     * @throws ServiceException
     */
    Rating getRatingOnMovieByUserId(int idMovie, int idUser) throws ServiceException;

    /**
     * Deletes rating in data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an id of a user who owns a rating
     * @return {@code true} if a rating was deleted from a data storage
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteRating(int idMovie, int idUser) throws ServiceException;
}
