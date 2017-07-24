package by.epam.movierating.service.impl;

import by.epam.movierating.bean.Review;
import by.epam.movierating.bean.dto.ReviewDTO;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.dao.ReviewDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.ReviewService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.util.Validator;

import java.util.List;

/**
 * Provides a business-logic with the {@link Review} entity.
 */
public class ReviewServiceImpl implements ReviewService {
    /**
     * Returns all reviews order by publish date
     * @return {@link List} of {@link Review} objects
     * @throws ServiceException
     */
    @Override
    public List<Review> getAllReviewsOrderByDate()
            throws ServiceException {
        List<Review> reviewList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            reviewList = reviewDAO.getAllReviewsOrderByDate();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reviewList;
    }

    /**
     * Returns reviews that contain full information of a review
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<ReviewDTO> getAllFullInfoReviewsOrderByDate(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<ReviewDTO> reviewList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            reviewList = reviewDAO.getAllFullInfoReviewsOrderByDate(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reviewList;
    }

    /**
     * Returns limited reviews
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<ReviewDTO> getLimitedReviews(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<ReviewDTO> reviewList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            reviewList = reviewDAO.getLimitedReviews(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reviewList;
    }

    /**
     * Defines rate opportunity of a user on a concrete movie
     * @param idMovie an id of a movie that has to be checked
     * @param idUser an id of a user that has to be checked
     * @return {@code true} if user has not such an opportunity
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean checkReviewOpportunity(int idMovie, int idUser)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            return reviewDAO.checkReviewOpportunity(idMovie, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns reviews by id movie
     * @param idMovie an id of the movie for search
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<ReviewDTO> getReviewsByIdMovie(int idMovie, String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        Validator.validateIntData(idMovie);
        List<ReviewDTO> reviewList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            reviewList = reviewDAO.getReviewsByIdMovie(idMovie, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reviewList;
    }

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
    @Override
    public boolean reviewMovie(int idMovie, int idUser, String title, String review)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser);
        Validator.validateStringData(title, review);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            return reviewDAO.reviewMovie(idMovie, idUser, title, review);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns the information of the most reviewed movies
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<StaticticsDTO> getReviewStatistics(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            return reviewDAO.getReviewStatistics(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes a review from data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an if of a user who owns the review
     * @return {@code true} if the review was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteReview(int idMovie, int idUser)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            return reviewDAO.deleteReview(idMovie, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns reviews by user id
     * @param idUser an id of a user who owns reviews
     * @param language a language for data selection
     * @return {@link List} of {@link ReviewDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<ReviewDTO> getReviewsByUserId(int idUser, String language)
            throws ServiceException {
        Validator.validateIntData(idUser);
        Validator.validateLanguage(language);
        List<ReviewDTO> reviewList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ReviewDAO reviewDAO = daoFactory.getReviewDAO();
            reviewList = reviewDAO.getReviewsByUserId(idUser, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reviewList;
    }
}
