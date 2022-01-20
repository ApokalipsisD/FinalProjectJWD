package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.dao.entity.Status;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.time.LocalDate;

public class ChangeCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeCourseCommand.class);

    private static final Command INSTANCE = new ChangeCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/WEB-INF/jsp/catalog.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String TITLE_ATTRIBUTE = "title";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String START_DATE_ATTRIBUTE = "startDate";
    private static final String END_DATE_ATTRIBUTE = "endDate";
    private static final String TEACHER_ATTRIBUTE = "teacher";
    private static final String ID_ATTRIBUTE = "id";
    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";

    private static final ResponseContext SUCCESSFUL_CHANGE_COURSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return pagePath;
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
//        HttpSession session;
//        if (context.getCurrentSession().isPresent()) {
//            session = context.getCurrentSession().get();
////            session.removeAttribute("error");
//        } else {
//            return ERROR_CONTEXT;
//        }
//        pagePath = context.getContextPath() + context.getHeader();

        String title = context.getParameterByName(TITLE_ATTRIBUTE);
        String description = context.getParameterByName(DESCRIPTION_ATTRIBUTE);
        Date startDate = Date.valueOf(context.getParameterByName(START_DATE_ATTRIBUTE));
        Date endDate = Date.valueOf(context.getParameterByName(END_DATE_ATTRIBUTE));
        Integer teacherId = Integer.valueOf(context.getParameterByName(TEACHER_ATTRIBUTE));
        Integer id = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));
        Integer status = getStatus(startDate, endDate);

        pagePath = "/controller?command=catalog&course=" + context.getParameterByName(ID_ATTRIBUTE);

        CourseDto courseDto = new CourseDto(id, title, description, startDate, endDate, status, teacherId);
        try {
            catalog.update(courseDto);
            context.addAttributeToJsp("message", "Course successfully updated");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        return SUCCESSFUL_CHANGE_COURSE_CONTEXT;
    }

    private Integer getStatus(Date startDate, Date endDate) {
        return startDate.toLocalDate().isAfter(LocalDate.now())
                ? Status.Coming.getId()
                : (endDate.toLocalDate().isAfter(LocalDate.now()) ? Status.Started.getId() : Status.Finished.getId());
    }
}
