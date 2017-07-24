package by.epam.movierating.command.impl.general;

import by.epam.movierating.bean.Movie;
import by.epam.movierating.bean.dto.ReviewDTO;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.service.MovieService;
import by.epam.movierating.service.ReviewService;
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
 * Redirect on welcome page.
 */
public class WelcomePageCommand implements Command {
    private final static Logger logger = Logger.getLogger(WelcomePageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        ServiceFactory serviceFactory = new ServiceFactory();
        ReviewService reviewService = serviceFactory.getReviewService();
        MovieService movieService=serviceFactory.getMovieService();

        try {
            List<ReviewDTO> reviewList = reviewService.getLimitedReviews(language);
            request.setAttribute(AttributeName.REVIEWS, reviewList);
            List<Movie> movieList=movieService.getNewestLimitedMovies(language);
            request.setAttribute(AttributeName.MOVIES, movieList);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
        if (session.getAttribute(AttributeName.ROLE).equals(AttributeName.ADMIN)) {
            request.getRequestDispatcher(JSPPageName.ADMIN_PAGE).forward(request, response);
        } else {
            request.getRequestDispatcher(JSPPageName.WELCOME_PAGE).forward(request, response);
        }
    }
}
