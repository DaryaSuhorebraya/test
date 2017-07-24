package by.epam.movierating.dao;

import by.epam.movierating.bean.Genre;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.dao.exception.DAOException;

import java.util.List;

/**
 * Provides a DAO-logic for the {@link Genre} entity in data storage
 */
public interface GenreDAO {
    /**
     * Returns all genres
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    List<Genre> getAllGenres(String language) throws DAOException;

    /**
     * Returns genres that are in use
     * (there are relations between genres and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    List<Genre> getAllActiveGenres(String language) throws DAOException;

    /**
     * Deletes a genre from the data storage
     * @param idGenre genre id that has to be deleted
     * @return {@code true} if genre was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteGenre(int idGenre) throws DAOException;

    /**
     * Updates the genre in data storage
     * @param idGenre genre id which has to be updated
     * @param name a new name of a genre
     * @param language a language of data
     * @return {@code true} if genre was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean editGenre(int idGenre, String name, String language) throws DAOException;

    /**
     * Returns {@link List} of genres which has reference
     * to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    List<Genre> getGenresByIdMovie(int idMovie, String language) throws DAOException;

    /**
     * Returns {@link List} of genres which has not
     * reference to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws DAOException
     */
    List<Genre> getGenresNotInMovie(int idMovie, String language) throws DAOException;

    /**
     * Returns a distribution between genres
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws DAOException
     */
    List<StaticticsDTO> getGenreStatistics(String language) throws DAOException;

    /**
     * Creates a new genre in data storage
     * @param nameRu a name of new genre in Russian
     * @param nameEn a name of new genre in English
     * @return id of a new inserted genre in data storage
     * @throws DAOException
     */
    int addGenre(String nameRu, String nameEn) throws DAOException;

    /**
     * Returns genre object by its id in data storage
     * @param idGenre an id of the genre for search
     * @param language a language for data selection
     * @return {@link Genre} object
     * @throws DAOException
     */
    Genre getGenreById(int idGenre, String language) throws DAOException;
}
