package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import static com.epam.jwd.controller.command.Attributes.CURRENT_ACCOUNT;
import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    private static final Command INSTANCE = new LogoutCommand();
    private static final String PAGE_PATH = "/controller?command=show_main";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_LOG_OUT_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
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
        session.removeAttribute(CURRENT_USER);
        session.removeAttribute(CURRENT_ACCOUNT);
        context.invalidateCurrentSession();

        return SUCCESSFUL_LOG_OUT_CONTEXT;
    }
}
