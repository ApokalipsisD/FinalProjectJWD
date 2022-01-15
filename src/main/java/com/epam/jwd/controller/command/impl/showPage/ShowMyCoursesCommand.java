package com.epam.jwd.controller.command.impl.showPage;

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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ShowMyCoursesCommand implements Command {
    private static final Command INSTANCE = new ShowMyCoursesCommand();
    private static final CourseServiceImpl catalog = new CourseServiceImpl();
    private static final AccountServiceImpl account = new AccountServiceImpl();
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

        UserDto userDto = (UserDto) session.getAttribute("user");
//        AccountDto accountDto = account.getAccountByUserId(userDto.getId());

        Integer studentId = userDto.getId();
        List<StudentHasCourseDto> list = record.getRecordsByStudentId(studentId);
        List<CourseDto> myCourses = new ArrayList<>();
        try {
            for (StudentHasCourseDto record : list) {
                myCourses.add(catalog.getById(record.getCourseId()));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

//        List<CourseDto> myCourses = list.stream().map(records -> catalog.getById(records.)).collect(Collectors.toList())
        session.setAttribute("myCourses", myCourses);

        return SHOW_MY_COURSES_PAGE_CONTEXT;
    }
}