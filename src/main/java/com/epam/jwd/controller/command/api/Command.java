package com.epam.jwd.controller.command.api;

import com.epam.jwd.controller.command.ApplicationCommand;

/**
 * Interface which provides command pattern methods for classes
 */
public interface Command {

    /**
     * Method which executes current command
     *
     * @param context - {@link RequestContext}
     * @return - {@link ResponseContext}
     */
    ResponseContext execute(RequestContext context);

    /**
     * Method which gets command by its name from all amount of available commands
     *
     * @param name - command name
     * @return - command
     */
    static Command of(String name) {
        return ApplicationCommand.getCommandByString(name);
    }
}
