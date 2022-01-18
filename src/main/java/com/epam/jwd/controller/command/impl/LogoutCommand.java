package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;

import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final Command INSTANCE = new LogoutCommand();

    private static final String PAGE_PATH = "/WEB-INF/jsp/main.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String CURRENT_USER = "userName";
    private static final String CURRENT_ACCOUNT = "account";

    private static final ResponseContext SUCCESSFUL_LOG_OUT_CONTEXT = new ResponseContext() {
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

    public static Command getInstance() {
        return INSTANCE;
    }

    @Override
    public ResponseContext execute(RequestContext context) {

        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        session.removeAttribute(CURRENT_USER);
        session.removeAttribute(CURRENT_ACCOUNT);
        context.invalidateCurrentSession();

        return SUCCESSFUL_LOG_OUT_CONTEXT;
    }
}
