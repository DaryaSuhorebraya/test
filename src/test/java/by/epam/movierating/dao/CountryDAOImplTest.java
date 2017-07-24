package by.epam.movierating.dao;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.bean.Country;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by Даша on 03.05.2017.
 */
public class CountryDAOImplTest {
    private static CountryDAO countryDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        countryDAO = daoFactory.getCountryDAO();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Can not init a pool", e);
        }
    }

    @AfterClass
    public static void destroy() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.dispose();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("Can not dispose a pool", e);
        }
    }

    @Test
    public void updateCountryTest() {
        try {
            List<Country> countryList = countryDAO.getAllCountries(LANGUAGE_EN);
            Assert.assertNotNull(countryList);
            Country actualCountry = countryList.get(0);
            String code = actualCountry.getCode();

            String name = "Test";
            countryDAO.updateCountry(code, name, LANGUAGE_EN);
            Country updatedCountry = countryDAO.getCountryByCode(code, LANGUAGE_EN);
            Assert.assertEquals(name, updatedCountry.getName());

            countryDAO.updateCountry(code, actualCountry.getName(), LANGUAGE_EN);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getAllCountriesTest() {
        try {
            List<Country> countryList = countryDAO.getAllCountries(LANGUAGE_EN);
            Assert.assertNotNull(countryList);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getCountryByCodeTest() {
        try {
            Country country=countryDAO.getCountryByCode("BY",LANGUAGE_EN);
            Assert.assertNotNull(country);
            Assert.assertEquals(country.getName(),"Belarus");

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
