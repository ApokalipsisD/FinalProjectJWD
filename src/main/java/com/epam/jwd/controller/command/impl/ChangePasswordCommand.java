package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.MessageException;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

import static com.epam.jwd.controller.command.Attributes.CONFIRM_PASSWORD_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;
import static com.epam.jwd.controller.command.Attributes.NEW_PASSWORD_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.OLD_PASSWORD_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.PASSWORD_CHANGED_MESSAGE;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final Command INSTANCE = new ChangePasswordCommand();

    private static final String PAGE_PATH = "/controller?command=show_profile_page";
    private static final String FAIL_PAGE_PATH = "/controller?command=show_password_page";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_CHANGE_PASSWORD_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    private static final ResponseContext CHANGE_PASSWORD_FAILED_CONTEXT = new ResponseContext() {
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
        if(context.getHeader() == null){
            return ERROR_CONTEXT;
        }
        HttpSession session = context.getCurrentSession().get();

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
        String oldPassword = context.getParameterByName(OLD_PASSWORD_ATTRIBUTE);
        String newPassword = context.getParameterByName(NEW_PASSWORD_ATTRIBUTE);
        String confirmPassword = context.getParameterByName(CONFIRM_PASSWORD_ATTRIBUTE);

        try {
            if(!newPassword.equals(confirmPassword)){
                throw new ServiceException(MessageException.PASSWORD_MISMATCH_MESSAGE);
            }
            if(!oldPassword.equals(userDto.getPassword())){
                throw new ServiceException(MessageException.INCORRECT_PASSWORD_MESSAGE);
            }
            if(newPassword.equals(userDto.getPassword())){
                throw new ServiceException(MessageException.REPEATING_PASSWORD_MESSAGE);
            }
            UserDto currentUser = new UserDto(userDto.getId(), userDto.getLogin(), newPassword);
            userService.update(currentUser);
            session.setAttribute(CURRENT_USER, currentUser);
            context.addAttributeToJsp(MESSAGE, PASSWORD_CHANGED_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
            return CHANGE_PASSWORD_FAILED_CONTEXT;
        }

        return SUCCESSFUL_CHANGE_PASSWORD_CONTEXT;
    }
}
