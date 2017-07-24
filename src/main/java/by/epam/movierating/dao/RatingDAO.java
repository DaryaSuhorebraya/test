package by.epam.movierating.dao;

import by.epam.movierating.bean.Rating;
import by.epam.movierating.dao.exception.DAOException;

/**
 * Provides a DAO-logic for the {@link Rating} entity in data storage
 */
public interface RatingDAO {
    /**
     * Creates a new rating in data storage
     * @param idMovie an id of the movie that has to be rated
     * @param idUser an id of the user that has rated the movie
     * @param mark a mark of rating
     * @return {@code true} new rating was created
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean rateMovie(int idMovie, int idUser, int mark) throws DAOException;

    /**
     * Defines rate opportunity of a user on a concrete movie
     * @param idMovie an id of a movie that has to be checked
     * @param idUser an id of a user that has to be checked
     * @return {@code true} if user has not such an opportunity
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean checkRateOpportunity(int idMovie, int idUser) throws DAOException;

    /**
     * Returns a rating of a movie by user id
     * @param idMovie an id of a movie
     * @param idUser an id of a movie
     * @return {@link Rating} object
     * @throws DAOException
     */
    Rating getRatingOnMovieByUserId(int idMovie, int idUser) throws DAOException;

    /**
     * Deletes rating in data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an id of a user who owns a rating
     * @return {@code true} if a rating was deleted from a data storage
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteRating(int idMovie, int idUser) throws DAOException;
}
