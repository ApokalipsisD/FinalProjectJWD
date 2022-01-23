package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;
import static com.epam.jwd.controller.command.Attributes.REVIEW_ADDED_MESSAGE;
import static com.epam.jwd.controller.command.Attributes.REVIEW_ATTENDANCE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.REVIEW_GRADE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.STUDENT_ID_ON_COURSE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.TEXT_REVIEW_ATTRIBUTE;

public class ReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ReviewCommand.class);

    private static final Command INSTANCE = new ReviewCommand();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/controller?command=catalog&course=";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
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
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        HttpSession session = context.getCurrentSession().get();

        Integer grade = Integer.valueOf(context.getParameterByName(REVIEW_GRADE_ATTRIBUTE));
        Integer attendance = Integer.valueOf(context.getParameterByName(REVIEW_ATTENDANCE_ATTRIBUTE));
        String reviewText = context.getParameterByName(TEXT_REVIEW_ATTRIBUTE);
        Integer studentId = Integer.valueOf(context.getParameterByName(STUDENT_ID_ON_COURSE_ATTRIBUTE));
        Integer courseId = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));

        try {
            review.create(new ReviewDto(courseId, studentId, grade, attendance, reviewText));
            context.addAttributeToJsp(MESSAGE, REVIEW_ADDED_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
        pagePath = PAGE_PATH + context.getParameterByName(ID_ATTRIBUTE);

        return SUCCESSFUL_CREATE_REVIEW_CONTEXT;
    }
}
