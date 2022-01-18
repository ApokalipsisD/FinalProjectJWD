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
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class JoinCourseCommand implements Command {
    private static final Command INSTANCE = new JoinCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/WEB-INF/jsp/course.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_JOIN_COURSE_CONTEXT = new ResponseContext() {
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
        pagePath = context.getContextPath() + context.getHeader();
        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
//            session.removeAttribute("error");
        } else {
            return ERROR_CONTEXT;
        }

        UserDto userDto = (UserDto) session.getAttribute("user");
        AccountDto accountDto = null;

        Integer courseId = Integer.valueOf(context.getParameterByName("id"));
        Integer studentId = userDto.getId();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date applicationDate = Date.valueOf(format.format(new GregorianCalendar().getTime()));

        try {
            accountDto = account.getAccountByUserId(userDto.getId());
            if (record.findRecordByCourseIdAndStudentId(courseId, studentId)) {
                record.create(new StudentHasCourseDto(courseId, studentId, applicationDate));
                if (accountDto.getRole().equals(Role.USER)) {
                    account.updateRole(accountDto.getId(), Role.STUDENT.getId());
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return SUCCESSFUL_JOIN_COURSE_CONTEXT;
    }
}
