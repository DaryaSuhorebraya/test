package by.epam.movierating.dao;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link Actor} entity in data storage
 */
public interface ActorDAO {
    /**
     * Returns {@link List} of actors which has reference to the required movie id
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    List<Actor> getActorsByMovieId(int idMovie, String language) throws DAOException;

    /**
     * Returns {@link List} of {@link Actor} objects
     * which do not have reference to the concrete movie
     * @param idMovie specify concrete movie id for search
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    List<Actor> getActorsNotInMovie(int idMovie, String language) throws DAOException;

    /**
     * Returns all actors by given language
     * @param language specify concrete one for search
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    List<Actor> getAllActors(String language) throws DAOException;

    /**
     * Returns actor which matches by its id
     * @param idActor an id of this actor to search
     * @param language specify concrete one for search
     * @return {@link Actor} object
     * @throws DAOException
     */
    Actor getActorById(int idActor, String language) throws DAOException;

    /**
     * Deletes an actor from the data storage
     * @param idActor actor id that has to be deleted
     * @return {@code true} if actor was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteActor(int idActor) throws DAOException;

    /**
     * Updates the actor in data storage
     * @param idActor actor id which has to be updated
     * @param firstName a new first name of an actor
     * @param lastName a new last name of an actor
     * @param language a language of data
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean editActor(int idActor, String firstName, String lastName, String language) throws DAOException;

    /**
     * Sets a new image path to the {@link Actor} object
     * @param idActor actor id which has to be updated
     * @param filePath a new file path to an image
     * @return {@code true} if actor was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean uploadActorImage(int idActor, String filePath) throws DAOException;

    /**
     * Creates a new actor in data storage
     * @param firstNameEn first name in English
     * @param lastNameEn last name in English
     * @param firstNameRu first name in Russian
     * @param lastNameRu last name in Russian
     * @return id of a new inserted actor in data storage
     * @throws DAOException
     */
    int addActor(String firstNameEn, String lastNameEn, String firstNameRu, String lastNameRu) throws DAOException;

    /**
     * Returns {@link List} of 4 {@link Actor} objects
     * required by current page number
     * @param language specify concrete data's language
     * @param currentPageNumber a number of current page with actors
     * @return {@link List} of {@link Actor} objects
     * @throws DAOException
     */
    List<Actor> getAllLimitedActors(String language, int currentPageNumber) throws DAOException;
}
