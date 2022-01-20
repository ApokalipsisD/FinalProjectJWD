package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

public class DropCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DropCourseCommand.class);

    private static final Command INSTANCE = new DropCourseCommand();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/WEB-INF/jsp/course.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String CURRENT_USER = "user";
    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ID_ATTRIBUTE = "id";

    private static final ResponseContext SUCCESSFUL_DROP_COURSE_CONTEXT = new ResponseContext() {
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
        HttpSession session = context.getCurrentSession().get();
//        pagePath = context.getContextPath() + context.getHeader();

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);

        Integer courseId = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));
        Integer studentId = userDto.getId();

        StudentHasCourseDto recordStudent;
        try {
            recordStudent = record.getRecordByCourseIdAndStudentId(courseId, studentId);
            record.delete(recordStudent);
            context.addAttributeToJsp("message", "You left the course");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
        pagePath = "/controller?command=catalog&course=" + context.getParameterByName(ID_ATTRIBUTE);
        return SUCCESSFUL_DROP_COURSE_CONTEXT;
    }
}
