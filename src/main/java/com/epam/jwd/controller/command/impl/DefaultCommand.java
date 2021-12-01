package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;

import javax.servlet.http.HttpSession;

public class DefaultCommand implements Command {
    private static final Command INSTANCE = new DefaultCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/main.jsp";
    private static final ResponseContext SHOW_DEFAULT_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
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
        HttpSession session = context.getCurrentSession().orElse(context.createSession());
        return SHOW_DEFAULT_PAGE_CONTEXT;
    }
}
