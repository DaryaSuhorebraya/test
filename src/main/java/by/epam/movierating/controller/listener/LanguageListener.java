package by.epam.movierating.controller.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Listener on a session creation.
 * Setups the default language.
 */
public class LanguageListener implements HttpSessionListener {
    private static final String LANGUAGE = "language";
    private static final String ENGLISH = "en_EN";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute(LANGUAGE, ENGLISH);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
