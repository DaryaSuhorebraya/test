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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Implementation of Command {@link Command}.
 * Services the changing ban status of the user.
 * Awaited the request was obtained by ajax.
 */
public class ChangeBanStatusCommand implements Command {
    private static final Logger logger = Logger.getLogger(ChangeBanStatusCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        response.setContentType("text/plain");
        int userId = Integer.parseInt(request.getParameter(ParameterName.USER_ID));
        String status = request.getParameter(ParameterName.STATUS);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            boolean result = userService.changeBanStatus(userId, status);
            response.getWriter().print(result);
        } catch (ServiceException e) {
            logger.error(e);
            response.getWriter().print(false);
        }
    }
}
