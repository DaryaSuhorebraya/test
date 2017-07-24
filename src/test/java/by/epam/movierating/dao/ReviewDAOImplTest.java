package by.epam.movierating.dao;

import by.epam.movierating.bean.Review;
import by.epam.movierating.bean.dto.ReviewDTO;
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
public class ReviewDAOImplTest {
    private static ReviewDAO reviewDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        reviewDAO = daoFactory.getReviewDAO();
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
    public void checkRateOpportunityTest() {
        try {
            MovieDAO movieDAO=DAOFactory.getInstance().getMovieDAO();
            int idMovie=movieDAO.getAllMovies(LANGUAGE_EN).get(1).getId();
            int idUser=10;
            boolean result=reviewDAO.checkReviewOpportunity(idMovie,idUser);
            Assert.assertFalse(result);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void reviewMovieTest() {
        try {
            MovieDAO movieDAO=DAOFactory.getInstance().getMovieDAO();
            int idMovie=movieDAO.getAllMovies(LANGUAGE_EN).get(0).getId();
            int idUser=10;
            String title="Test";
            String reviewText="TestTestTest";
            reviewDAO.reviewMovie(idMovie,idUser,title,reviewText);

            ReviewDTO review=reviewDAO.getReviewsByUserId(idUser,LANGUAGE_EN).get(0);
            Assert.assertEquals(title,review.getTitle());
            Assert.assertEquals(reviewText, review.getReview());

            reviewDAO.deleteReview(idMovie,idUser);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteReviewTest() {
        try {
            MovieDAO movieDAO=DAOFactory.getInstance().getMovieDAO();
            int idMovie=movieDAO.getAllMovies(LANGUAGE_EN).get(0).getId();
            int idUser=10;
            String title="Test";
            String reviewText="TestTestTest";
            reviewDAO.reviewMovie(idMovie,idUser,title,reviewText);
            int prevSize=reviewDAO.getReviewsByUserId(idUser,LANGUAGE_EN).size();

            reviewDAO.deleteReview(idMovie,idUser);
            int currentSize=reviewDAO.getReviewsByUserId(idUser,LANGUAGE_EN).size();
            Assert.assertNotEquals(prevSize,currentSize);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getAllReviewsOrderByDateTest() {
        try {
            List<Review> reviewList = reviewDAO.getAllReviewsOrderByDate();
            Assert.assertNotNull(reviewList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}
