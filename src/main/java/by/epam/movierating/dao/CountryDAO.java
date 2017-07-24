package by.epam.movierating.dao;

import by.epam.movierating.bean.Country;
import by.epam.movierating.dao.exception.DAOException;

import java.util.List;

/**
 *  Provides a DAO-logic for the {@link Country} entity in data storage
 */
public interface CountryDAO {
    /**
     * Updates country in data storage
     * @param countryCode code of country to update
     * @param name new name of this country
     * @param language language of data
     * @throws DAOException
     */
    void updateCountry(String countryCode, String name, String language) throws DAOException;

    /**
     * Returns all countries
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    List<Country> getAllCountries(String language) throws DAOException;

    /**
     * Returns countries that are in use
     * (there are relations between countries and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    List<Country> getAllActiveCountries(String language) throws DAOException;

    /**
     * Returns country by its code
     * @param countryCode code of the country for search
     * @param language a language for data selection
     * @return @link Country} object
     * @throws DAOException
     */
    Country getCountryByCode(String countryCode, String language) throws DAOException;

    /**
     * Returns countries that match by movie id
     * @param movieId movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    List<Country> getCountriesByMovieId(int movieId, String language) throws DAOException;

    /**
     * Returns countries that do not match by movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    List<Country> getCountriesNotInMovie(int idMovie, String language) throws DAOException;


}
