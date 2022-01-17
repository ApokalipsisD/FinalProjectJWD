package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;

public class DeleteAccountCommand implements Command {
    private static final Command INSTANCE = new DeleteAccountCommand();
    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final AccountServiceImpl accountService = new AccountServiceImpl();

    private static final String PAGE_PATH = "/controller?command=show_login";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String CURRENT_USER = "user";
    private static final String CURRENT_ACCOUNT = "account";
    private static final String USERNAME = "userName";

    private static final ResponseContext SUCCESSFUL_DELETE_ACCOUNT_CONTEXT = new ResponseContext() {
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

        AccountDto accountDto = (AccountDto) session.getAttribute(CURRENT_ACCOUNT);
        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);

        try {
            accountService.delete(accountDto);
            userService.delete(userDto);
            session.removeAttribute(CURRENT_ACCOUNT);
            session.removeAttribute(CURRENT_USER);
            session.removeAttribute(USERNAME);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

//        pagePath = context.getContextPath() + context.getHeader();
        return SUCCESSFUL_DELETE_ACCOUNT_CONTEXT;
    }
}
