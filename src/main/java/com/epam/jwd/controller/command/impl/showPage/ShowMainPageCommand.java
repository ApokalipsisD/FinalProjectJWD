package com.epam.jwd.controller.command.impl.showPage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.DefaultCommand;

public class ShowMainPageCommand implements Command {
    private static final Command INSTANCE = new DefaultCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/main.jsp";
    private static final ResponseContext SHOW_MAIN_PAGE_CONTEXT = new ResponseContext() {
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
        return SHOW_MAIN_PAGE_CONTEXT;
    }
}
