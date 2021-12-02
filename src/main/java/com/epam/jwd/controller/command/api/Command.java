package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.ApplicationCommand;

public interface Command {

    ResponseContext execute(RequestContext context);

    static Command of(String name) {
        return ApplicationCommand.getCommandByString(name);
    }
}