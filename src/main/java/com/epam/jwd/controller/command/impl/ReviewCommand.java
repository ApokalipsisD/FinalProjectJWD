package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.ReviewServiceImpl;

import javax.servlet.http.HttpSession;

public class ReviewCommand implements Command {
    private static final Command INSTANCE = new ReviewCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();


    private static final String PAGE_PATH = "/WEB-INF/jsp/catalog.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String GRADE_ATTRIBUTE = "grade";
    private static final String ATTENDANCE_ATTRIBUTE = "attendance";
    private static final String REVIEW_TEXT_ATTRIBUTE = "reviewText";
    private static final String STUDENT_ID_ON_COURSE_ATTRIBUTE = "studentIdOnCourse";
    private static final String COURSE_ID = "id";


    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_CREATE_REVIEW_CONTEXT = new ResponseContext() {
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
        Integer studentId = Integer.valueOf(context.getParameterByName(STUDENT_ID_ON_COURSE_ATTRIBUTE));
        Integer courseId = Integer.valueOf(context.getParameterByName(COURSE_ID));

        try {
            review.create(new ReviewDto(courseId, studentId, grade, attendance, reviewText));
        } catch (ServiceException e) {
            return ERROR_CONTEXT;
        }
        pagePath = context.getContextPath() + context.getHeader();

        return SUCCESSFUL_CREATE_REVIEW_CONTEXT;
    }
}
