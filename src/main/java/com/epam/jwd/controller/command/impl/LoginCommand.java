package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    private static final Command INSTANCE = new LoginCommand();
    private static final UserServiceImpl user = new UserServiceImpl();
    private static final AccountServiceImpl accountService = new AccountServiceImpl();

    private static final String PAGE_PATH = "/controller?command=show_main";
    private static final String FAIL_PAGE_PATH = "/controller?command=show_login";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String LOGIN_ATTRIBUTE = "login";
    private static final String PASSWORD_ATTRIBUTE = "password";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String CURRENT_USER = "user";
    private static final String CURRENT_ACCOUNT = "account";
    private static final String CURRENT_USER_NAME = "userName";
    private static final String DELIMITER = ":";


    private static final ResponseContext SUCCESSFUL_LOG_IN_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
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

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {
        HttpSession session = context.getCurrentSession().get();
        String login = context.getParameterByName(LOGIN_ATTRIBUTE);
        String password = context.getParameterByName(PASSWORD_ATTRIBUTE);

//        if (context.getCurrentSession().isPresent()) {
//            session = context.getCurrentSession().get();
////            session.removeAttribute("error");
//        } else {
//            return ERROR_CONTEXT;
//        }
        UserDto userDto;
        try {
            userDto = user.getByLogin(login);
            if (userDto.getPassword().equals(password)) {
                session.setAttribute(CURRENT_USER, userDto);
                session.setAttribute(CURRENT_USER_NAME, userDto.getLogin());
                session.setAttribute(CURRENT_ACCOUNT, accountService.getAccountByUserId(userDto.getId()));
//                context.addAttributeToJsp("message", "Log in is successfully completed");  // Log in is successfully completed
            } else {
               throw new ServiceException(MessageException.USER_NOT_FOUND_EXCEPTION);
            }
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
            return LOG_IN_FAILED_CONTEXT;
        }


//        session.setAttribute(CURRENT_USER, user);
//        context.addAttributeToJsp("message", "Registration is successfully completed");


        return SUCCESSFUL_LOG_IN_CONTEXT;
    }
}

