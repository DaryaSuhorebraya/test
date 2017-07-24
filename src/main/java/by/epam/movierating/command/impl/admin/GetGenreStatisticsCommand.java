package by.epam.movierating.command.impl.admin;

import by.epam.movierating.bean.dto.StaticticsDTO;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
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
 * Services the receiving statistics about genres.
 * Awaited the request was obtained by ajax.
 */
public class GetGenreStatisticsCommand implements Command {

    private static final Logger logger = Logger.getLogger(GetGenreStatisticsCommand.class);
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);

        ServiceFactory serviceFactory = new ServiceFactory();
        try {
            GenreService genreService=serviceFactory.getGenreService();
            List<StaticticsDTO> staticticsDTOList=genreService.getGenreStatistics(language);

            String json = new Gson().toJson(staticticsDTOList);
            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().print(json);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
