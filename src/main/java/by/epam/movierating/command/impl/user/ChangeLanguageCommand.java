package by.epam.movierating.command.impl.user;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.util.PagePathUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Implementation of Command {@link Command}.
 * Services the changing the language.
 */
public class ChangeLanguageCommand implements Command {
    private static final Logger logger = Logger.getLogger(ChangeLanguageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        if (language.equals(AttributeName.RUSSIAN)) {
            session.setAttribute(AttributeName.LANGUAGE, AttributeName.ENGLISH);
        } else {
            session.setAttribute(AttributeName.LANGUAGE, AttributeName.RUSSIAN);
        }
        String pagePath = PagePathUtil.getPagePath(session);
        response.sendRedirect(pagePath);
    }
}
