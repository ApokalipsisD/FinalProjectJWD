package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.showPage.ShowCoursesPageCommand;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class DeleteCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowCoursesPageCommand.class);

    private static final Command INSTANCE = new DeleteCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();

    private static final String PAGE_PATH = "/controller?command=show_courses";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";
    private static final String DELIMITER = ":";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String ID_ATTRIBUTE = "id";
    private static String pagePath;

    private static final ResponseContext SUCCESSFUL_DELETE_COURSE_CONTEXT = new ResponseContext() {
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
        Integer id = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));
        CourseDto courseDto;
        List<StudentHasCourseDto> list;
        try {
            courseDto = catalog.getById(id);
            list = record.getRecordsByCourseId(courseDto.getId());
            for (StudentHasCourseDto student : list) {
                record.delete(student);
            }
            catalog.delete(courseDto);
            context.addAttributeToJsp("message", "Course was deleted");
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
//        pagePath = "/controller?command=catalog&course=" + context.getParameterByName(ID_ATTRIBUTE);

        return SUCCESSFUL_DELETE_COURSE_CONTEXT;
    }
}
