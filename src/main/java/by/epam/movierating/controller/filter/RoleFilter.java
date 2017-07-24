package by.epam.movierating.controller.filter;

import by.epam.movierating.command.constant.AttributeName;
import by.epam.movierating.command.constant.JSPPageName;
import by.epam.movierating.command.constant.ParameterName;
import by.epam.movierating.controller.util.ParseRoleUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This filter initialize roles for the command.
 * Checks rights for processing requests by user that have his role.
 */
public class RoleFilter implements Filter {
    private List<String> userRoles;
    private List<String> guestRoles;
    private static final String DEFAULT_PAGE = "welcome-page";
    private static final String USER = "user";
    private static final String GUEST = "guest";
    private static final String ADMIN = "admin";
    private static final String REDIRECT = "redirect";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Map<String, List<String>> roleList = new ParseRoleUtil().parse();
        userRoles = roleList.get(USER);
        guestRoles = roleList.get(GUEST);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String command = request.getParameter(ParameterName.COMMAND);
        command = (command == null) ? DEFAULT_PAGE : command;
        HttpSession session = request.getSession(false);
        String role = (session == null) ? GUEST : (String) session.getAttribute(AttributeName.ROLE);
        if (command.equals(REDIRECT)) {
            if (checkRedirectCommand(request, role)){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(JSPPageName.ERROR_404_PAGE);
            }
        } else if (checkRole(role, command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(JSPPageName.ERROR_404_PAGE);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * Checks roles on their rights to process the command
     * @param role role for check
     * @param command command for check
     * @return <code>true</code> if the role is suitable to process the request;
     *         <code>false</code> otherwise.
     */
    private boolean checkRole(String role, String command) {
        int result = 0;
        switch (role) {
            case USER:
                for (String commandName : userRoles) {
                    if (command.equals(commandName)) {
                        result++;
                    }
                }
                break;
            case GUEST:
                for (String commandName : guestRoles) {
                    if (command.equals(commandName)) {
                        result++;
                    }
                }
                break;
            case ADMIN:
                return true;
        }
        return result > 0;
    }

    /**
     * Checks the redirect command on execution
     * @param request {@link HttpServletRequest} object
     * @param role role for check
     * @return <code>true</code> if the role is suitable to process the request;
     *         <code>false</code> otherwise.
     */
    private boolean checkRedirectCommand(HttpServletRequest request, String role) {
        String redirectPage = request.getParameter(ParameterName.REDIRECT);
        switch (redirectPage) {
            case ParameterName.REGISTRATION: {
                return true;
            }
            case ParameterName.ADD_MOVIE: {
                return role.equals(ADMIN);
            }
            case ParameterName.SUCCESS_ADD_MOVIE: {
                return role.equals(ADMIN);
            }
            default: {
                return false;
            }
        }
    }
}
