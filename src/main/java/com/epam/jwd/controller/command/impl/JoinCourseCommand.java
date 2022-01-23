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

import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.JOINED_COURSE_MESSAGE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;

public class JoinCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(JoinCourseCommand.class);

    private static final Command INSTANCE = new JoinCourseCommand();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/controller?command=catalog&course=";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

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
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        HttpSession session = context.getCurrentSession().get();
        pagePath = PAGE_PATH + context.getParameterByName(ID_ATTRIBUTE);

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);

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
            context.addAttributeToJsp(MESSAGE, JOINED_COURSE_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        return SUCCESSFUL_JOIN_COURSE_CONTEXT;
    }
}
