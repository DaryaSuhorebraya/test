package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.MovieService;
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
 * Services the editing movie.
 * Awaited the request was obtained by ajax.
 */
public class EditMovieCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditMovieCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/plain");
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        int idMovie = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));
        String field = request.getParameter(ParameterName.FIELD);
        String value = request.getParameter(ParameterName.VALUE);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();
        try {
            boolean result = movieService.editMovieField(idMovie, field, value, language);
            response.getWriter().print(result);
        } catch (ServiceException e) {
            logger.error(e);
            response.getWriter().print(false);
        }
    }
}
