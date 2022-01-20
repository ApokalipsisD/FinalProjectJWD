package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DeleteReviewCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteReviewCommand.class);

    private static final Command INSTANCE = new DeleteReviewCommand();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/catalog.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String REVIEW_ID_ATTRIBUTE = "reviewId";
    private static final String ID_ATTRIBUTE = "id";


    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_DELETE_REVIEW_CONTEXT = new ResponseContext() {
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
        Integer reviewId = Integer.valueOf(context.getParameterByName(REVIEW_ID_ATTRIBUTE));
        ReviewDto reviewDto;
        try {
            reviewDto = review.getById(reviewId);
            review.delete(reviewDto);
            context.addAttributeToJsp("message", "Review removed");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
//        pagePath = context.getContextPath() + context.getHeader();
        pagePath = "/controller?command=catalog&course=" + context.getParameterByName(ID_ATTRIBUTE);

        return SUCCESSFUL_DELETE_REVIEW_CONTEXT;
    }
}
