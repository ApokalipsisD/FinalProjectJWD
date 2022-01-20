package com.epam.jwd.controller.command.impl.showPage;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.AccountDto;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowTeacherCoursesCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowTeacherCoursesCommand.class);

    private static final Command INSTANCE = new ShowTeacherCoursesCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/teacherCourses.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String USER_ATTRIBUTE = "user";
    private static final String TEACHER_COURSES_ATTRIBUTE = "teacherCourses";
    private static final String DELIMITER = ":";


    private static final ResponseContext SHOW_TEACHER_COURSES_PAGE_CONTEXT = new ResponseContext() {
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

        UserDto userDto = (UserDto) session.getAttribute(USER_ATTRIBUTE);

        AccountDto accountDto;
        try {
            accountDto = account.getAccountByUserId(userDto.getId());
            Integer teacherId = accountDto.getId();
            List<CourseDto> list = catalog.getCoursesByTeacherId(teacherId);
            session.setAttribute(TEACHER_COURSES_ATTRIBUTE, list);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        return SHOW_TEACHER_COURSES_PAGE_CONTEXT;
    }
}
