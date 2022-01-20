package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.dao.entity.Status;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class CreateCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateCourseCommand.class);

    private static final Command INSTANCE = new CreateCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();

    private static final String PAGE_PATH = "/controller?command=show_courses";
    private static final String FAIL_PAGE_PATH = "/controller?command=show_courses";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String TITLE_ATTRIBUTE = "title";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String START_DATE_ATTRIBUTE = "startDate";
    private static final String END_DATE_ATTRIBUTE = "endDate";
    private static final String TEACHER_ATTRIBUTE = "teacher";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String DELIMITER = ":";

    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_CREATE_COURSE_CONTEXT = new ResponseContext() {
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

    private final ResponseContext FAIL_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
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
//        HttpSession session = context.getCurrentSession().get();

//        if (context.getCurrentSession().isPresent()) {
//            session = context.getCurrentSession().get();
//            session.removeAttribute(ERROR_ATTRIBUTE);
//        } else {
//            return ERROR_CONTEXT;
//        }

        String title = context.getParameterByName(TITLE_ATTRIBUTE);
        String description = context.getParameterByName(DESCRIPTION_ATTRIBUTE);
        Date startDate = Date.valueOf(context.getParameterByName(START_DATE_ATTRIBUTE));
        Date endDate = Date.valueOf(context.getParameterByName(END_DATE_ATTRIBUTE));
        Integer teacherId = Objects.nonNull(context.getParameterByName(TEACHER_ATTRIBUTE))
                ? Integer.valueOf(context.getParameterByName(TEACHER_ATTRIBUTE)) : null;
        Integer status = getStatus(startDate, endDate);

        try {
            catalog.create(new CourseDto(title, description, startDate, endDate, status, teacherId));
            context.addAttributeToJsp(MESSAGE_ATTRIBUTE, "Course created successfully");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
            return FAIL_PAGE_CONTEXT;
        }
        pagePath = context.getContextPath() + context.getHeader();
        return SUCCESSFUL_CREATE_COURSE_CONTEXT;
    }

    private Integer getStatus(Date startDate, Date endDate) {
        return startDate.toLocalDate().isAfter(LocalDate.now())
                ? Status.Coming.getId()
                : (endDate.toLocalDate().isAfter(LocalDate.now()) ? Status.Started.getId() : Status.Finished.getId());
    }
}
