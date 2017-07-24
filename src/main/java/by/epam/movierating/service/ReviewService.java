package by.epam.movierating.service;

import by.epam.movierating.bean.Review;
import by.epam.movierating.bean.dto.ReviewDTO;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides work with {@link Review} entity
 */
public interface ReviewService {
    /**
     * Returns all reviews order by publish date
     * @return {@link List} of {@link Review} objects
     * @throws ServiceException
     */
    List<Review> getAllReviewsOrderByDate() throws ServiceException;

    /**
     * Returns reviews that contain full information of a review
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    List<ReviewDTO> getAllFullInfoReviewsOrderByDate(String language) throws ServiceException;

    /**
     * Returns reviews by id movie
     * @param idMovie an id of the movie for search
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    List<ReviewDTO> getReviewsByIdMovie(int idMovie, String language) throws ServiceException;

    /**
     * Returns limited reviews
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    List<ReviewDTO> getLimitedReviews(String language) throws ServiceException;

    /**
     * Defines rate opportunity of a user on a concrete movie
     * @param idMovie an id of a movie that has to be checked
     * @param idUser an id of a user that has to be checked
     * @return {@code true} if user has not such an opportunity
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean checkReviewOpportunity(int idMovie, int idUser) throws ServiceException;

    /**
     * Creates a new review in data storage
     * @param idMovie an id of the movie that has to be reviewed
     * @param idUser an id of the user that has raviewed the movie
     * @param title a title of the review
     * @param review a text of the review
     * @return {@code true} if a new review was created
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean reviewMovie(int idMovie, int idUser, String title, String review) throws ServiceException;

    /**
     * Returns the information of the most reviewed movies
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws ServiceException
     */
    List<StaticticsDTO> getReviewStatistics(String language) throws ServiceException;

    /**
     * Deletes a review from data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an if of a user who owns the review
     * @return {@code true} if the review was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteReview(int idMovie, int idUser) throws ServiceException;

    /**
     * Returns reviews by user id
     * @param idUser an id of a user who owns reviews
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    List<ReviewDTO> getReviewsByUserId(int idUser, String language) throws ServiceException;
}
