package by.epam.movierating.command.impl.admin;

import by.epam.movierating.bean.User;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
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
 * Services the receiving the user.
 */
public class ViewUserCommand implements Command {
    private final static Logger logger = Logger.getLogger(ViewUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        HttpSession session = request.getSession(true);

        int idUser = Integer.parseInt(request.getParameter(ParameterName.USER_ID));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        try {
            User user = userService.getUserById(idUser);
            request.setAttribute(AttributeName.USER, user);
            request.getRequestDispatcher(JSPPageName.USER_INFO_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }

    }
}
