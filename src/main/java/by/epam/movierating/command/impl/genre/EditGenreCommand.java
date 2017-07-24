package by.epam.movierating.command.impl.genre;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.GenreService;
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
 * Services the editing the genre.
 * Awaited the request was obtained by ajax.
 */
public class EditGenreCommand implements Command {
    private static final Logger logger = Logger.getLogger(EditGenreCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        HttpSession session = request.getSession(true);

        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        int idGenre = Integer.parseInt(request.getParameter(ParameterName.GENRE_ID));
        String name = request.getParameter(ParameterName.NAME);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        GenreService genreService = serviceFactory.getGenreService();
        try {
            boolean result = genreService.editGenre(idGenre, name, language);
            response.getWriter().print(result);
        } catch (ServiceException e) {
            logger.error(e);
            response.getWriter().print(false);
        }
    }
}
