package by.epam.movierating.command.impl.admin;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.JSPPageName;
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
 * Services the editing the user.
 */
public class EditUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String login = request.getParameter(ParameterName.LOGIN);
        String firstName = request.getParameter(ParameterName.FIRSTNAME);
        String lastName = request.getParameter(ParameterName.LASTNAME);
        String email = request.getParameter(ParameterName.EMAIL);
        String dateRegister = request.getParameter(ParameterName.DATE_REGISTER);
        String status = request.getParameter(ParameterName.STATUS);
        String isAdmin = request.getParameter(ParameterName.IS_ADMIN);
        String isBanned = request.getParameter(ParameterName.IS_BANNED);
        int idUser = Integer.parseInt(request.getParameter(ParameterName.USER_ID));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            userService.editUser(idUser, login, firstName, lastName, email, dateRegister,
                    status, isAdmin, isBanned);
            response.sendRedirect(JSPPageName.REDIRECT_TO_USER_INFO_PAGE + idUser);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
