package by.epam.movierating.dao;

import by.epam.movierating.bean.Movie;
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
public class MovieDAOImplTest {
    private static MovieDAO movieDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        movieDAO = daoFactory.getMovieDAO();
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
    public void getAllMoviesTest() {
        try {
            List<Movie> movieList = movieDAO.getAllMovies(LANGUAGE_EN);
            Assert.assertNotNull(movieList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getMoviesByCountryCodeTest() {
        try {
            List<Movie> movieList = movieDAO.getMoviesByCountry("US",LANGUAGE_EN);
            Assert.assertNotNull(movieList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getMovieByIdTest() {
        try {
            Movie movie = movieDAO.getMovieById(1,LANGUAGE_EN);
            Assert.assertNotNull(movie);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void deleteMovieTest() {
        try {
            String titleEn = "Test";
            String titleRu = "Тест";
            int releaseYear=1999;
            String descriptionEn = "TestTest";
            String descriptionRu = "ТестТест";
            int idMovie=movieDAO.addMovie(titleEn,titleRu,releaseYear,descriptionEn,descriptionRu);
            List<Movie> movieList = movieDAO.getAllMovies(LANGUAGE_EN);
            Assert.assertNotNull(movieList);

            int prevSize = movieList.size();

            movieDAO.deleteMovie(idMovie);
            movieList = movieDAO.getAllMovies(LANGUAGE_EN);
            Assert.assertNotEquals(prevSize, movieList.size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addMovieTest() {
        try {
            String titleEn = "Test";
            String titleRu = "Тест";
            int releaseYear=1999;
            String descriptionEn = "TestTest";
            String descriptionRu = "ТестТест";
            int idMovie=movieDAO.addMovie(titleEn,titleRu,releaseYear,descriptionEn,descriptionRu);
            Movie movie = movieDAO.getMovieById(idMovie,LANGUAGE_EN);
            Assert.assertNotNull(movie);
            Assert.assertEquals(titleEn,movie.getTitle());
            Assert.assertEquals(descriptionEn,movie.getDescription());
            Assert.assertEquals(releaseYear,movie.getReleaseYear());

            movie = movieDAO.getMovieById(idMovie,LANGUAGE_RU);
            Assert.assertNotNull(movie);
            Assert.assertEquals(titleRu,movie.getTitle());
            Assert.assertEquals(descriptionRu,movie.getDescription());

            movieDAO.deleteMovie(idMovie);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void updateMovieTest() {
        try {
            List<Movie> movieList = movieDAO.getAllMovies(LANGUAGE_EN);
            Assert.assertNotNull(movieList);
            Movie actualMovie = movieList.get(0);
            int idActor = actualMovie.getId();

            String titleEn = "Test";
            String releaseYear="1999";
            String descriptionEn = "TestTest";

            movieDAO.editMovieField(idActor, "title_", titleEn, LANGUAGE_EN);
            movieDAO.editMovieField(idActor, "release_year", releaseYear, LANGUAGE_EN);
            movieDAO.editMovieField(idActor, "description_", descriptionEn, LANGUAGE_EN);
            Movie updatedMovie = movieDAO.getMovieById(idActor, LANGUAGE_EN);
            Assert.assertEquals(titleEn, updatedMovie.getTitle());
            Assert.assertEquals(descriptionEn, updatedMovie.getDescription());
            Assert.assertEquals(Integer.parseInt(releaseYear), updatedMovie.getReleaseYear());

            movieDAO.editMovieField(idActor, "title_", actualMovie.getTitle(), LANGUAGE_EN);
            movieDAO.editMovieField(idActor, "release_year", String.valueOf(actualMovie.getReleaseYear()), LANGUAGE_EN);
            movieDAO.editMovieField(idActor, "description_", actualMovie.getDescription(), LANGUAGE_EN);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addGenreForMovieTest() {
        try {
            List<Movie> movieList=movieDAO.getAllMovies(LANGUAGE_EN);
            int idMovie=movieList.get(0).getId();
            String genreName="sport";

            movieList=movieDAO.getMoviesByGenreName(genreName,LANGUAGE_EN);
            int prevSize=movieList.size();
            movieDAO.addGenreForMovie(idMovie,genreName,LANGUAGE_EN);

            movieList=movieDAO.getMoviesByGenreName(genreName,LANGUAGE_EN);
            Assert.assertNotEquals(prevSize,movieList.size());

            movieDAO.deleteGenreForMovie(genreName,LANGUAGE_EN,idMovie);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addCountryForMovieTest() {
        try {
            List<Movie> movieList=movieDAO.getAllMovies(LANGUAGE_EN);
            int idMovie=movieList.get(0).getId();
            String countryName="Anguilla";
            String countryCode="AI";

            movieList=movieDAO.getMoviesByCountry(countryCode,LANGUAGE_EN);
            int prevSize=movieList.size();
            movieDAO.addCountryForMovie(idMovie,countryName,LANGUAGE_EN);

            movieList=movieDAO.getMoviesByCountry(countryCode,LANGUAGE_EN);
            Assert.assertNotEquals(prevSize,movieList.size());

            movieDAO.deleteCountryForMovie(countryName,LANGUAGE_EN,idMovie);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
