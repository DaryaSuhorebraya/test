package by.epam.movierating.dao.impl;


import by.epam.movierating.bean.Country;
import by.epam.movierating.dao.CountryDAO;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import by.epam.movierating.dao.util.DAOUtil;
import org.apache.log4j.Logger;

/**
 * {@link CountryDAO} implementation, provided DAO-logic for {@link Country} entity
 */
public class CountryDAOImpl implements CountryDAO {
    private static final String SQL_UPDATE_COUNTRY="UPDATE country SET name_=? WHERE code=?";
    private static final String SQL_GET_ALL_COUNTRIES="SELECT code" +
            ",name_ FROM country";
    private static final String SQL_GET_ALL_ACTIVE_COUNTRIES="SELECT country.code, country.name_ FROM country " +
            "INNER JOIN movie_country ON country.code=movie_country.country_code " +
            "GROUP BY country.code";
    private static final String SQL_GET_COUNTRY_BY_CODE="SELECT country.code, country.name_ FROM country WHERE code=?";
    private static final String SQL_COUNTRIES_BY_MOVIE_ID= "SELECT country.code, country.name_ FROM country "+
            "INNER JOIN movie_country ON country.code= movie_country.country_code "+
            "WHERE movie_country.id_movie=? "+
            "GROUP BY country.code";
    private static final String SQL_GET_COUNTRIES_NOT_IN_MOVIE="SELECT country.code, country.name_ FROM country " +
            "WHERE country.code NOT IN "+
            "( SELECT movie_country.country_code FROM movie_country WHERE movie_country.id_movie=? ) "+
            "GROUP BY country.code";

    private static final Logger logger=Logger.getLogger(CountryDAOImpl.class);

    /**
     * Updates country in data storage
     * @param countryCode code of country to update
     * @param name new name of this country
     * @param language language of data
     * @throws DAOException
     */
    @Override
    public void updateCountry(String countryCode, String name, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            ConnectionPool connectionPool= ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            preparedStatement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_UPDATE_COUNTRY,language));
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,countryCode);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection ",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,preparedStatement);
        }
    }

    /**
     * Returns all countries
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    @Override
    public List<Country> getAllCountries(String language) throws DAOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<Country> countryList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_ALL_COUNTRIES,language));
            countryList=setCountryInfo(resultSet);
            return countryList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection ",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns countries that are in use
     * (there are relations between countries and movies )
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    @Override
    public List<Country> getAllActiveCountries(String language) throws DAOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<Country> countryList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            statement=connection.createStatement();
            resultSet=statement.executeQuery(DAOUtil.
                    localizeStatement(SQL_GET_ALL_ACTIVE_COUNTRIES,language));
            countryList=setCountryInfo(resultSet);
            return countryList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection ",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * Returns country by its code
     * @param countryCode code of the country for search
     * @param language a language for data selection
     * @return @link Country} object
     * @throws DAOException
     */
    @Override
    public Country getCountryByCode(String countryCode, String language)
            throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Country country=null;
        try {
            ConnectionPool connectionPool= ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            preparedStatement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_COUNTRY_BY_CODE,language));
            preparedStatement.setString(1,countryCode);
            resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                country=new Country();
                country.setCode(resultSet.getString(1));
                country.setName(resultSet.getString(2));
            }
        } catch (ConnectionPoolException e){
            logger.error(e);
            throw new DAOException("Can not get a connection from pool",e);
        } catch (SQLException e){
            logger.error(e);
            throw new DAOException("Error during updating country procedure",e);
        }
        return country;
    }

    /**
     * Returns countries that match by movie id
     * @param movieId movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    @Override
    public List<Country> getCountriesByMovieId(int movieId, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Country> countryList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            preparedStatement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_COUNTRIES_BY_MOVIE_ID,language));
            preparedStatement.setInt(1,movieId);
            resultSet=preparedStatement.executeQuery();
            countryList=setCountryInfo(resultSet);
            return countryList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection ",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,preparedStatement,resultSet);
        }
    }

    /**
     * Returns countries that do not match by movie id
     * @param idMovie movie id for search
     * @param language a language for data selection
     * @return {@link List} of {@link Country} objects
     * @throws DAOException
     */
    @Override
    public List<Country> getCountriesNotInMovie(int idMovie, String language)
            throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<Country> countryList;
        try {
            ConnectionPool connectionPool=ConnectionPool.getInstance();
            connection=connectionPool.getConnection();
            preparedStatement=connection.prepareStatement(DAOUtil.
                    localizeStatement(SQL_GET_COUNTRIES_NOT_IN_MOVIE,language));
            preparedStatement.setInt(1,idMovie);
            resultSet=preparedStatement.executeQuery();
            countryList=setCountryInfo(resultSet);
            return countryList;
        } catch (ConnectionPoolException e) {
            throw new DAOException("Can not get a connection ",e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DAOUtil.close(connection,preparedStatement,resultSet);
        }
    }

    /**
     * Setups countries' information
     * @param resultSet {@link ResultSet} object contains countries' data
     * @return {@link List} of {@link Country} objects
     * @throws SQLException
     */
    private List<Country> setCountryInfo(ResultSet resultSet) throws SQLException{
        List<Country> countryList=new ArrayList<>();
        while (resultSet.next()){
            Country country=new Country();
            country.setCode(resultSet.getString(1));
            country.setName(resultSet.getString(2));
            countryList.add(country);
        }
        return countryList;
    }

}
