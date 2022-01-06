package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.dao.entity.Status;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;

import javax.servlet.http.HttpSession;
import java.sql.Date;

public class CreateCourseCommand implements Command {
    private static final Command INSTANCE = new CreateCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/catalog.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String TITLE_ATTRIBUTE = "title";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String START_DATE_ATTRIBUTE = "startDate";
    private static final String END_DATE_ATTRIBUTE = "endDate";
    private static final String TEACHER_ATTRIBUTE = "teacher";
    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_CATALOG_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return pagePath;
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
            session.removeAttribute("error");
        } else {
            return ERROR_CONTEXT;
        }

        String title = context.getParameterByName(TITLE_ATTRIBUTE);
        String description = context.getParameterByName(DESCRIPTION_ATTRIBUTE);
        Date startDate = Date.valueOf(context.getParameterByName(START_DATE_ATTRIBUTE));
        Date endDate = Date.valueOf(context.getParameterByName(END_DATE_ATTRIBUTE));
        Integer teacherId = Integer.valueOf(context.getParameterByName(TEACHER_ATTRIBUTE));

        try {
            catalog.create(new CourseDto(title, description, startDate, endDate, Status.Coming.getId(), teacherId));
        } catch (ServiceException e) {
            return ERROR_CONTEXT;
        }
        pagePath = context.getContextPath() + context.getHeader();
//        System.out.println(pagePath);
        return SUCCESSFUL_CATALOG_CONTEXT;
    }
}
