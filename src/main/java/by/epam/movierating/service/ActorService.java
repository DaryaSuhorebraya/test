package by.epam.movierating.service;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides work with {@link Actor} entity
 */
public interface ActorService {
    /**
     * Returns all actors by given language
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    List<Actor> getAllActors(String language) throws ServiceException;

    /**
     * Returns all actors in json type
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    List<Actor> getAllActorsForJson(String language) throws ServiceException;

    /**
     * Returns {@link List} of actors which has reference to the required movie id
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    List<Actor> getActorsByMovieId(int idMovie, String language) throws ServiceException;

    /**
     * Returns {@link List} of {@link Actor} objects
     * which do not have reference to the concrete movie
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    List<Actor> getActorsNotInMovie(int idMovie, String language) throws ServiceException;

    /**
     * Deletes an actor from the data storage
     * @param idActor actor id that has to be deleted
     * @return {@code true} if actor was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteActor(int idActor) throws ServiceException;

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
    boolean editActor(int idActor, String firstName, String lastName, String language) throws ServiceException;

    /**
     * Sets a new image path to the {@link Actor} object
     * @param idActor actor id which has to be updated
     * @param filePath a new file path to an image
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean uploadActorImage(int idActor, String filePath) throws ServiceException;

    /**
     * Creates a new actor in data storage
     * @param firstNameEn first name in English
     * @param lastNameEn last name in English
     * @param firstNameRu first name in Russian
     * @param lastNameRu last name in Russian
     * @return id of a new inserted actor in data storage
     * @throws ServiceException
     */
    int addActor(String firstNameEn, String lastNameEn, String firstNameRu, String lastNameRu) throws ServiceException;

    /**
     * Returns {@link List} of 4 {@link Actor} objects
     * required by current page number
     * @param language specify concrete data's language
     * @param currentPageNumber a number of current page with actors
     * @return {@link List} of {@link Actor} objects
     * @throws ServiceException
     */
    List<Actor> getAllLimitedActors(String language,int currentPageNumber) throws ServiceException;
}
