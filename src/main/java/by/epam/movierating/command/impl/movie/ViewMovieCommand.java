package by.epam.movierating.command.impl.movie;

import by.epam.movierating.bean.*;
import by.epam.movierating.bean.dto.ReviewDTO;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.service.*;
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
 * Services the receiving movie.
 */
public class ViewMovieCommand implements Command {
    private final static Logger logger = Logger.getLogger(ViewMovieCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        HttpSession session = request.getSession(true);
        int idMovie = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();
        GenreService genreService = serviceFactory.getGenreService();
        CountryService countryService = serviceFactory.getCountryService();
        ActorService actorService = serviceFactory.getActorService();
        ReviewService reviewService = serviceFactory.getReviewService();
        RatingService ratingService = serviceFactory.getRatingService();
        try {
            Movie movie = movieService.getMovieById(idMovie, language);
            request.setAttribute(AttributeName.MOVIE, movie);
            List<Genre> genreList = genreService.getGenresByIdMovie(idMovie, language);
            request.setAttribute(AttributeName.GENRES, genreList);
            List<Country> countryList = countryService.getCountriesByMovieId(idMovie, language);
            request.setAttribute(AttributeName.COUNTRIES, countryList);
            List<Actor> actorList = actorService.getActorsByMovieId(idMovie, language);
            request.setAttribute(AttributeName.ACTORS, actorList);
            List<ReviewDTO> reviewList = reviewService.getReviewsByIdMovie(idMovie, language);
            request.setAttribute(AttributeName.REVIEWS, reviewList);
            if (session.getAttribute(AttributeName.USER_ID) != null) {
                int idUser = (int) session.getAttribute(AttributeName.USER_ID);
                Rating rating = ratingService.getRatingOnMovieByUserId(idMovie, idUser);
                if (rating != null) {
                    request.setAttribute(AttributeName.RATING, rating);
                }
            }

            request.getRequestDispatcher(JSPPageName.MOVIE_INFO_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
