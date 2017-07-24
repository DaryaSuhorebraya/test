package by.epam.movierating.dao;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ActorDAOImplTest {
    private static ActorDAO actorDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        actorDAO = daoFactory.getActorDAO();
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
    public void addActorTest() {
        String firstNameEn = "Test";
        String lastNameEn = "Tested";
        String firstNameRu = "Тест";
        String lastNameRu = "Тестирович";

        try {
            int id = actorDAO.addActor(firstNameEn, lastNameEn, firstNameRu, lastNameRu);
            List<Actor> actorList = actorDAO.getAllActors(LANGUAGE_EN);
            Actor actor = actorList.get(actorList.size() - 1);
            Assert.assertEquals(firstNameEn, actor.getFirstName());
            Assert.assertEquals(lastNameEn, actor.getLastName());

            actorList = actorDAO.getAllActors(LANGUAGE_RU);
            actor = actorList.get(actorList.size() - 1);

            Assert.assertEquals(firstNameRu, actor.getFirstName());
            Assert.assertEquals(lastNameRu, actor.getLastName());
            actorDAO.deleteActor(id);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getActorsNotInMovieTest() {
        try {
            List<Actor> actorList = actorDAO.getActorsNotInMovie(1, LANGUAGE_EN);
            Assert.assertNotNull(actorList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllActorsTest() {
        try {
            List<Actor> actorList = actorDAO.getAllActors(LANGUAGE_EN);
            Assert.assertNotNull(actorList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateActorTest() {
        try {
            List<Actor> actorList = actorDAO.getAllActors(LANGUAGE_EN);
            Assert.assertNotNull(actorList);
            Actor actualActor = actorList.get(0);
            int idActor = actualActor.getId();

            String firstName = "Test";
            String lastName = "Testes";
            actorDAO.editActor(idActor, firstName, lastName, LANGUAGE_EN);
            Actor updatedActor = actorDAO.getActorById(idActor, LANGUAGE_EN);
            Assert.assertEquals(firstName, updatedActor.getFirstName());
            Assert.assertEquals(lastName, updatedActor.getLastName());

            actorDAO.editActor(idActor, actualActor.getFirstName(),
                    actualActor.getLastName(), LANGUAGE_EN);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteActorTest() {
        try {
            String firstNameEn = "Test";
            String lastNameEn = "Tested";
            String firstNameRu = "Тест";
            String lastNameRu = "Тестирович";
            int idActor=actorDAO.addActor(firstNameEn,lastNameEn,firstNameRu,lastNameRu);
            List<Actor> actorList = actorDAO.getAllActors(LANGUAGE_EN);
            Assert.assertNotNull(actorList);

            int prevSize = actorList.size();

            actorDAO.deleteActor(idActor);
            actorList = actorDAO.getAllActors(LANGUAGE_EN);
            Assert.assertNotEquals(prevSize, actorList.size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getLimitedActorsTest() {
        try {
            List<Actor> actorList = actorDAO.getAllLimitedActors(LANGUAGE_EN,1);
            Assert.assertNotNull(actorList);
            Assert.assertEquals(3,actorList.size());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


}
