package com.epam.jwd.controller.command.impl.showPage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MY_COURSES_ATTRIBUTE;

public class ShowMyCoursesCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowMyCoursesCommand.class);

    private static final Command INSTANCE = new ShowMyCoursesCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/myCourses.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SHOW_MY_COURSES_PAGE_CONTEXT = new ResponseContext() {
        @Override
        public String getPage() {
            return PAGE_PATH;
        }

        @Override
        public boolean isRedirect() {
            return false;
        }
    };

    public static Command getInstance() {
        return INSTANCE;
    }

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

    @Override
    public ResponseContext execute(RequestContext context) {
        HttpSession session = context.getCurrentSession().get();

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);

        Integer studentId = userDto.getId();
        List<CourseDto> myCourses = new ArrayList<>();
        try {
            List<StudentHasCourseDto> list = record.getRecordsByStudentId(studentId);
            for (StudentHasCourseDto record : list) {
                myCourses.add(catalog.getById(record.getCourseId()));
            }
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        session.setAttribute(MY_COURSES_ATTRIBUTE, myCourses);
        return SHOW_MY_COURSES_PAGE_CONTEXT;
    }
}