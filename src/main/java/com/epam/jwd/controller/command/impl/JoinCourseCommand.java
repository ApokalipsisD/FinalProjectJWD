package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.dao.entity.Role;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class JoinCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(JoinCourseCommand.class);

    private static final Command INSTANCE = new JoinCourseCommand();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/WEB-INF/jsp/course.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_ATTRIBUTE = "user";
    private static final String ID_ATTRIBUTE = "id";


    private static final ResponseContext SUCCESSFUL_JOIN_COURSE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return pagePath;
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
//        pagePath = context.getContextPath() + context.getHeader();
        HttpSession session = context.getCurrentSession().get();


        UserDto userDto = (UserDto) session.getAttribute(USER_ATTRIBUTE);

        Integer courseId = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));
        Integer studentId = userDto.getId();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date applicationDate = Date.valueOf(format.format(new GregorianCalendar().getTime()));
        AccountDto accountDto;
        try {
            accountDto = account.getAccountByUserId(userDto.getId());
            if (record.findRecordByCourseIdAndStudentId(courseId, studentId)) {
                record.create(new StudentHasCourseDto(courseId, studentId, applicationDate));
                if (accountDto.getRole().equals(Role.USER)) {
                    account.updateRole(accountDto.getId(), Role.STUDENT.getId());
                }
            }
            context.addAttributeToJsp("message", "Joined course successfully");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        pagePath = "/controller?command=catalog&course=" + context.getParameterByName(ID_ATTRIBUTE);
        return SUCCESSFUL_JOIN_COURSE_CONTEXT;
    }
}
