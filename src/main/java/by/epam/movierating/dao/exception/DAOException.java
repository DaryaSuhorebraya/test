package by.epam.movierating.dao.exception;

/**
 * Exception that can occurs in dao layer when some error arose
 */
public class DAOException extends Exception {
    private static long serialVersionUID = 1L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }

    public DAOException(Exception e) {
        super(e);
    }
}
