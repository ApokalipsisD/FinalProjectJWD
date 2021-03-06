package com.epam.jwd.controller.servlet;

import com.epam.jwd.controller.command.RequestContextImpl;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.connectionPoolService.ConnectionPoolService;
import com.epam.jwd.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class which provides main http method logic
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private final ConnectionPoolService pool = ConnectionPoolService.getInstance();
    public static final String COMMAND_PARAMETER_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Method which describes main logic of taken command from request and gives it to precise command class
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = Command.of(request.getParameter(COMMAND_PARAMETER_NAME));
        ResponseContext commandResult = command.execute(new RequestContextImpl(request));
        if (commandResult.isRedirect()) {
            response.sendRedirect(commandResult.getPage());
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(commandResult.getPage());
            requestDispatcher.forward(request, response);
        }
    }

    /**
     * Method for initialization of connection pool within start of the application
     */
    @Override
    public void init() {
        try {
            pool.initialize();
        } catch (ServiceException e) {
            logger.error(e.getMessage() + e);
        }
    }

    /**
     * Method that shutDowns connection pool in the end of application work
     */
    @Override
    public void destroy() {
        super.destroy();
        try {
            pool.shutDown();
        } catch (ServiceException e) {
            logger.error(e.getMessage() + e);
        }
    }
}

