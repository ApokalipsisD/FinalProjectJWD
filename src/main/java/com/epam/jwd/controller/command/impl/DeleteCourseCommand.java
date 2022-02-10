package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.showPage.ShowCoursesPageCommand;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

import static com.epam.jwd.controller.command.Attributes.COURSE_DELETED_MESSAGE;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.MESSAGE;

public class DeleteCourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowCoursesPageCommand.class);

    private static final Command INSTANCE = new DeleteCourseCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/controller?command=show_courses";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

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
        if (context.getHeader() == null) {
            return ERROR_CONTEXT;
        }
        Integer id = Integer.valueOf(context.getParameterByName(ID_ATTRIBUTE));
        CourseDto courseDto;
        List<StudentHasCourseDto> list;
        List<ReviewDto> reviewDtoList;
        try {
            courseDto = catalog.getById(id);
            list = record.getRecordsByCourseId(courseDto.getId());
            for (StudentHasCourseDto student : list) {
                record.delete(student);
            }
            reviewDtoList = review.getReviewsByCourseId(courseDto.getId());
            for(ReviewDto reviewDto: reviewDtoList){
                review.delete(reviewDto);
            }
            catalog.delete(courseDto);
            context.addAttributeToJsp(MESSAGE, COURSE_DELETED_MESSAGE);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }
        return SUCCESSFUL_DELETE_COURSE_CONTEXT;
    }
}
