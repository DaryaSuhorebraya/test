package by.epam.movierating.service;

import by.epam.movierating.bean.Country;
import by.epam.movierating.service.exception.ServiceException;

import java.util.List;

/**
 * Provides work with {@link Country} entity
 */
public interface CountryService {
    /**
     * Returns all countries
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    List<Country> getAllCountries(String language) throws ServiceException;

    /**
     * Returns countries that are in use
     * (there are relations between countries and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    List<Country> getAllActiveCountries(String language) throws ServiceException;

    /**
     * Returns countries that match by movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    List<Country> getCountriesByMovieId(int idMovie, String language) throws ServiceException;

    /**
     * Returns countries that do not match by movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws ServiceException
     */
    List<Country> getCountriesNotInMovie(int idMovie, String language) throws ServiceException;
}
