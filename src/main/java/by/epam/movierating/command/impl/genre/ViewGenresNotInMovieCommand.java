package by.epam.movierating.command.impl.genre;

import by.epam.movierating.bean.Genre;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.GenreService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of Command {@link Command}.
 * Services the receiving list of the genres that not include in movie.
 * Awaited the request was obtained by ajax.
 */
public class ViewGenresNotInMovieCommand implements Command {
    private static final Logger logger = Logger.getLogger(ViewGenresNotInMovieCommand.class);
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        int idMovie = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));
        ServiceFactory serviceFactory = new ServiceFactory();
        try {
            GenreService genreService = serviceFactory.getGenreService();
            List<Genre> genreList = genreService.getGenresNotInMovie(idMovie, language);
            String json = new Gson().toJson(genreList);
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().print(json);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }

    }
}
