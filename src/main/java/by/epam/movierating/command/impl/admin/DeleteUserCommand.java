package by.epam.movierating.command.impl.admin;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.UserService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of Command {@link Command}.
 * Services the deleting the user.
 * Awaited the request was obtained by ajax.
 */
public class DeleteUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        int isUser = Integer.parseInt(request.getParameter(ParameterName.USER_ID));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            boolean result = userService.deleteUser(isUser);
            response.getWriter().print(result);
        } catch (ServiceException e) {
            logger.error(e);
            response.getWriter().print(false);
        }

    }
}
