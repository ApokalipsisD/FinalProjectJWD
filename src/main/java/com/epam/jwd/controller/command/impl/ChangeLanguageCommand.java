package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeLanguageCommand.class);

    private static final UserServiceImpl userService = new UserServiceImpl();
    private static final Command INSTANCE = new ChangeLanguageCommand();

    private static final String PAGE_PATH = "/controller?command=show_profile_page";
    private static final String FAIL_PAGE_PATH = "/controller?command=show_password_page";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static String pagePath;

    private static final ResponseContext SUCCESSFUL_CHANGE_LANGUAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return pagePath;
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
//        if(context.getHeader() == null){
//            return ERROR_CONTEXT;
//        }
        HttpSession session = context.getCurrentSession().get();
        String lang = context.getParameterByName("lang");
        if(lang.equals("ru")){
            session.setAttribute("language", "ru");
        } else {
            session.setAttribute("language", "en");
        }
        pagePath = context.getContextPath() + context.getHeader();

        return SUCCESSFUL_CHANGE_LANGUAGE_CONTEXT;
    }
}