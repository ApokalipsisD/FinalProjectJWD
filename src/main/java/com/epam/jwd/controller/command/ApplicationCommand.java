package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.controller.command.impl.showPage.*;
import com.epam.jwd.dao.entity.Role;

import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {
    DEFAULT(DefaultCommand.getInstance()),
    SHOW_LOGIN(ShowLoginPageCommand.getInstance()),
    SHOW_MAIN(ShowMainPageCommand.getInstance()),
    SHOW_SIGN_UP(ShowSignUpPageCommand.getInstance()),
    SIGN_UP_COMMAND(SignUpCommand.getInstance()),
    LOGIN(LoginCommand.getInstance()),
    LOGOUT(LogoutCommand.getInstance()),
    SHOW_PROFILE_PAGE(ShowProfilePageCommand.getInstance()),
    SHOW_EDIT_PROFILE(ShowEditProfileCommand.getInstance()),
    EDIT_PROFILE(EditProfileCommand.getInstance()),
    SHOW_COURSES(ShowCoursesPageCommand.getInstance()),
    CREATE_COURSE(CreateCourseCommand.getInstance(), Role.ADMIN),
    CATALOG(CourseCommand.getInstance()),
    CHANGE_COURSE(ChangeCourseCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    COURSE(CourseCommand.getInstance()),
    DELETE_COURSE(DeleteCourseCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    JOIN_COURSE(JoinCourseCommand.getInstance()),
    DROP_COURSE(DropCourseCommand.getInstance()),
    SHOW_MY_COURSES(ShowMyCoursesCommand.getInstance()),
    SHOW_TEACHER_COURSES(ShowTeacherCoursesCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    REVIEW(ReviewCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    CHANGE_REVIEW(ChangeReviewCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    DELETE_REVIEW(DeleteReviewCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    DELETE_ACCOUNT(DeleteAccountCommand.getInstance());

    private final Command command;
    private final List<Role> allowRoles;

    ApplicationCommand(Command command, Role... roles) {
        this.command = command;
        this.allowRoles = roles != null && roles.length > 0 ? Arrays.asList(roles) : Role.valuesAsList();
    }

    public static Command getCommandByString(String name) {
        return Arrays.stream(ApplicationCommand.values())
                .filter(command -> command.toString().equalsIgnoreCase(name))
                .map(command -> command.command)
                .findFirst()
                .orElse(DefaultCommand.getInstance());
    }

    public Command getCommand() {
        return command;
    }

    public List<Role> getAllowRoles() {
        return allowRoles;
    }
}
