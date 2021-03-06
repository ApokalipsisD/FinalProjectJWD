package com.epam.jwd.controller.command.impl.showPage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.impl.AccountServiceImpl;

public class ShowEditProfileCommand implements Command {
    private static final Command INSTANCE = new ShowEditProfileCommand();
    private static final AccountServiceImpl accountService = new AccountServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/editProfile.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";


    private static final ResponseContext SUCCESSFUL_PROFILE_CONTEXT = new ResponseContext() {
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
        return SUCCESSFUL_PROFILE_CONTEXT;
    }
}
