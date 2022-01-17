package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.api.Service;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.validator.impl.AccountValidator;

import javax.servlet.http.HttpSession;
import java.sql.Date;

public class EditProfileCommand implements Command {
    private static final Command INSTANCE = new EditProfileCommand();
    private static final Service<AccountDto, Integer> accountService = new AccountServiceImpl();
    private static final AccountValidator validator = new AccountValidator();

    private static final String PAGE_PATH = "/WEB-INF/jsp/profile.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final String CURRENT_USER = "user";
    private static final String CURRENT_ACCOUNT = "account";
    private static final String FIRST_NAME_ATTRIBUTE = "firstName";
    private static final String LAST_NAME_ATTRIBUTE = "lastName";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String BIRTH_DATE_ATTRIBUTE = "birthDate";
    private static final String USERNAME_ATTRIBUTE = "userName";

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

        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
        } else {
            return ERROR_CONTEXT;
        }

        AccountDto accountDto = (AccountDto) session.getAttribute(CURRENT_ACCOUNT);

        String firstName = context.getParameterByName(FIRST_NAME_ATTRIBUTE).equals("")
                ? accountDto.getFirstName() : context.getParameterByName(FIRST_NAME_ATTRIBUTE);

        String lastName = context.getParameterByName(LAST_NAME_ATTRIBUTE).equals("")
                ? accountDto.getLastName() : context.getParameterByName(LAST_NAME_ATTRIBUTE);

        String email = context.getParameterByName(EMAIL_ATTRIBUTE).equals("")
                ? accountDto.getEmail() : context.getParameterByName(EMAIL_ATTRIBUTE);

        String birthDateString = context.getParameterByName(BIRTH_DATE_ATTRIBUTE);



        try {
            validator.validateDate(birthDateString);
            Date birthDate = Date.valueOf(birthDateString);

            AccountDto accountDto1 = new AccountDto(accountDto.getId(), firstName, lastName, email, birthDate, accountDto.getRole().getId(), accountDto.getUserId());

            accountService.update(accountDto1);
            session.setAttribute(CURRENT_ACCOUNT, accountDto1);
        } catch (ServiceException e) {
            //
//            System.out.println(e.getMessage());
//            session.setAttribute("error", e.getMessage());
//            context.addAttributeToJsp("error", e.getMessage());

            return ERROR_CONTEXT;
        }
        return SUCCESSFUL_PROFILE_CONTEXT;
    }
}
