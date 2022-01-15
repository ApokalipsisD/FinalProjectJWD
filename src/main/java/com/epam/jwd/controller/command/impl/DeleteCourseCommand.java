package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;

import javax.servlet.http.HttpSession;

public class DeleteCourseCommand implements Command {
    private static final Command INSTANCE = new DeleteCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();

    private static final String PAGE_PATH = "/controller?command=show_courses";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_DELETE_COURSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return true;
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
        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
//            session.removeAttribute("error");
        } else {
            return ERROR_CONTEXT;
        }

        Integer id = Integer.valueOf(context.getParameterByName("id"));
        CourseDto courseDto;
        try {
            courseDto = catalog.getById(id);
            catalog.delete(courseDto);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return SUCCESSFUL_DELETE_COURSE_CONTEXT;
    }
}
