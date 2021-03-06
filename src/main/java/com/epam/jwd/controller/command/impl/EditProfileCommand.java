package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import com.epam.jwd.service.validator.impl.AccountValidator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Date;

import static com.epam.jwd.controller.command.Attributes.BIRTH_DATE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.CURRENT_ACCOUNT;
import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.EMAIL_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.FIRST_NAME_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.LAST_NAME_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;
import static com.epam.jwd.controller.command.Attributes.PROFILE_UPDATED_MESSAGE;
import static com.epam.jwd.controller.command.Attributes.USERNAME;

public class EditProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);

    private static final Command INSTANCE = new EditProfileCommand();
    private static final Service<AccountDto, Integer> accountService = new AccountServiceImpl();
    private static final Service<UserDto, Integer> userService = new UserServiceImpl();
    private static final AccountValidator validator = new AccountValidator();

    private static final String PAGE_PATH = "/controller?command=show_profile_page";
    private static final String FAIL_PAGE_PATH = "/WEB-INF/jsp/editProfile.jsp";
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

    private static final ResponseContext EDIT_PROFILE_FAILED_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return FAIL_PAGE_PATH;
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
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        HttpSession session = context.getCurrentSession().get();

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
        AccountDto accountDto = (AccountDto) session.getAttribute(CURRENT_ACCOUNT);

        String login = context.getParameterByName(USERNAME);

        String firstName = context.getParameterByName(FIRST_NAME_ATTRIBUTE).isBlank()
                ? accountDto.getFirstName() : context.getParameterByName(FIRST_NAME_ATTRIBUTE);

        String lastName = context.getParameterByName(LAST_NAME_ATTRIBUTE).isBlank()
                ? accountDto.getLastName() : context.getParameterByName(LAST_NAME_ATTRIBUTE);

        String email = context.getParameterByName(EMAIL_ATTRIBUTE).isBlank()
                ? accountDto.getEmail() : context.getParameterByName(EMAIL_ATTRIBUTE);

        Date birthDate = context.getParameterByName(BIRTH_DATE_ATTRIBUTE).isBlank()
                ? accountDto.getBirthDate() : Date.valueOf(context.getParameterByName(BIRTH_DATE_ATTRIBUTE));

        try {
            if (!login.trim().equals(userDto.getLogin())) {
                UserDto currentUser = new UserDto(userDto.getId(), login, userDto.getPassword());
                userService.update(currentUser);
                session.setAttribute(CURRENT_USER, currentUser);
                session.setAttribute(USERNAME, currentUser.getLogin());
            }
            AccountDto accountDto1 = new AccountDto(accountDto.getId(), firstName, lastName, email, birthDate, accountDto.getRole().getId(), accountDto.getUserId());
            accountService.update(accountDto1);
            session.setAttribute(CURRENT_ACCOUNT, accountDto1);
            context.addAttributeToJsp(MESSAGE, PROFILE_UPDATED_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
            return EDIT_PROFILE_FAILED_CONTEXT;
        }
        return SUCCESSFUL_PROFILE_CONTEXT;
    }
}
