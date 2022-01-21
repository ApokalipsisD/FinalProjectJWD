package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ReviewCommand.class);

    private static final Command INSTANCE = new ReviewCommand();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/controller?command=catalog&course=";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String GRADE_ATTRIBUTE = "grade";
    private static final String ATTENDANCE_ATTRIBUTE = "attendance";
    private static final String REVIEW_TEXT_ATTRIBUTE = "reviewText";
    private static final String STUDENT_ID_ON_COURSE_ATTRIBUTE = "studentIdOnCourse";
    private static final String COURSE_ID = "id";
    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";


    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_CREATE_REVIEW_CONTEXT = new ResponseContext() {
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

//        HttpSession session = context.getCurrentSession().get();
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        Integer grade = Integer.valueOf(context.getParameterByName(GRADE_ATTRIBUTE));
        Integer attendance = Integer.valueOf(context.getParameterByName(ATTENDANCE_ATTRIBUTE));
        String reviewText = context.getParameterByName(REVIEW_TEXT_ATTRIBUTE);
        Integer studentId = Integer.valueOf(context.getParameterByName(STUDENT_ID_ON_COURSE_ATTRIBUTE));
        Integer courseId = Integer.valueOf(context.getParameterByName(COURSE_ID));

        try {
            review.create(new ReviewDto(courseId, studentId, grade, attendance, reviewText));
            context.addAttributeToJsp("message", "Review added");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
//        pagePath = context.getContextPath() + context.getHeader();
        pagePath = PAGE_PATH + context.getParameterByName(COURSE_ID);

        return SUCCESSFUL_CREATE_REVIEW_CONTEXT;
    }
}
