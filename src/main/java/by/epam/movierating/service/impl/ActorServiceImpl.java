package by.epam.movierating.service.impl;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.bean.Movie;
import by.epam.movierating.dao.ActorDAO;
import by.epam.movierating.dao.MovieDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.ActorService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.util.Validator;

import java.util.List;

/**
 * Provides a business-logic with the {@link Actor} entity.
 */
public class ActorServiceImpl implements ActorService {
    /**
     * Returns {@link List} of actors which has reference to the required movie id
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    @Override
    public List<Actor> getActorsByMovieId(int idMovie, String language)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateLanguage(language);
        List<Actor> actorList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            actorList = actorDAO.getActorsByMovieId(idMovie, language);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
        return actorList;
    }

    /**
     * Returns {@link List} of {@link Actor} objects
     * which do not have reference to the concrete movie
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    @Override
    public List<Actor> getActorsNotInMovie(int idMovie, String language)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateLanguage(language);
        List<Actor> actorList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            actorList = actorDAO.getActorsNotInMovie(idMovie, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return actorList;
    }

    /**
     * Returns all actors in json type
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    @Override
    public List<Actor> getAllActorsForJson(String language) throws ServiceException {
        Validator.validateLanguage(language);
        List<Actor> actorList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            actorList = actorDAO.getAllActors(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return actorList;
    }

    /**
     * Returns all actors by given language
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    @Override
    public List<Actor> getAllActors(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<Actor> actorList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            actorList = actorDAO.getAllActors(language);
            for (Actor actor : actorList) {
                List<Movie> movieList = movieDAO.getMoviesByActorInitial(actor.getFirstName(),
                        actor.getLastName(), language);
                actor.setMovieList(movieList);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return actorList;
    }

    /**
     * Deletes an actor from the data storage
     * @param idActor actor id that has to be deleted
     * @return {@code true} if actor was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteActor(int idActor)
            throws ServiceException {
        Validator.validateIntData(idActor);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            return actorDAO.deleteActor(idActor);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates the actor in data storage
     * @param idActor actor id which has to be updated
     * @param firstName a new first name of an actor
     * @param lastName a new last name of an actor
     * @param language a language of data
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean editActor(int idActor, String firstName,
                             String lastName, String language)
            throws ServiceException {
        Validator.validateIntData(idActor);
        Validator.validateStringData(firstName, lastName);
        Validator.validateLanguage(language);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            return actorDAO.editActor(idActor, firstName, lastName, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Sets a new image path to the {@link Actor} object
     * @param idActor actor id which has to be updated
     * @param filePath a new file path to an image
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean uploadActorImage(int idActor, String filePath)
            throws ServiceException {
        Validator.validateIntData(idActor);
        Validator.validateStringData(filePath);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            return actorDAO.uploadActorImage(idActor, filePath);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Creates a new actor in data storage
     * @param firstNameEn first name in English
     * @param lastNameEn last name in English
     * @param firstNameRu first name in Russian
     * @param lastNameRu last name in Russian
     * @return id of a new inserted actor in data storage
     * @throws ServiceException
     */
    @Override
    public int addActor(String firstNameEn, String lastNameEn,
                        String firstNameRu, String lastNameRu)
            throws ServiceException {
        Validator.validateStringData(firstNameEn,lastNameEn,firstNameRu,lastNameRu);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            return actorDAO.addActor(firstNameEn,lastNameEn,firstNameRu,lastNameRu);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns {@link List} of 4 {@link Actor} objects
     * required by current page number
     * @param language specify concrete data's language
     * @param currentPageNumber a number of current page with actors
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    @Override
    public List<Actor> getAllLimitedActors(String language, int currentPageNumber)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<Actor> actorList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            ActorDAO actorDAO = daoFactory.getActorDAO();
            MovieDAO movieDAO = daoFactory.getMovieDAO();
            actorList = actorDAO.getAllLimitedActors(language, currentPageNumber);
            for (Actor actor : actorList) {
                List<Movie> movieList = movieDAO.getMoviesByActorInitial(actor.getFirstName(),
                        actor.getLastName(), language);
                actor.setMovieList(movieList);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return actorList;
    }
}
