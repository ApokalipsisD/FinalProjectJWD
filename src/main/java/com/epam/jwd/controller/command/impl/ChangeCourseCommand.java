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
import java.util.Objects;

public class ChangeCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeCourseCommand.class);

    private static final Command INSTANCE = new ChangeCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/controller?command=catalog&course=";
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
        if(context.getHeader() == null){
            return ERROR_CONTEXT;
        }
        String title = context.getParameterByName(TITLE_ATTRIBUTE);
        String description = context.getParameterByName(DESCRIPTION_ATTRIBUTE);
        String startDate = context.getParameterByName(START_DATE_ATTRIBUTE);
        String endDate = context.getParameterByName(END_DATE_ATTRIBUTE);
        String teacherId = context.getParameterByName(TEACHER_ATTRIBUTE);
        String id = context.getParameterByName(ID_ATTRIBUTE);

//        Date startDate = Objects.nonNull(context.getParameterByName(START_DATE_ATTRIBUTE)) ?
//                Date.valueOf(context.getParameterByName(START_DATE_ATTRIBUTE)) : null;
//
//        Date endDate = Objects.nonNull(context.getParameterByName(END_DATE_ATTRIBUTE)) ?
//                Date.valueOf(context.getParameterByName(END_DATE_ATTRIBUTE)) : null;
//
//        Integer teacherId = Objects.nonNull(context.getParameterByName(TEACHER_ATTRIBUTE)) ?
//                Integer.valueOf(context.getParameterByName(TEACHER_ATTRIBUTE)) : null;
//
//        Integer id = Objects.nonNull(context.getParameterByName(ID_ATTRIBUTE)) ?
//                Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE)) : null;
//        Integer status = getStatus(startDate, endDate);

//        CourseDto courseDto = new CourseDto(id, title, description, startDate, endDate, status, teacherId);
        try {
            if (Objects.isNull(startDate) || Objects.isNull(endDate) || Objects.isNull(title)
                    || Objects.isNull(description) || Objects.isNull(teacherId) || Objects.isNull(id)) {
                throw new ServiceException("Enter data");
            }
            Integer status = getStatus(Date.valueOf(startDate), Date.valueOf(endDate));
            CourseDto courseDto = new CourseDto(Integer.valueOf(id), title, description,
                    Date.valueOf(startDate), Date.valueOf(endDate), status, Integer.valueOf(teacherId));
            catalog.update(courseDto);
            context.addAttributeToJsp("message", "Course successfully updated");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
        pagePath = PAGE_PATH + id;
//        pagePath = context.getContextPath() + context.getHeader();
//        pagePath = PAGE_PATH + context.getParameterByName(ID_ATTRIBUTE);
        return SUCCESSFUL_CHANGE_COURSE_CONTEXT;
    }

    private Integer getStatus(Date startDate, Date endDate) {
        return startDate.toLocalDate().isAfter(LocalDate.now())
                ? Status.Coming.getId()
                : (endDate.toLocalDate().isAfter(LocalDate.now()) ? Status.Started.getId() : Status.Finished.getId());
    }
}