package by.epam.movierating.command.impl.user;

import by.epam.movierating.bean.User;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.service.UserService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.exception.ServiceWrongDataException;
import by.epam.movierating.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Implementation of Command {@link Command}.
 * Services the registration of new user.
 */
public class RegistrationCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String login = request.getParameter(ParameterName.LOGIN);
        String firstName = request.getParameter(ParameterName.FIRSTNAME);
        String lastName = request.getParameter(ParameterName.LASTNAME);
        String email = request.getParameter(ParameterName.EMAIL);
        byte[] password = request.getParameter(ParameterName.PASSWORD).getBytes();
        byte[] confirmPassword = request.getParameter(ParameterName.CONFIRM_PASSWORD).getBytes();

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            User user = userService.register(login, password, confirmPassword, email, firstName, lastName);
            session.setAttribute(AttributeName.USER_ID, user.getId());
            String pagePath = PagePathUtil.getPagePath(session);
            if (user.isAdmin()) {
                session.setAttribute(AttributeName.ROLE, AttributeName.ADMIN);
                response.sendRedirect(JSPPageName.REDIRECT_TO_ADMIN_PAGE);
            } else {
                session.setAttribute(AttributeName.ROLE, AttributeName.USER);
                response.sendRedirect(pagePath);
            }
        } catch (ServiceWrongDataException e) {
            logger.error("Wrong input data", e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
