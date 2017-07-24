package by.epam.movierating.service.impl;

import by.epam.movierating.bean.User;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.dao.UserDAO;
import by.epam.movierating.dao.exception.DAOException;
import by.epam.movierating.dao.factory.DAOFactory;
import by.epam.movierating.service.UserService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongDataException;
import by.epam.movierating.service.util.ServiceUtil;
import by.epam.movierating.service.util.Validator;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Provides a business-logic with the {@link User} entity.
 */
public class UserServiceImpl implements UserService {
    /**
     * Returns a user who match by the login and password
     * @param login a login of a user for search
     * @param password a password of a user for search
     * @return {@link User} object or {@code null}
     * @throws ServiceException
     * @throws ServiceWrongDataException if a login or password are incorrect
     */
    @Override
    public User login(String login, byte[] password) throws ServiceException {
        Validator.validateLogin(login);
        Validator.validatePassword(password);
        User user;
        String encodePassword = ServiceUtil.encodePassword(password);
        System.out.println(encodePassword);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.getUserByLogin(login);
            if (user == null) {
                throw new ServiceWrongDataException("Wrong login");
            }
            if (!user.getPassword().equals(encodePassword)) {
                throw new ServiceWrongDataException("Wrong password");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Creates a new user in data storage
     * @param login a login of a new user
     * @param password a password of a new user
     * @param confirmPassword a confirm of the password of a new user
     * @param email a email of a new user
     * @param firstName a first name if a new user
     * @param lastName a last name of a new user
     * @return {@link User} object
     * @throws ServiceWrongDataException
     * @throws ServiceException
     */
    @Override
    public User register(String login, byte[] password, byte[] confirmPassword,
                         String email, String firstName, String lastName)
            throws ServiceException {
        Validator.validateLogin(login);
        Validator.validatePassword(password, confirmPassword);
        Validator.validateEmail(email);
        Validator.validateStringData(firstName, lastName);
        String encodePassword = ServiceUtil.encodePassword(password);
        User user;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.register(login, encodePassword, new Date(),
                    email, firstName, lastName);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Returns all users
     * @return {@link List} of {@link User} objects
     * @throws ServiceException
     */
    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> userList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            userList = userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    /**
     * Returns a user by its id
     * @param idUser an id of a user for search
     * @return {@link User} object
     * @throws ServiceException
     */
    @Override
    public User getUserById(int idUser) throws ServiceException {
        Validator.validateIntData(idUser);
        User user;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.getUserById(idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Updates a user in data storage
     * @param idUser an id of a user that has to be updated
     * @param login a new login of a user
     * @param firstName a new first name of a user
     * @param lastName a new last name of a user
     * @param email a new email of a user
     * @param dateRegister a new register date of a user
     * @param status a new status of a user
     * @param isAdminString determines admin status
     * @param isBannedString determines ban status
     * @throws ServiceException
     */
    @Override
    public void editUser(int idUser, String login, String firstName,
                         String lastName, String email, String dateRegister,
                         String status, String isAdminString, String isBannedString)
            throws ServiceException {
        Validator.validateIntData(idUser);
        Validator.validateEmail(email);
        Validator.validateLogin(login);
        Validator.validateStringData(firstName, lastName, status);
        Date date = Validator.validateDate(dateRegister);
        boolean isAdmin = Validator.validateAdminString(isAdminString);
        boolean isBanned = Validator.validateBannedString(isBannedString);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            userDAO.editUser(idUser, login, firstName, lastName,
                    email, date, status, isAdmin, isBanned);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes a user in data storage
     * @param idUser an id of a user that has to be deleted
     * @return {@code true} if a user was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean deleteUser(int idUser) throws ServiceException {
        Validator.validateIntData(idUser);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            return userDAO.deleteUser(idUser);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Changes ban status of a user
     * @param idUser an id of a user that has to be updated
     * @param status a new ban status of a user
     * @return {@code true} if a user was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    @Override
    public boolean changeBanStatus(int idUser, String status)
            throws ServiceException {
        Validator.validateIntData(idUser);
        Validator.validateBanStatus(status);
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            if (status.equals(ParameterName.BAN_EN)
                    || status.equals(ParameterName.BAN_RU)) {
                return userDAO.banUser(idUser);
            } else {
                return userDAO.unbanUser(idUser);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns a statistics of registered user on last month
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws ServiceException
     */
    @Override
    public List<StaticticsDTO> getMonthUserCount() throws ServiceException {
        List<StaticticsDTO> staticticsDTOList;
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            staticticsDTOList = userDAO.getMonthUserCount();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return staticticsDTOList;
    }

    /**
     * Returns a user by its login
     * @param login a login of a user for search
     * @return {@link User} object
     * @throws ServiceException
     */
    @Override
    public User getUserByLogin(String login) throws ServiceException {
        Validator.validateLogin(login);
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            return userDAO.getUserByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
