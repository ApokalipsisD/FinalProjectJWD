package com.epam.jwd.controller.command.impl.showPage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.DefaultCommand;

public class ShowUsersCommand implements Command {
    private static final Command INSTANCE = new DefaultCommand();
    private static final String PAGE_PATH = "/WEB-INF/jsp/users.jsp";
    private static final ResponseContext SHOW_USERS_CONTEXT = new ResponseContext() {
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
        return SHOW_USERS_CONTEXT;
    }
}
