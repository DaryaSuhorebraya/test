package by.epam.movierating.service;

import by.epam.movierating.bean.User;
import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongDataException;

import java.util.List;

/**
 * Created by Даша on 14.02.2017.
 */
public interface UserService {
    /**
     * Returns a user who match by the login and password
     * @param login a login of a user for search
     * @param password a password of a user for search
     * @return {@link User} object or {@code null}
     * @throws ServiceException
     * @throws ServiceWrongDataException if a login or password are incorrect
     */
    User login(String login, byte[] password) throws ServiceException,ServiceWrongDataException;

    /**
     * Creates a new user in data storage
     * @param login a login of a new user
     * @param password a password of a new user
     * @param confirmPassword a confirm of the password of a new user
     * @param email a email of a new user
     * @param firstName a first name if a new user
     * @param lastName a last name of a new user
     * @return {@link User} object
     * @throws ServiceWrongDataException if input data is invalid
     * @throws ServiceException
     */
    User register(String login, byte[] password,byte[] confirmPassword, String email, String firstName, String lastName) throws ServiceWrongDataException,ServiceException;

    /**
     * Returns all users
     * @return {@link List} of {@link User} objects
     * @throws ServiceException
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Returns a user by its id
     * @param idUser an id of a user for search
     * @return {@link User} object
     * @throws ServiceException
     */
    User getUserById(int idUser) throws ServiceException;

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
     * @throws ServiceException
     */
    void editUser(int idUser,String login, String firstName, String lastName, String email,String dateRegister, String status,
                  String isAdmin, String isBanned) throws ServiceException;

    /**
     * Deletes a user in data storage
     * @param idUser an id of a user that has to be deleted
     * @return {@code true} if a user was deleted
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean deleteUser(int idUser) throws ServiceException;

    /**
     * Changes ban status of a user
     * @param idUser an id of a user that has to be updated
     * @param status a new ban status of a user
     * @return {@code true} if a user was updated
     *         and {@code false} otherwise
     * @throws ServiceException
     */
    boolean changeBanStatus(int idUser, String status) throws ServiceException;

    /**
     * Returns a statistics of registered user on last month
     * @return {@link List} of {@link StaticticsDTO} objects
     * @throws ServiceException
     */
    List<StaticticsDTO> getMonthUserCount() throws ServiceException;

    /**
     * Returns a user by its login
     * @param login a login of a user for search
     * @return {@link User} object
     * @throws ServiceException
     */
    User getUserByLogin(String login) throws ServiceException;
}
