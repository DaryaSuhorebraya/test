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
 * Services the deleting actor for movie.
 * Awaited the request was obtained by ajax.
 */
public class DeleteActorForMovieCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteActorForMovieCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        String firstName = request.getParameter(ParameterName.FIRSTNAME);
        String lastName = request.getParameter(ParameterName.LASTNAME);
        int movieId = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();
        try {
            boolean result = movieService.deleteActorForMovie(firstName, lastName, language, movieId);
            response.getWriter().print(result);
        } catch (ServiceException e) {
            logger.error(e);
            response.getWriter().print(false);
        }
    }
}
