package by.epam.movierating.controller.listener;

import by.epam.movierating.bean.Country;
import by.epam.movierating.bean.Genre;
import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.service.CountryService;
import by.epam.movierating.service.GenreService;
import by.epam.movierating.service.exception.ServiceException;
import by.epam.movierating.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Listener on a servlet context creation.
 * Setups static constant content.
 */
public class StaticContentListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(StaticContentListener.class);
    private static final String LANGUAGE_EN = "en_EN";
    private static final String LANGUAGE_RU = "ru_RU";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServiceFactory serviceFactory = new ServiceFactory();
        GenreService genreService = serviceFactory.getGenreService();
        CountryService countryService = serviceFactory.getCountryService();

        ServletContext servletContext = servletContextEvent.getServletContext();

        List<Genre> genreListEn = null;
        List<Country> countryListEn = null;
        List<Genre> genreListRu = null;
        List<Country> countryListRu = null;
        try {
            genreListEn = genreService.getAllActiveGenre(LANGUAGE_EN);
            countryListEn = countryService.getAllActiveCountries(LANGUAGE_EN);
            genreListRu = genreService.getAllActiveGenre(LANGUAGE_RU);
            countryListRu = countryService.getAllActiveCountries(LANGUAGE_RU);

        } catch (ServiceException e) {
            logger.error(e);
        }

        servletContext.setAttribute(AttributeName.COUNTRIES_EN, countryListEn);
        servletContext.setAttribute(AttributeName.GENRES_EN, genreListEn);
        servletContext.setAttribute(AttributeName.COUNTRIES_RU, countryListRu);
        servletContext.setAttribute(AttributeName.GENRES_RU, genreListRu);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
