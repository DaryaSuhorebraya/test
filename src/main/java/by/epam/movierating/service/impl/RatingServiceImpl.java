package by.epam.movierating.service.impl;

import by.epam.movierating.bean.Rating;
import by.epam.movierating.dao.RatingDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.RatingService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.util.Validator;

/**
 * Provides a business-logic with the {@link Rating} entity.
 */
public class RatingServiceImpl implements RatingService {

    /**
     * Creates a new rating in data storage
     * @param idMovie an id of the movie that has to be rated
     * @param idUser an id of the user that has rated the movie
     * @param mark a mark of rating
     * @return {@code true} new rating was created
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean rateMovie(int idMovie, int idUser, int mark)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser, mark);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            return ratingDAO.rateMovie(idMovie, idUser, mark);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
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
    public boolean checkRateOpportunity(int idMovie, int idUser)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            return ratingDAO.checkRateOpportunity(idMovie, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a rating of a movie by user id
     * @param idMovie an id of a movie
     * @param idUser an id of a movie
     * @return {@link Rating} object
     * @throws ServiceException
     */
    @Override
    public Rating getRatingOnMovieByUserId(int idMovie, int idUser)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            return ratingDAO.getRatingOnMovieByUserId(idMovie, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes rating in data storage
     * @param idMovie an id of a movie that has to be deleted
     * @param idUser an id of a user who owns a rating
     * @return {@code true} if a rating was deleted from a data storage
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteRating(int idMovie, int idUser)
            throws ServiceException {
        Validator.validateIntData(idMovie, idUser);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            RatingDAO ratingDAO = daoFactory.getRatingDAO();
            return ratingDAO.deleteRating(idMovie, idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
