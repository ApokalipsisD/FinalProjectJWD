package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.impl.*;
import com.epam.jwd.controller.command.impl.showPage.*;
import com.epam.jwd.dao.entity.Role;

import java.util.Arrays;
import java.util.List;

public enum ApplicationCommand {
    DEFAULT(DefaultCommand.getInstance()),
    SHOW_LOGIN(ShowLoginPageCommand.getInstance(), Role.UNAUTHORIZED),
    SHOW_MAIN(ShowMainPageCommand.getInstance()),
    SHOW_SIGN_UP(ShowSignUpPageCommand.getInstance(), Role.UNAUTHORIZED),
    SIGN_UP_COMMAND(SignUpCommand.getInstance(), Role.UNAUTHORIZED),
    LOGIN(LoginCommand.getInstance(), Role.UNAUTHORIZED),
    LOGOUT(LogoutCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_PROFILE_PAGE(ShowProfilePageCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_EDIT_PROFILE(ShowEditProfileCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    EDIT_PROFILE(EditProfileCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_COURSES(ShowCoursesPageCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    CREATE_COURSE(CreateCourseCommand.getInstance(), Role.ADMIN),
    CATALOG(CourseCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    CHANGE_COURSE(ChangeCourseCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    COURSE(CourseCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    DELETE_COURSE(DeleteCourseCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    JOIN_COURSE(JoinCourseCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    DROP_COURSE(DropCourseCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_MY_COURSES(ShowMyCoursesCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_TEACHER_COURSES(ShowTeacherCoursesCommand.getInstance(), Role.TEACHER),
    REVIEW(ReviewCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    CHANGE_REVIEW(ChangeReviewCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    DELETE_REVIEW(DeleteReviewCommand.getInstance(), Role.TEACHER, Role.ADMIN),
    DELETE_ACCOUNT(DeleteAccountCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_PASSWORD_PAGE(ShowPasswordPageCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    CHANGE_PASSWORD(ChangePasswordCommand.getInstance(), Role.USER, Role.STUDENT, Role.TEACHER, Role.ADMIN),
    SHOW_ERROR_PAGE(ShowErrorPageCommand.getInstance());

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

    public static ApplicationCommand getCommands(String commandName) {
        for (ApplicationCommand command : values()) {
            if (command.name().equalsIgnoreCase(commandName)) {
                return command;
            }
        }
        return DEFAULT;
    }
}
