package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteAccountCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteAccountCommand.class);

    private static final Command INSTANCE = new DeleteAccountCommand();
    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final AccountServiceImpl accountService = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl records = new StudentHasCourseServiceImpl();

    private static final String PAGE_PATH = "/controller?command=logout";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String CURRENT_USER = "user";
    private static final String CURRENT_ACCOUNT = "account";
    private static final String USERNAME = "userName";
    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";

    private static final ResponseContext SUCCESSFUL_DELETE_ACCOUNT_CONTEXT = new ResponseContext() {
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
        if(context.getHeader() == null){
            return ERROR_CONTEXT;
        }
        HttpSession session = context.getCurrentSession().get();
        AccountDto accountDto = (AccountDto) session.getAttribute(CURRENT_ACCOUNT);
        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);

        try {
//            accountService.delete(accountDto);
            List<StudentHasCourseDto> list = records.getRecordsByStudentId(userDto.getId());
            for (StudentHasCourseDto record : list) {
                records.delete(record);
            }
            userService.delete(userDto);
            session.removeAttribute(CURRENT_ACCOUNT);
            session.removeAttribute(CURRENT_USER);
            session.removeAttribute(USERNAME);
            context.addAttributeToJsp("message", "Account has been deleted");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        return SUCCESSFUL_DELETE_ACCOUNT_CONTEXT;
    }
}
