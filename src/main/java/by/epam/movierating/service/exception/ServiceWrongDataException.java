package by.epam.movierating.service.exception;

/**
 * Exception that can occurs in service layer when some data is incorrect
 */
public class ServiceWrongDataException extends ServiceException {
    public ServiceWrongDataException(){
        super();
    }
    public ServiceWrongDataException(String message){
        super(message);
    }
    public ServiceWrongDataException(Exception e){
        super(e);
    }
    public ServiceWrongDataException(String message, Exception e){
        super(message,e);
    }
}
