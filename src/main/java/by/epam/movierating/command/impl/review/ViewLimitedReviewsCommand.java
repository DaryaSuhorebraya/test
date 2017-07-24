package by.epam.movierating.command.impl.review;

import by.epam.movierating.bean.dto.ReviewDTO;
import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.util.PagePathUtil;
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
 * Services the receiving the limited list of reviews.
 */
public class ViewLimitedReviewsCommand implements Command {
    private static final Logger logger = Logger.getLogger(ViewLimitedReviewsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        try {
            ReviewService reviewService = serviceFactory.getReviewService();
            List<ReviewDTO> reviewList = reviewService.getAllFullInfoReviewsOrderByDate(language);
            request.setAttribute(AttributeName.REVIEWS, reviewList);
            request.getRequestDispatcher(JSPPageName.REVIEWS_PAGE).forward(request, response);
        } catch (ServiceException e) {
            logger.error(e);
            response.sendRedirect(JSPPageName.ERROR_500_PAGE);
        }
    }
}
