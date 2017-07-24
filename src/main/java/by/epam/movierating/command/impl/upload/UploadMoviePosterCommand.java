package by.epam.movierating.command.impl.upload;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.controller.util.UploadUtil;
import by.epam.movierating.service.MovieService;
import by.epam.movierating.service.factory.ServiceFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Implementation of Command {@link Command}.
 * Services the uploading new file belonging to movie.
 */
public class UploadMoviePosterCommand implements Command {
    private static final Logger logger = Logger.getLogger(UploadMoviePosterCommand.class);
    private static final String MOVIE = "movie";
    private static final String QUERY_STRING = "/Controller?command=redirect&redirectPage=addMovie";
    private static final String SUCCESS_ADD="Controller?command=redirect&redirectPage=successAdd";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletFileUpload upload = UploadUtil.createServletFileUpload(request);
        HttpSession session = request.getSession(true);
        int idMovie = Integer.parseInt(request.getParameter(ParameterName.MOVIE_ID));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        MovieService movieService = serviceFactory.getMovieService();

        ServletContext servletContext = request.getServletContext();

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem item : fileItems) {
                if (!item.isFormField()) {
                    String fileName = UploadUtil.processUploadedFile(item, servletContext, MOVIE);
                    boolean result = movieService.uploadMoviePoster(idMovie, fileName);
                    if (result) {
                        String pagePath = PagePathUtil.getPagePath(session);
                        if (pagePath.equals(QUERY_STRING)) {
                            response.sendRedirect(SUCCESS_ADD);
                        } else {
                            response.sendRedirect(pagePath);
                        }
                    } else {
                        response.sendRedirect(JSPPageName.ERROR_500_PAGE);
                    }
                }
            }
        } catch (FileUploadException e) {
            logger.error("Error during uploading file", e);
            response.getWriter().print(false);
        } catch (Exception e) {
            logger.error(e);
            response.getWriter().print(false);
        }
    }
}
