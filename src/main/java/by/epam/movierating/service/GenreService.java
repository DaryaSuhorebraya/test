package by.epam.movierating.service;

import by.epam.movierating.bean.Genre;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides work with {@link Genre} entity
 */
public interface GenreService {
    /**
     * Returns all genres
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    List<Genre> getAllGenres(String language) throws ServiceException;

    /**
     * Returns genres that are in use
     * (there are relations between genres and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    List<Genre> getAllActiveGenre(String language) throws ServiceException;

    /**
     * Deletes a genre from the data storage
     * @param idGenre genre id that has to be deleted
     * @return {@code true} if genre was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteGenre(int idGenre) throws ServiceException;

    /**
     * Updates the genre in data storage
     * @param idGenre genre id which has to be updated
     * @param name a new name of a genre
     * @param language a language of data
     * @return {@code true} if genre was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean editGenre(int idGenre, String name, String language) throws ServiceException;

    /**
     * Returns {@link List} of genres which has reference to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    List<Genre> getGenresByIdMovie(int idMovie, String language) throws ServiceException;

    /**
     * Returns {@link List} of genres which has not
     * reference to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    List<Genre> getGenresNotInMovie(int idMovie, String language) throws ServiceException;

    /**
     * Returns a distribution between genres
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws ServiceException
     */
    List<StaticticsDTO> getGenreStatistics(String language) throws ServiceException;

    /**
     * Creates a new genre in data storage
     * @param nameRu a name of new genre in Russian
     * @param nameEn a name of new genre in English
     * @return id of a new inserted genre in data storage
     * @throws ServiceException
     */
    int addGenre(String nameRu, String nameEn) throws ServiceException;
}
