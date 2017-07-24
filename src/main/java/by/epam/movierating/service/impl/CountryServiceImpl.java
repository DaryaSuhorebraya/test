package by.epam.movierating.service.impl;

import by.epam.movierating.bean.Country;
import by.epam.movierating.dao.CountryDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.CountryService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.util.Validator;

import java.util.List;

/**
 * Provides a business-logic with the {@link Country} entity.
 */
public class CountryServiceImpl implements CountryService {
    /**
     * Returns all countries
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    @Override
    public List<Country> getAllCountries(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<Country> countryList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            CountryDAO countryDAO=daoFactory.getCountryDAO();
            countryList=countryDAO.getAllCountries(language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return countryList;
    }

    /**
     * Returns countries that are in use
     * (there are relations between countries and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    @Override
    public List<Country> getAllActiveCountries(String language)
            throws ServiceException {
        Validator.validateLanguage(language);
        List<Country> countryList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            CountryDAO countryDAO=daoFactory.getCountryDAO();
            countryList=countryDAO.getAllActiveCountries(language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return countryList;
    }

    /**
     * Returns countries that match by movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    @Override
    public List<Country> getCountriesByMovieId(int idMovie, String language)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateLanguage(language);
        List<Country> countryList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            CountryDAO countryDAO=daoFactory.getCountryDAO();
            countryList=countryDAO.getCountriesByMovieId(idMovie,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return countryList;
    }

    /**
     * Returns countries that do not match by movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    @Override
    public List<Country> getCountriesNotInMovie(int idMovie, String language)
            throws ServiceException {
        Validator.validateIntData(idMovie);
        Validator.validateLanguage(language);
        List<Country> countryList;
        try {
            DAOFactory daoFactory=DAOFactory.getInstance();
            CountryDAO countryDAO=daoFactory.getCountryDAO();
            countryList=countryDAO.getCountriesNotInMovie(idMovie,language);
        } catch (DAOException e){
            throw new ServiceException(e);
        }
        return countryList;
    }
}
