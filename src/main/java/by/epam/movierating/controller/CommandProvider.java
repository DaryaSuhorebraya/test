package by.epam.movierating.controller;

import by.epam.movierating.command.Command;
import by.epam.movierating.controller.util.ParseCommandUtil;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

/**
 * Initializes command's map and provides its instance
 */
class CommandProvider {
    private static final Logger logger = Logger.getLogger(CommandProvider.class);

    private static final CommandProvider instance = new CommandProvider();
    private Map<String, Command> repository = new HashMap<>();
    private static final String DEFAULT_PAGE = "welcome-page";

    private CommandProvider() {
        repository = new ParseCommandUtil().parse();
    }

    /**
     * Returns an instance of CommandProvider
     * @return {@link CommandProvider} object
     */
    public static CommandProvider getInstance() {
        return instance;
    }

    /**
     * Returns Command object by its name
     * @param name tha name for invocation this command
     * @return {@link Command} object
     */
    Command getCommand(String name) {
        Command command = null;
        try {
            if (name != null) {
                command = repository.get(name);
            } else {
                command = repository.get(DEFAULT_PAGE);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.error(e);
        }

        return command;
    }
}
