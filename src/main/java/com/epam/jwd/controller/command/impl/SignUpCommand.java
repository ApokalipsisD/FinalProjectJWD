package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Objects;

import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.LOGIN_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;
import static com.epam.jwd.controller.command.Attributes.PASSWORD_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.REPEAT_PASSWORD_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.USER_CREATED;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignUpCommand.class);

    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final Command INSTANCE = new SignUpCommand();

    private static final String PAGE_PATH = "/controller?command=show_login";
    private static final String FAIL_PAGE_PATH = "/controller?command=show_sign_up";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_SIGN_UP_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext SIGN_UP_FAILED_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
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

//        if(context.getCurrentSession().get().getAttribute())
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        String login = context.getParameterByName(LOGIN_ATTRIBUTE);
        String password = context.getParameterByName(PASSWORD_ATTRIBUTE);
        String repeatPassword = context.getParameterByName(REPEAT_PASSWORD_ATTRIBUTE);

        try {
            if (Objects.isNull(login) || Objects.isNull(password) || Objects.isNull(repeatPassword)) {
                throw new ServiceException(MessageException.ENTER_DATE_EXCEPTION);
            }
            if (!userService.checkRepeatPassword(password, repeatPassword)) {
                throw new ServiceException(MessageException.PASSWORD_MISMATCH_MESSAGE);
            }
            if (!userService.checkIfLoginFree(login)) {
                throw new ServiceException(MessageException.LOGIN_IS_TAKEN_EXCEPTION);
            }
            userService.create(new UserDto(login, password));
            context.addAttributeToJsp(MESSAGE, USER_CREATED);
        } catch (ServiceException e) {
            BasicConfigurator.configure();
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
            return SIGN_UP_FAILED_CONTEXT;
        }

        return SUCCESSFUL_SIGN_UP_CONTEXT;
    }
}
