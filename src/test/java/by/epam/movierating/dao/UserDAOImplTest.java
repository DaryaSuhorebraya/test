package by.epam.movierating.dao;

import by.epam.movierating.bean.User;
import by.epam.movierating.dao.connectionpool.ConnectionPool;
import by.epam.movierating.dao.exception.ConnectionPoolException;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by Даша on 04.05.2017.
 */
public class UserDAOImplTest {
    private static UserDAO userDAO;
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @BeforeClass
    public static void init() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        userDAO = daoFactory.getUserDAO();
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
    public void getAllUsersTest() {
        try {
            List<User> userList = userDAO.getAllUsers();
            Assert.assertNotNull(userList);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerTest() {
        String login = "Test";
        String password = "Tested";
        Date date = new Date();
        String email = "asdasd.rr@gmail.com";
        String firstName = "Тест";
        String lastName = "Тестирович";

        try {
            User newUser = userDAO.register(login, password, date, email, firstName, lastName);

            User user = userDAO.getUserById(newUser.getId());
            Assert.assertEquals(login, user.getLogin());
            Assert.assertEquals(email, user.getEmail());
            Assert.assertEquals(firstName, user.getFirstName());
            Assert.assertEquals(lastName, user.getLastName());

            userDAO.deleteUser(newUser.getId());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUserTest() {
        try {
            List<User> userList = userDAO.getAllUsers();
            Assert.assertNotNull(userList);
            User actualUser = userList.get(0);
            int idUser = actualUser.getId();

            String login = "Test";
            Date date = new Date();
            String email = "asdasd.rr@gmail.com";
            String firstName = "Тест";
            String lastName = "Тестирович";
            String status = "Newer";
            boolean idAdmin = false;
            boolean isBanned = false;
            userDAO.editUser(idUser, login, firstName, lastName, email, date, status, idAdmin, isBanned);
            User updatedUser = userDAO.getUserById(idUser);
            Assert.assertEquals(login, updatedUser.getLogin());
            Assert.assertEquals(email, updatedUser.getEmail());
            Assert.assertEquals(firstName, updatedUser.getFirstName());
            Assert.assertEquals(lastName, updatedUser.getLastName());
            Assert.assertEquals(status, updatedUser.getStatus());
            Assert.assertFalse(updatedUser.isAdmin());
            Assert.assertFalse(updatedUser.isBanned());

            userDAO.editUser(idUser,actualUser.getLogin(), actualUser.getFirstName(),
                    actualUser.getLastName(), actualUser.getEmail(),actualUser.getDateRegister(),
                    actualUser.getStatus(), actualUser.isAdmin(), actualUser.isBanned());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteUserTest() {
        String login = "Test";
        String password = "Tested";
        Date date = new Date();
        String email = "asdasd.rr@gmail.com";
        String firstName = "Тест";
        String lastName = "Тестирович";

        try {
            User newUser = userDAO.register(login, password, date, email, firstName, lastName);

            User user = userDAO.getUserById(newUser.getId());
            List<User> userList = userDAO.getAllUsers();
            Assert.assertNotNull(userList);

            int prevSize = userList.size();

            userDAO.deleteUser(user.getId());
            userList = userDAO.getAllUsers();
            Assert.assertNotEquals(prevSize, userList.size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void banUserTest() {
        try {
            List<User> userList = userDAO.getAllUsers();
            Assert.assertNotNull(userList);
            User actualUser = userList.get(userList.size()-1);
            int idUser = actualUser.getId();
            userDAO.banUser(idUser);

            User user=userDAO.getUserById(idUser);
            Assert.assertTrue(user.isBanned());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void unbanUserTest() {
        try {
            List<User> userList = userDAO.getAllUsers();
            Assert.assertNotNull(userList);
            User actualUser = userList.get(userList.size()-1);
            int idUser = actualUser.getId();
            userDAO.unbanUser(idUser);

            User user=userDAO.getUserById(idUser);
            Assert.assertFalse(user.isBanned());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
