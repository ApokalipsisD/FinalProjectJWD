package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ReviewServiceImpl;

import javax.servlet.http.HttpSession;

public class DeleteReviewCommand implements Command {
    private static final Command INSTANCE = new DeleteReviewCommand();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/catalog.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static String pagePath = null;

    private static final ResponseContext SUCCESSFUL_DELETE_REVIEW_CONTEXT = new ResponseContext() {
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

        Integer reviewId = Integer.valueOf(context.getParameterByName("reviewId"));

        ReviewDto reviewDto;
        try {
            reviewDto = review.getById(reviewId);
            review.delete(reviewDto);
        } catch (ServiceException e) {
            return ERROR_CONTEXT;
        }
        pagePath = context.getContextPath() + context.getHeader();

        return SUCCESSFUL_DELETE_REVIEW_CONTEXT;
    }
}
