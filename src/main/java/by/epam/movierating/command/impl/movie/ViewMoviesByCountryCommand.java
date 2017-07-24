package by.epam.movierating.command.impl.movie;

import by.epam.movierating.bean.Movie;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.service.MovieService;
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
 * Services the receiving list of movies by country code.
 */
public class ViewMoviesByCountryCommand implements Command {
    private static final Logger logger=Logger.getLogger(ViewMoviesByCountryCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        HttpSession session = request.getSession(true);
        String language=(String) session.getAttribute(AttributeName.LANGUAGE);
        List<Movie> movieList;

        String countryCode=request.getParameter(ParameterName.COUNTRY_CODE);
        ServiceFactory serviceFactory=ServiceFactory.getInstance();
        MovieService movieService=serviceFactory.getMovieService();
        try {
            movieList=movieService.getMoviesByCountry(countryCode,language);
            request.setAttribute(AttributeName.MOVIES,movieList);
            request.getRequestDispatcher(JSPPageName.MOVIES_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
