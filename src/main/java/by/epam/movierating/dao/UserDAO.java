package by.epam.movierating.dao;

import by.epam.movierating.bean.User;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.dao.exception.DAOException;

import java.util.Date;
import java.util.List;

/**
 * Provides a DAO-logic for the {@link User} entity in data storage
 */
public interface UserDAO {
    /**
     * Returns a user by its login
     * @param login a login of a user for search
     * @return {@link User} object
     * @throws DAOException
     */
    User getUserByLogin(String login) throws DAOException;

    /**
     * Creates a new user in data storage
     * @param login a login of a new user
     * @param password a password of a new user
     * @param date a register date
     * @param email a email of a new user
     * @param firstName a first name if a new user
     * @param lastName a last name of a new user
     * @return {@link User} object
     * @throws DAOException
     */
    User register(String login, String password, Date date, String email,
                  String firstName,String lastName) throws DAOException;

    /**
     * Returns all users
     * @return {@link List} of {@link User} objects
     * @throws DAOException
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * Returns a user by its id
     * @param idUser an id of a user for search
     * @return {@link User} object
     * @throws DAOException
     */
    User getUserById(int idUser) throws DAOException;

    /**
     * Updates a user in data storage
     * @param idUser an id of a user that has to be updated
     * @param login a new login of a user
     * @param firstName a new first name of a user
     * @param lastName a new last name of a user
     * @param email a new email of a user
     * @param dateRegister a new register date of a user
     * @param status a new status of a user
     * @param isAdmin determines admin status
     * @param isBanned determines ban status
     * @throws DAOException
     */
    void editUser(int idUser, String login, String firstName, String lastName,
                  String email, Date dateRegister, String status, boolean isAdmin,
                  boolean isBanned) throws DAOException;

    /**
     * Deletes a user in data storage
     * @param idUser an id of a user that has to be deleted
     * @return {@code true} if a user was deleted
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean deleteUser(int idUser) throws DAOException;

    /**
     * Bans user in data storage
     * @param idUser an id of a user that has to be banned
     * @return {@code true} if a user was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean banUser(int idUser) throws DAOException;

    /**
     * Dilutes user in data storage
     * @param idUser an id of a user that has to be unbanned
     * @return {@code true} if a user was updated
     *         and {@code false} otherwise
     * @throws DAOException
     */
    boolean unbanUser(int idUser) throws DAOException;

    /**
     * Returns a statistics of registered user on last month
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws DAOException
     */
    List<StaticticsDTO> getMonthUserCount() throws DAOException;
}
