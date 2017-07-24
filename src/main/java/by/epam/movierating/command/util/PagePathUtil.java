package by.epam.movierating.command.util;

import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Saves query strings to session and gives it back
 */
public class PagePathUtil {
    private static final String WELCOME_PAGE_COMMAND = "/Controller?command=welcome-page";

    /**
     * Return saved path or null if it does not exist.
     * @param session current session
     * @return page's path
     */
    public static String getPagePath(HttpSession session) {
        String pagePath = (String) session.getAttribute(AttributeName.PREV_QUERY);
        if (pagePath == null) {
            return WELCOME_PAGE_COMMAND;
        } else {
            return pagePath;
        }
    }

    /**
     * Saves query to session
     * @param request current request
     */
    public static void setQueryString(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString == null) {
            session.setAttribute(AttributeName.PREV_QUERY, requestURI);
        } else {
            session.setAttribute(AttributeName.PREV_QUERY, requestURI + '?' + queryString);
        }
    }
}
