package by.epam.movierating.command.impl.upload;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.command.util.PagePathUtil;
import by.epam.movierating.controller.util.UploadUtil;
import by.epam.movierating.service.ActorService;
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
 * Services the uploading new file belonging to actor.
 */
public class UploadActorImageCommand implements Command {
    private static final Logger logger = Logger.getLogger(UploadActorImageCommand.class);
    private static final String ACTOR = "actor";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletFileUpload upload = UploadUtil.createServletFileUpload(request);
        HttpSession session = request.getSession(true);
        int idActor = Integer.parseInt(request.getParameter(ParameterName.ACTOR_ID));
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ActorService actorService = serviceFactory.getActorService();

        ServletContext servletContext = request.getServletContext();

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem item : fileItems) {
                if (!item.isFormField()) {
                    String fileName = UploadUtil.processUploadedFile(item, servletContext, ACTOR);
                    boolean result = actorService.uploadActorImage(idActor, fileName);
                    if (result) {
                        String pagePath = PagePathUtil.getPagePath(session);
                        response.sendRedirect(pagePath);
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
