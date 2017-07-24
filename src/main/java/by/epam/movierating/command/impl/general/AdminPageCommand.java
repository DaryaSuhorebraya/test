package by.epam.movierating.command.impl.general;

import by.epam.movierating.bean.Genre;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.service.GenreService;
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
 * Redirects to admin page.
 */
public class AdminPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(AdminPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        ServiceFactory serviceFactory = new ServiceFactory();
        GenreService genreService = serviceFactory.getGenreService();

        try {
            List<Genre> genreList = genreService.getAllGenres(language);
            request.setAttribute(AttributeName.GENRES, genreList);
            request.getRequestDispatcher(JSPPageName.ADMIN_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
