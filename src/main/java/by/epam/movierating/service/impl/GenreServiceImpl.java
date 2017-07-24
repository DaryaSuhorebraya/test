package by.epam.movierating.service.impl;

import by.epam.movierating.bean.Genre;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.dao.GenreDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.GenreService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.util.Validator;

import java.util.List;

/**
 * Provides a business-logic with the {@link Genre} entity.
 */
public class GenreServiceImpl implements GenreService {

    /**
     * Returns all genres
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    @Override
    public List<Genre> getAllGenres(String language) throws ServiceException {
        Validator.validateLanguage(language);
        List<Genre> genreList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            genreList = genreDAO.getAllGenres(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return genreList;
    }

    /**
     * Returns genres that are in use
     * (there are relations between genres and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    @Override
    public List<Genre> getAllActiveGenre(String language) throws ServiceException {
        Validator.validateLanguage(language);
        List<Genre> genreList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            genreList = genreDAO.getAllActiveGenres(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return genreList;
    }

    /**
     * Deletes a genre from the data storage
     * @param idGenre genre id that has to be deleted
     * @return {@code true} if genre was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteGenre(int idGenre) throws ServiceException {
        Validator.validateIntData(idGenre);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            return genreDAO.deleteGenre(idGenre);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates the genre in data storage
     * @param idGenre genre id which has to be updated
     * @param name a new name of a genre
     * @param language a language of data
     * @return {@code true} if genre was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean editGenre(int idGenre, String name, String language)
            throws ServiceException {
        Validator.validateIntData(idGenre);
        Validator.validateStringData(name, language);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            return genreDAO.editGenre(idGenre, name, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns {@link List} of genres which has reference to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    @Override
    public List<Genre> getGenresByIdMovie(int idMovie, String language)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateLanguage(language);
        List<Genre> genreList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            genreList = genreDAO.getGenresByIdMovie(idMovie, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return genreList;
    }

    /**
     * Returns {@link List} of genres which has not
     * reference to the required movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Genre} objects
     * @throws ServiceException
     */
    @Override
    public List<Genre> getGenresNotInMovie(int idMovie, String language)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateLanguage(language);
        List<Genre> genreList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            genreList = genreDAO.getGenresNotInMovie(idMovie, language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return genreList;
    }

    /**
     * Returns a distribution between genres
     * @param language a language for data selection
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<StaticticsDTO> getGenreStatistics(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            return genreDAO.getGenreStatistics(language);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Creates a new genre in data storage
     * @param nameRu a name of new genre in Russian
     * @param nameEn a name of new genre in English
     * @return id of a new inserted genre in data storage
     * @throws ServiceException
     */
    @Override
    public int addGenre(String nameRu, String nameEn)
            throws ServiceException {
        Validator.validateStringData(nameRu, nameEn);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            GenreDAO genreDAO = daoFactory.getGenreDAO();
            return genreDAO.addGenre(nameRu, nameEn);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}

