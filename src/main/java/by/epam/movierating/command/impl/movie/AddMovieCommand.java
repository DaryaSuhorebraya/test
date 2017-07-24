package by.epam.movierating.command.impl.movie;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.MovieService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of Command {@link Command}.
 * Services the adding movie.
 */
public class AddMovieCommand implements Command {
    private static final Logger logger = Logger.getLogger(AddMovieCommand.class);
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titleEn = request.getParameter(ParameterName.TITLE_EN);
        String titleRu = request.getParameter(ParameterName.TITLE_RU);
        int releaseYear = Integer.parseInt(request.getParameter(ParameterName.RELEASE_YEAR));
        String descrEn = request.getParameter(ParameterName.DESCRIPTION_EN);
        String descrRu = request.getParameter(ParameterName.DESCRIPTION_RU);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();
        try {
            int id = movieService.addMovie(titleEn, titleRu, releaseYear, descrEn, descrRu);
            String json = new Gson().toJson(id);
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().print(json);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
