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

import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.LEFT_COURSE_MESSAGE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;

public class DropCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DropCourseCommand.class);

    private static final Command INSTANCE = new DropCourseCommand();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static String pagePath;

    private static final String PAGE_PATH = "/controller?command=catalog&course=";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

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
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        HttpSession session = context.getCurrentSession().get();
        pagePath = PAGE_PATH + context.getParameterByName(ID_ATTRIBUTE);
        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);

        Integer courseId = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));
        Integer studentId = userDto.getId();

        StudentHasCourseDto recordStudent;
        try {
            recordStudent = record.getRecordByCourseIdAndStudentId(courseId, studentId);
            record.delete(recordStudent);
            context.addAttributeToJsp(MESSAGE, LEFT_COURSE_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
        return SUCCESSFUL_DROP_COURSE_CONTEXT;
    }
}
