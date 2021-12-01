package com.epam.jwd.controller.servlet;

import com.epam.jwd.controller.command.RequestContextImpl;
import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.ResponseContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    public static final String COMMAND_PARAMETER_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

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
}

