package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.epam.jwd.controller.command.Attributes.ATTENDANCE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.GRADE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;
import static com.epam.jwd.controller.command.Attributes.REVIEW_CHANGED_MESSAGE;
import static com.epam.jwd.controller.command.Attributes.REVIEW_ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.REVIEW_TEXT_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.STUDENT_ID_ON_COURSE_ATTRIBUTE;

public class ChangeReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeReviewCommand.class);

    private static final Command INSTANCE = new ChangeReviewCommand();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/controller?command=catalog&course=";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_CHANGE_REVIEW_CONTEXT = new ResponseContext() {
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
        Integer grade = Integer.valueOf(context.getParameterByName(GRADE_ATTRIBUTE));
        Integer attendance = Integer.valueOf(context.getParameterByName(ATTENDANCE_ATTRIBUTE));
        String reviewText = context.getParameterByName(REVIEW_TEXT_ATTRIBUTE);
        Integer reviewId = Integer.valueOf(context.getParameterByName(REVIEW_ID_ATTRIBUTE));
        Integer studentId = Integer.valueOf(context.getParameterByName(STUDENT_ID_ON_COURSE_ATTRIBUTE));
        Integer courseId = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));

        try {
            review.update(new ReviewDto(reviewId, courseId, studentId, grade, attendance, reviewText));
            context.addAttributeToJsp(MESSAGE, REVIEW_CHANGED_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
        pagePath = PAGE_PATH + context.getParameterByName(ID_ATTRIBUTE);

        return SUCCESSFUL_CHANGE_REVIEW_CONTEXT;
    }
}
