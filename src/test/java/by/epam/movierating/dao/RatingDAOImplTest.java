package by.epam.movierating.dao;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.bean.Rating;
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
 * Created by Даша on 04.05.2017.
 */
public class RatingDAOImplTest {
    private static RatingDAO ratingDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        ratingDAO = daoFactory.getRatingDAO();
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
    public void rateMovieTest() {

        try {
            MovieDAO movieDAO=DAOFactory.getInstance().getMovieDAO();
            int idMovie=movieDAO.getAllMovies(LANGUAGE_EN).get(0).getId();
            int idUser=10;
            int mark=9;
            ratingDAO.rateMovie(idMovie,idUser,mark);

            Rating rating=ratingDAO.getRatingOnMovieByUserId(idMovie,idUser);
            Assert.assertEquals(mark, rating.getMark());

            //TODO delete rating
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void checkRateOpportunityTest() {
        try {
            MovieDAO movieDAO=DAOFactory.getInstance().getMovieDAO();
            int idMovie=movieDAO.getAllMovies(LANGUAGE_EN).get(1).getId();
            int idUser=10;
            boolean result=ratingDAO.checkRateOpportunity(idMovie,idUser);
            Assert.assertFalse(result);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
