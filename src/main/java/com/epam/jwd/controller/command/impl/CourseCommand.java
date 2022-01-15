package com.epam.jwd.controller.command.impl;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.api.RequestContext;
import com.epam.jwd.controller.command.api.ResponseContext;
import com.epam.jwd.service.dto.CourseDto;
import com.epam.jwd.service.dto.StudentHasCourseDto;
import com.epam.jwd.service.dto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountServiceImpl;
import com.epam.jwd.service.impl.CourseServiceImpl;
import com.epam.jwd.service.impl.StudentHasCourseServiceImpl;
import com.epam.jwd.service.impl.UserServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CourseCommand implements Command {
    private static final Command INSTANCE = new CourseCommand();
    private static final UserServiceImpl user = new UserServiceImpl();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
    private static final StudentHasCourseServiceImpl record = new StudentHasCourseServiceImpl();


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
        HttpSession session;
        if (context.getCurrentSession().isPresent()) {
            session = context.getCurrentSession().get();
//            session.removeAttribute("error");
        } else {
            return ERROR_CONTEXT;
        }

        UserDto userDto = (UserDto) session.getAttribute("user");
        Integer id = Integer.valueOf(context.getParameterByName("course"));
        CourseDto courseDto;
        boolean isRecordExists = false;
        List<StudentHasCourseDto> studentsOnCourse;
        List<UserDto> students = new ArrayList<>();

        try {
            courseDto = catalog.getById(id);
            context.addAttributeToJsp("title", courseDto.getTitle());
            context.addAttributeToJsp("description", courseDto.getDescription());
            context.addAttributeToJsp("status", courseDto.getCourseStatus());
            context.addAttributeToJsp("startDate", courseDto.getStartDate());
            context.addAttributeToJsp("endDate", courseDto.getEndDate());
            context.addAttributeToJsp("teacher", account.getById(courseDto.getTeacherId()));
            context.addAttributeToJsp("id", courseDto.getId());

            if (!record.findRecordByCourseIdAndStudentId(courseDto.getId(), userDto.getId())) {
                isRecordExists = true;
            }
            context.addAttributeToJsp("record", isRecordExists);
            studentsOnCourse = record.getRecordsByCourseId(courseDto.getId());
            for(StudentHasCourseDto student: studentsOnCourse){
                students.add(user.getById(student.getStudentId()));
            }
            context.addAttributeToJsp("studentsOnCourse", students);
//            studentsOnCourse.forEach(student -> students.add(user.getById(student.getStudentId())));

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return SUCCESSFUL_COURSE_CONTEXT;
    }
}
