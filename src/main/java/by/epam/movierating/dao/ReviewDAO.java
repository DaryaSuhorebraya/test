package by.epam.movierating.dao;

import by.epam.movierating.bean.Review;
import by.epam.movierating.bean.dto.ReviewDTO;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link Review} entity in data storage
 */
public interface ReviewDAO {
    /**
     * Returns all reviews order by publish date
     * @return {@link List} of {@link Review} objects
     * @throws DAOException
     */
    List<Review> getAllReviewsOrderByDate() throws DAOException;

    /**
     * Returns reviews that contain full information of a review
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws DAOException
     */
    List<ReviewDTO> getAllFullInfoReviewsOrderByDate(String language) throws DAOException;

    /**
     * Returns limited reviews
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws DAOException
     */
    List<ReviewDTO> getLimitedReviews(String language) throws DAOException;

    /**
     * Defines rate opportunity of a user on a concrete movie
     * @param idMovie an id of a movie that has to be checked
     * @param idUser an id of a user that has to be checked
     * @return {@code true} if user has not such an opportunity
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean checkReviewOpportunity(int idMovie, int idUser) throws DAOException;

    /**
     * Returns reviews by id movie
     * @param idMovie an id of the movie for search
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws DAOException
     */
    List<ReviewDTO> getReviewsByIdMovie(int idMovie, String language) throws DAOException;

    /**
     * Creates a new review in data storage
     * @param idMovie an id of the movie that has to be reviewed
     * @param idUser an id of the user that has raviewed the movie
     * @param title a title of the review
     * @param review a text of the review
     * @return {@code true} if a new review was created
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean reviewMovie(int idMovie, int idUser, String title, String review) throws DAOException;

    /**
     * Returns the information of the most reviewed movies
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws DAOException
     */
    List<StaticticsDTO> getReviewStatistics(String language) throws DAOException;

    /**
     * Deletes a review from data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an if of a user who owns the review
     * @return {@code true} if the review was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteReview(int idMovie, int idUser) throws DAOException;

    /**
     * Returns reviews by user id
     * @param idUser an id of a user who owns reviews
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws DAOException
     */
    List<ReviewDTO> getReviewsByUserId(int idUser, String language) throws DAOException;

}
