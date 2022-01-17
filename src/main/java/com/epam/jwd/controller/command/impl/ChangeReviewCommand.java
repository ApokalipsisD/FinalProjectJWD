package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ReviewServiceImpl;

import javax.servlet.http.HttpSession;

public class ChangeReviewCommand implements Command {
    private static final Command INSTANCE = new ChangeReviewCommand();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();


    private static final String PAGE_PATH = "/WEB-INF/jsp/catalog.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String GRADE_ATTRIBUTE = "gradeChange";
    private static final String ATTENDANCE_ATTRIBUTE = "attendanceChange";
    private static final String REVIEW_TEXT_ATTRIBUTE = "reviewTextChange";

    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_CHANGE_REVIEW_CONTEXT = new ResponseContext() {
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

        HttpSession session = context.getCurrentSession().get();

        Integer grade = Integer.valueOf(context.getParameterByName(GRADE_ATTRIBUTE));
        Integer attendance = Integer.valueOf(context.getParameterByName(ATTENDANCE_ATTRIBUTE));
        String reviewText = context.getParameterByName(REVIEW_TEXT_ATTRIBUTE);
        Integer reviewId = Integer.valueOf(context.getParameterByName("reviewId"));

        Integer studentId = Integer.valueOf(context.getParameterByName("studentIdOnCourse"));

        Integer courseId = Integer.valueOf(context.getParameterByName("id"));

        try {
            review.update(new ReviewDto(reviewId, courseId, studentId, grade, attendance, reviewText));
        } catch (ServiceException e) {
            return ERROR_CONTEXT;
        }
        pagePath = context.getContextPath() + context.getHeader();

        return SUCCESSFUL_CHANGE_REVIEW_CONTEXT;
    }
}
