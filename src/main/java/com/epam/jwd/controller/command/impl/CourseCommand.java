package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.controller.command.impl.showPage.ShowCoursesPageCommand;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.dto.ReviewDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.ReviewServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.jwd.controller.command.Attributes.COURSE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.CURRENT_USER;
import static com.epam.jwd.controller.command.Attributes.DELIMITER;
import static com.epam.jwd.controller.command.Attributes.DESCRIPTION_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.END_DATE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ERROR_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.ID_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.RECORD_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.REVIEWS_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.START_DATE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.STATUS_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.STUDENTS_ON_COURSE_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.TEACHER_ATTRIBUTE;
import static com.epam.jwd.controller.command.Attributes.TITLE_ATTRIBUTE;

public class CourseCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowCoursesPageCommand.class);

    private static final Command INSTANCE = new CourseCommand();
    private static final UserServiceImpl user = new UserServiceImpl();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();
    private static final ReviewServiceImpl review = new ReviewServiceImpl();

    private static final String PAGE_PATH = "/WEB-INF/jsp/course.jsp";
    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/error.jsp";

    private static final ResponseContext SUCCESSFUL_COURSE_CONTEXT = new ResponseContext() {
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
        HttpSession session = context.getCurrentSession().get();

        UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
        Integer id = Integer.valueOf(context.getParameterByName(COURSE_ATTRIBUTE));
        CourseDto courseDto;
        boolean isRecordExists = false;
        boolean isReviewExists;
        List<StudentHasCourseDto> studentsOnCourse;
        Map<UserDto, Boolean> mapUsers = new HashMap<>();
        Map<Integer, ReviewDto> reviews = new HashMap<>();

        try {
            courseDto = catalog.getById(id);
            context.addAttributeToJsp(TITLE_ATTRIBUTE, courseDto.getTitle());
            context.addAttributeToJsp(DESCRIPTION_ATTRIBUTE, courseDto.getDescription());
            context.addAttributeToJsp(STATUS_ATTRIBUTE, courseDto.getCourseStatus());
            context.addAttributeToJsp(START_DATE_ATTRIBUTE, courseDto.getStartDate());
            context.addAttributeToJsp(END_DATE_ATTRIBUTE, courseDto.getEndDate());
            context.addAttributeToJsp(TEACHER_ATTRIBUTE, account.getById(courseDto.getTeacherId()));
            context.addAttributeToJsp(ID_ATTRIBUTE, courseDto.getId());

            if (!record.findRecordByCourseIdAndStudentId(courseDto.getId(), userDto.getId())) {
                isRecordExists = true;
            }
            context.addAttributeToJsp(RECORD_ATTRIBUTE, isRecordExists);

            studentsOnCourse = record.getRecordsByCourseId(courseDto.getId());

            for (StudentHasCourseDto student : studentsOnCourse) {
                UserDto userOnCourse = user.getById(student.getStudentId());
                isReviewExists = !review.findReviewByCourseIdAndStudentId(courseDto.getId(), userOnCourse.getId());
                mapUsers.put(userOnCourse, isReviewExists);
            }
            context.addAttributeToJsp(STUDENTS_ON_COURSE_ATTRIBUTE, mapUsers);

            List<ReviewDto> reviewDtoList = review.getReviewsByCourseId(courseDto.getId());
            reviewDtoList.forEach(review -> reviews.put(review.getStudentId(), review));
            context.addAttributeToJsp(REVIEWS_ATTRIBUTE, reviews);
        } catch (ServiceException e) {
            logger.error(ERROR_ATTRIBUTE + DELIMITER + e.getMessage());
            context.addAttributeToJsp(ERROR_ATTRIBUTE, e.getMessage());
        }

        return SUCCESSFUL_COURSE_CONTEXT;
    }
}
