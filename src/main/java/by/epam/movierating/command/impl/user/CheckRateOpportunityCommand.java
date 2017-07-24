package by.epam.movierating.command.impl.user;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.service.RatingService;
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
 * Services the checking the opportunity to rate movie by user.
 * Awaited the request was obtained by ajax.
 */
public class CheckRateOpportunityCommand implements Command {
    private static final Logger logger = Logger.getLogger(CheckRateOpportunityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/plain");

        if ((boolean) session.getAttribute(AttributeName.IS_BANNED)) {
            response.getWriter().print(AttributeName.IS_BANNED);
        } else {
            int idMovie = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));
            int idUser = (Integer) session.getAttribute(AttributeName.USER_ID);

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            RatingService ratingService = serviceFactory.getRatingService();
            try {
                boolean result = ratingService.checkRateOpportunity(idMovie, idUser);
                response.getWriter().print(result);
            } catch (ServiceException e) {
                logger.error(e);
                response.getWriter().print(false);
            }
        }
    }
}
