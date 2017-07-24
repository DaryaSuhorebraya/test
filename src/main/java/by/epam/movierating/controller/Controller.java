package by.epam.movierating.controller;

import by.epam.movierating.command.Command;
import by.epam.movierating.command.constant.ParameterName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet for services all requests.
 */
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commandString = request.getParameter(ParameterName.COMMAND);
        Command command = CommandProvider.getInstance().getCommand(commandString);
        command.execute(request, response);
    }
}
