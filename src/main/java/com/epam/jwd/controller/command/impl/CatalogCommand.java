package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.impl.CourseServiceImpl;

public class CatalogCommand implements Command {
    private static final Command INSTANCE = new CatalogCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/course.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_CATALOG_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
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

//        String login = context.getParameterByName(LOGIN_ATTRIBUTE);
//        String password = context.getParameterByName(PASSWORD_ATTRIBUTE);
//        HttpSession session;
//        if (context.getCurrentSession().isPresent()) {
//            session = context.getCurrentSession().get();
//            session.removeAttribute("error");
//        } else {
//            return ERROR_CONTEXT;
//        }
//
//        List<CourseDto> list = catalog.getAll();
//        System.out.println(list);


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
        return SUCCESSFUL_CATALOG_CONTEXT;
    }
}
