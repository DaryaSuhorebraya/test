package by.epam.movierating.command.impl.actor;

import by.epam.movierating.bean.Actor;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.service.ActorService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of Command {@link Command}.
 * Services the receiving list of all actors.
 */
public class ViewAllActorsCommand implements Command {
    private static final Logger logger = Logger.getLogger(ViewAllActorsCommand.class);
    private static final int DEFAULT_PAGE_NUMBER=1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        int currentPageNumber;
        String currentPage=request.getParameter(ParameterName.CURRENT_PAGE_NUMBER);
        currentPageNumber = currentPage == null ? DEFAULT_PAGE_NUMBER : Integer.parseInt(currentPage);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        try {
            ActorService actorService = serviceFactory.getActorService();
            List<Actor> actorList = actorService.getAllLimitedActors(language, currentPageNumber);

            request.setAttribute(AttributeName.ACTORS, actorList);
            request.setAttribute(AttributeName.CURRENT_PAGE_NUMBER, currentPageNumber);

            request.getRequestDispatcher(JSPPageName.ACTORS_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
