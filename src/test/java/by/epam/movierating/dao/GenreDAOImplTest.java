package by.epam.movierating.dao;

import by.epam.movierating.bean.Genre;
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
public class GenreDAOImplTest {
    private static GenreDAO genreDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        genreDAO = daoFactory.getGenreDAO();
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
    public void getAllActorsTest() {
        try {
            List<Genre> genreList = genreDAO.getAllGenres(LANGUAGE_EN);
            Assert.assertNotNull(genreList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addActorTest() {
        String nameEn = "Test";
        String nameRu = "Тест";

        try {
            int id = genreDAO.addGenre(nameRu,nameEn);
            List<Genre> genreList = genreDAO.getAllGenres(LANGUAGE_EN);
            Genre genre = genreList.get(genreList.size() - 1);
            Assert.assertEquals(nameEn, genre.getName());

            genreList = genreDAO.getAllGenres(LANGUAGE_RU);
            genre = genreList.get(genreList.size() - 1);

            Assert.assertEquals(nameRu, genre.getName());
            genreDAO.deleteGenre(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void updateActorTest() {
        try {
            List<Genre> genreList = genreDAO.getAllGenres(LANGUAGE_EN);
            Genre actualGenre = genreList.get(0);
            int idGenre = actualGenre.getId();

            String name = "Test";
            genreDAO.editGenre(idGenre, name, LANGUAGE_EN);
            Genre updatedGenre = genreDAO.getGenreById(idGenre, LANGUAGE_EN);
            Assert.assertEquals(name, updatedGenre.getName());

            genreDAO.editGenre(idGenre, actualGenre.getName(), LANGUAGE_EN);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void deleteActorTest() {
        try {
            String nameEn = "Test";
            String nameRu = "Тест";
            int idGenre = genreDAO.addGenre(nameRu,nameEn);
            List<Genre> genreList = genreDAO.getAllGenres(LANGUAGE_EN);
            Assert.assertNotNull(genreList);

            int prevSize = genreList.size();

            genreDAO.deleteGenre(idGenre);
            genreList = genreDAO.getAllGenres(LANGUAGE_EN);
            Assert.assertNotEquals(prevSize, genreList.size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
