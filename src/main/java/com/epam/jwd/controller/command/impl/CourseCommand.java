package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;

public class CourseCommand implements Command {

    private static final Command INSTANCE = new CourseCommand();
//    private static final UserServiceImpl user = new UserServiceImpl();
//
//    private static final String PAGE_PATH = "/WEB-INF/jsp/main.jsp";
//    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/login.jsp";
//    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
//
//    private static final String LOGIN_ATTRIBUTE = "login";
//    private static final String PASSWORD_ATTRIBUTE = "password";
//    private static final String ERROR_ATTRIBUTE = "error";
//    private static final String CURRENT_USER = "user";
//
//    private static final String CURRENT_USER_NAME = "userName";
//
//    private static final ResponseContext SUCCESSFUL_LOG_IN_CONTEXT = new ResponseContext() {
//        @Override
//        public String getPage() {
//            return PAGE_PATH;
//        }
//
//        @Override
//        public boolean isRedirect() {
//            return false;
//        }
//    };
//
//    private static final ResponseContext LOG_IN_FAILED_CONTEXT = new ResponseContext() {
//        @Override
//        public String getPage() {
//            return FAIL_PAGE_PATH;
//        }
//
//        @Override
//        public boolean isRedirect() {
//            return false;
//        }
//    };
//
//    private final ResponseContext ERROR_CONTEXT = new ResponseContext() {
//        @Override
//        public String getPage() {
//            return ERROR_PAGE_PATH;
//        }
//
//        @Override
//        public boolean isRedirect() {
//            return false;
//        }
//    };
//
    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

//        String login = context.getParameterByName(LOGIN_ATTRIBUTE);
//        String password = context.getParameterByName(PASSWORD_ATTRIBUTE);
//        HttpSession session;
//        if (context.getCurrentSession().isPresent()) {
//            session = context.getCurrentSession().get();
//            session.removeAttribute("error");
//        } else {
//            return ERROR_CONTEXT;
//        }
//        UserDto userDto;
//        try {
//            userDto = user.getByLogin(login);
//            if (userDto.getPassword().equals(password)) {
//                session.setAttribute(CURRENT_USER, userDto);
//                session.setAttribute(CURRENT_USER_NAME, userDto.getLogin());
//                context.addAttributeToJsp("message", "Log in is successfully completed");  // Log in is successfully completed
//            } else {
//                return ERROR_CONTEXT;
//            }
//        } catch (ServiceException e) {
//            return ERROR_CONTEXT;
//        }
//
//
//
////        session.setAttribute(CURRENT_USER, user);
////        context.addAttributeToJsp("message", "Registration is successfully completed");
//
//
//        return SUCCESSFUL_LOG_IN_CONTEXT;
        return null;
    }
}

