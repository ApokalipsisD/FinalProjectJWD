package com.epam.jwd.controller.command.impl.showPage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

public class ShowProfilePageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowCoursesPageCommand.class);

    private static final Command INSTANCE = new ShowProfilePageCommand();
    private static final AccountServiceImpl accountService = new AccountServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/profile.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String CURRENT_USER = "user";
    private static final String CURRENT_ACCOUNT = "account";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String DELIMITER = ":";

    private static final ResponseContext SHOW_PROFILE_PAGE_CONTEXT = new ResponseContext() {
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
        HttpSession session = context.getCurrentSession().get();
//        if (context.getCurrentSession().isPresent()) {
//            session = context.getCurrentSession().get();
//        } else {
//            return ERROR_CONTEXT;
//        }

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
        AccountDto accountDto;
        try {
            accountDto = accountService.getAccountByUserId(userDto.getId());
            session.setAttribute(CURRENT_ACCOUNT, accountDto);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        return SHOW_PROFILE_PAGE_CONTEXT;
    }
}

