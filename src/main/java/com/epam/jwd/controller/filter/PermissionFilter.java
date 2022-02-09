package com.epam.jwd.controller.filter;

import com.epam.jwd.controller.command.ApplicationCommand;
import com.epam.jwd.dao.entity.Role;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Filter class which filter every servlet request for role identity
 */
@WebFilter(urlPatterns = "/controller")
public class PermissionFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(PermissionFilter.class);

    private static final String ERROR_PAGE = "/controller?command=show_error_page";
    private static final String SESSION_ERROR_PAGE = "/controller?command=show_login";
    private static final String ACCOUNT_ATTRIBUTE = "account";
    private static final String COMMAND_ATTRIBUTE = "command";

    private final Map<Role, Set<ApplicationCommand>> commandsByRole;

    public PermissionFilter() {
        this.commandsByRole = new EnumMap<>(Role.class);
    }

    /**
     * Main filter method which filter request or redirect to error page
     *
     * @param request     - servlet request
     * @param response    - servlet response
     * @param filterChain - filter chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final ApplicationCommand command = ApplicationCommand.getCommands(req.getParameter(COMMAND_ATTRIBUTE));
        final HttpSession session = req.getSession(false);
        Role currentRole = null;
        try {
            currentRole = extractRoleFromSession(session);
        } catch (ServiceException e) {
            logger.error(e.getMessage() + e);
        }
        final Set<ApplicationCommand> allowedCommands = commandsByRole.get(currentRole);
        if (allowedCommands.contains(command)) {
            filterChain.doFilter(request, response);
        } else {
            if (session != null && !session.isNew()) {
                ((HttpServletResponse) response).sendRedirect(ERROR_PAGE);
            } else {
                ((HttpServletResponse) response).sendRedirect(SESSION_ERROR_PAGE);
            }
        }
    }

    /**
     * Method for adding allowed command to set
     *
     * @param filterConfig {@link FilterConfig}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        for (ApplicationCommand command : ApplicationCommand.values()) {
            for (Role allowedRole : command.getAllowRoles()) {
                Set<ApplicationCommand> commands = commandsByRole.computeIfAbsent(allowedRole, k -> EnumSet.noneOf(ApplicationCommand.class));
                commands.add(command);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * Method for extracting Role from session
     *
     * @param session current session
     * @return role of current user
     */
    private Role extractRoleFromSession(HttpSession session) throws ServiceException {
        return session != null &&
                (session.getAttribute(ACCOUNT_ATTRIBUTE) != null)
                ? ((AccountDto) session.getAttribute(ACCOUNT_ATTRIBUTE)).getRole()
                : Role.UNAUTHORIZED;
    }
}
