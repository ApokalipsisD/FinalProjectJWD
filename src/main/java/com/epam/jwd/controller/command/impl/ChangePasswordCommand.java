package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final Command INSTANCE = new ChangePasswordCommand();

    private static final String PAGE_PATH = "/controller?command=show_profile_page";
    private static final String FAIL_PAGE_PATH = "/controller?command=show_password_page";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String CURRENT_USER = "user";
    private static final String OLD_PASSWORD_ATTRIBUTE = "oldPass";
    private static final String NEW_PASSWORD_ATTRIBUTE = "newPass";
    private static final String CONFIRM_PASSWORD_ATTRIBUTE = "confirmNewPass";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String DELIMITER = ":";

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
            if (!userService.checkRepeatPassword(newPassword, confirmPassword)) {
                throw new ServiceException("Password mismatch");
            }
            if(!oldPassword.equals(userDto.getPassword())){
                throw new ServiceException("Incorrect password");
            }
            if(newPassword.equals(userDto.getPassword())){
                throw new ServiceException("The new password cannot be the same as the previous one");
            }
            UserDto currentUser = new UserDto(userDto.getId(), userDto.getLogin(), newPassword);
            userService.update(currentUser);
            session.setAttribute(CURRENT_USER, currentUser);
            context.addAttributeToJsp("message", "Password successfully changed");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
            return CHANGE_PASSWORD_FAILED_CONTEXT;
        }

        return SUCCESSFUL_CHANGE_PASSWORD_CONTEXT;
    }
}
