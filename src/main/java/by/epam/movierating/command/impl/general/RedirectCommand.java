package by.epam.movierating.command.impl.general;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of Command {@link Command}.
 * Redirect pages.
 */
public class RedirectCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagePathUtil.setQueryString(request);
        String redirectPage = request.getParameter(ParameterName.REDIRECT);
        String redirectPagePath = defineRedirectPagePath(redirectPage);
        request.getRequestDispatcher(redirectPagePath).forward(request, response);

    }


    private String defineRedirectPagePath(String redirectPage) {
        switch (redirectPage) {
            case ParameterName.REGISTRATION: {
                return JSPPageName.REGISTRATION_PAGE;
            }
            case ParameterName.ADD_MOVIE: {
                return JSPPageName.ADD_MOVIE_PAGE;
            }
            case ParameterName.SUCCESS_ADD_MOVIE: {
                return JSPPageName.SUCCESS_MOVIE_ADD;
            }
            default: {
                return "";
            }
        }
    }
}
