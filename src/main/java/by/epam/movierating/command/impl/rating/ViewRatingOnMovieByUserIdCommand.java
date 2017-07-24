package by.epam.movierating.command.impl.rating;

import by.epam.movierating.bean.Rating;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.RatingService;
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
 * Services the receiving the rating on movie by user id.
 * Awaited the request was obtained by ajax.
 */
public class ViewRatingOnMovieByUserIdCommand implements Command {
    private static final Logger logger = Logger.getLogger(ViewRatingOnMovieByUserIdCommand.class);
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idMovie = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));
        int idUser = Integer.parseInt(request.getParameter(ParameterName.USER_ID));

        ServiceFactory serviceFactory = new ServiceFactory();
        RatingService ratingService = serviceFactory.getRatingService();
        try {
            Rating rating = ratingService.getRatingOnMovieByUserId(idMovie, idUser);
            String json = new Gson().toJson(rating.getMark());

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);
            response.getWriter().print(json);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
