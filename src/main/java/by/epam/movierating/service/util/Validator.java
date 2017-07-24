package by.epam.movierating.service.util;

import by.epam.movierating.service.exception.ServiceWrongDataException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Provides validation of data
 */
public class Validator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.,_%+-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}$";
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final int MAX_LOGIN_LENGTH = 25;

    private static final String LANGUAGE_EN="en_EN";
    private static final String LANGUAGE_RU="ru_RU";
    private static final String BAN_EN="Ban";
    private static final String UNBAN_EN="Unban";
    private static final String BAN_RU="Поставить бан";
    private static final String UNBAN_RU="Отменить бан";
    private static final String YES_RU="Да ";
    private static final String NO_RU="Нет ";
    private static final String YES_EN="Yes ";
    private static final String NO_EN="No ";
    private static final String DATE_FORMAT="yyyy-mm-dd";

    /**
     * Checks the given string which represents the email
     * for compliance with the {@link Validator#EMAIL_PATTERN} pattern
     * and that this string is not empty
     * @param email string which represents the email
     * @throws ServiceWrongDataException if given string
     *         isn't matches to the email pattern or is empty
     */
    public static void validateEmail(String email)
            throws ServiceWrongDataException {
        if(email == null || email.isEmpty() || !email.matches(EMAIL_PATTERN)){
            throw new ServiceWrongDataException("Invalid email");
        }
    }

    /**
     * Checks the given string for compliance with the allowed
     * languages {@link Validator#LANGUAGE_EN}, {@link Validator#LANGUAGE_RU}
     * @param language string which represents the language
     * @throws ServiceWrongDataException if given string
     *         isn't matches to the allowed languages
     */
    public static void validateLanguage(String language)
            throws ServiceWrongDataException{
        if (!language.equals(LANGUAGE_EN)&&!language.equals(LANGUAGE_RU)){
            throw new ServiceWrongDataException("No such allowed language");
        }
    }

    /**
     * Checks the given string which represents the password
     * for compliance with the max, min and not empty rules
     * @param password representation of the password for check
     * @throws ServiceWrongDataException
     */
    public static void validatePassword(byte[] password)
            throws ServiceWrongDataException {
        if(password == null || password.length>MAX_PASSWORD_LENGTH
                || password.length<MIN_PASSWORD_LENGTH){
            System.out.println(password);
            throw new ServiceWrongDataException("Invalid password");
        }
    }

    /**
     * Checks password and its confirmation on equals
     * @param password password for check
     * @param confirmPassword confirmation of this password
     * @throws ServiceWrongDataException if password and its confirmation
     * is not equals
     */
    public static void validatePassword(byte[] password, byte[] confirmPassword)
            throws ServiceWrongDataException {
        validatePassword(password);
        if (!Arrays.equals(password,confirmPassword)){
            throw new ServiceWrongDataException("Confirmed password" +
                    " does not equal to input password");
        }
    }

    /**
     * Checks the string for compliance with the max length and
     * on not be empty
     * @param login string which represents the login
     * @throws ServiceWrongDataException if login isn't matches
     * to the allowed rules
     */
    public static void validateLogin(String login)
            throws ServiceWrongDataException {
        if(login == null || login.isEmpty()
                || login.length() > MAX_LOGIN_LENGTH){
            throw new ServiceWrongDataException("Invalid login");
        }
    }

    /**
     * Checks strings on not be empty
     * @param stringData array of strings
     * @throws ServiceWrongDataException if string is empty
     */
    public static void validateStringData(String... stringData)
            throws ServiceWrongDataException {
        for (String data: stringData
             ) {
            if(data == null || data.isEmpty()){
                throw new ServiceWrongDataException("Invalid data"+data);
            }
        }
    }

    /**
     * Checks int values on be non-zero or less than zero
     * @param intData array of int values
     * @throws ServiceWrongDataException
     */
    public static void validateIntData(int... intData)
            throws ServiceWrongDataException{
        for (int data: intData
             ) {
            if (data==0 ||data<0 ){
                throw new ServiceWrongDataException("Incorrect data"+data);
            }
        }
    }

    /**
     * Checks the input string for compliance
     * with the {@link Validator#DATE_FORMAT} pattern
     * and returns parsed date string to {@link Date}
     * @param date string which represents the date
     * @return parsed date
     * @throws ServiceWrongDataException if date format id incorrect
     */
    public static Date validateDate(String date)
            throws ServiceWrongDataException{
        Date dateRegister;
        try {
            DateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
            dateRegister=dateFormat.parse(date);
        } catch (ParseException e) {
            throw new ServiceWrongDataException("Incorrect date format"+date);
        }
        return dateRegister;
    }

    /**
     * Checks the input string for compliance
     * with the allowed strings mean values of right admin
     * condition
     * @param isAdminString string for check
     * @return {@code true} if admin string is right
     *         and {@code false} otherwise
     * @throws ServiceWrongDataException if admin string is invalid
     */
    public static boolean validateAdminString(String isAdminString)
            throws ServiceWrongDataException{
        switch (isAdminString){
            case YES_EN: {
                return true;
            }
            case YES_RU: {
                return true;
            }
            case NO_EN: {
                return false;
            }
            case NO_RU: {
                return false;
            }
            default: {
                throw new ServiceWrongDataException("Invalid value" +
                        " for admin string"+isAdminString);
            }
        }
    }

    /**
     * Checks the input string for compliance
     * with the allowed strings mean values of right ban
     * condition
     * @param isBannedString string for check
     * @return {@code true} if ban string is right
     *         and {@code false} otherwise
     * @throws ServiceWrongDataException if ban string is invalid
     */
    public static boolean validateBannedString(String isBannedString)
            throws ServiceWrongDataException{
        switch (isBannedString){
            case YES_EN: {
                return true;
            }
            case YES_RU: {
                return true;
            }
            case NO_EN: {
                return false;
            }
            case NO_RU: {
                return false;
            }
            default: {
                throw new ServiceWrongDataException("Invalid value" +
                        " for banned string: "+isBannedString);
            }
        }
    }

    /**
     * Checks the input string for compliance
     * with the ban and unban values
     * @param status string for check
     * @throws ServiceWrongDataException if ban status is invalid
     */
    public static void validateBanStatus(String status)
            throws ServiceWrongDataException{
        if(!status.equals(BAN_EN) && !status.equals(UNBAN_EN)
                && !status.equals(BAN_RU)&& !status.equals(UNBAN_RU)){
            throw new ServiceWrongDataException("Invalid value" +
                    " of a ban status: "+status);
        }
    }

}
