package com.epam.jwd.controller.command;

import com.epam.jwd.controller.command.api.Command;
import com.epam.jwd.controller.command.impl.ChangeCourseCommand;
import com.epam.jwd.controller.command.impl.ChangeLanguageCommand;
import com.epam.jwd.controller.command.impl.ChangePasswordCommand;
import com.epam.jwd.controller.command.impl.ChangeReviewCommand;
import com.epam.jwd.controller.command.impl.CourseCommand;
import com.epam.jwd.controller.command.impl.CreateCourseCommand;
import com.epam.jwd.controller.command.impl.DefaultCommand;
import com.epam.jwd.controller.command.impl.DeleteAccountCommand;
import com.epam.jwd.controller.command.impl.DeleteCourseCommand;
import com.epam.jwd.controller.command.impl.DeleteReviewCommand;
import com.epam.jwd.controller.command.impl.DropCourseCommand;
import com.epam.jwd.controller.command.impl.EditProfileCommand;
import com.epam.jwd.controller.command.impl.JoinCourseCommand;
import com.epam.jwd.controller.command.impl.LoginCommand;
import com.epam.jwd.controller.command.impl.LogoutCommand;
import com.epam.jwd.controller.command.impl.ReviewCommand;
import com.epam.jwd.controller.command.impl.SignUpCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowCoursesPageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowEditProfileCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowErrorPageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowLoginPageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowMainPageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowMyCoursesCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowPasswordPageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowProfilePageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowSignUpPageCommand;
import com.epam.jwd.controller.command.impl.showPage.ShowTeacherCoursesCommand;
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
    SHOW_ERROR_PAGE(ShowErrorPageCommand.getInstance()),
    CHANGE_LANGUAGE(ChangeLanguageCommand.getInstance());

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
