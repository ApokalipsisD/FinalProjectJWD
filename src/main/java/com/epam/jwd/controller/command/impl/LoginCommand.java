package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private final Service<UserDto, Integer> userService = new UserServiceImpl();
    private static final Command INSTANCE = new LoginCommand();

    private static final String PAGE_PATH = "/WEB-INF/jsp/main.jsp";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/login.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String LOGIN_ATTRIBUTE = "login";
    private static final String PASSWORD_ATTRIBUTE = "password";
    private static final String ERROR_ATTRIBUTE = "error";

    private static final String CURRENT_USER = "currentUser";

    private static final ResponseContext SUCCESSFUL_LOG_IN_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext LOG_IN_FAILED_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private final ResponseContext ERROR_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return ERROR_PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    public static Command getInstance(){
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        String login = context.getParameterByName(LOGIN_ATTRIBUTE);
        String password = context.getParameterByName(PASSWORD_ATTRIBUTE);


        UserDto userDto = new UserDto(login, password);
        UserDto user = userService.create(userDto);

        HttpSession session;

        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        session.setAttribute(CURRENT_USER, user);
        context.addAttributeToJsp("message", "Registration is successfully completed");



        return SUCCESSFUL_LOG_IN_CONTEXT;
    }
}
