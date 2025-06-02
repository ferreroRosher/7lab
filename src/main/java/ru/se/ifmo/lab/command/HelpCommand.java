package ru.se.ifmo.lab.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.CommandAction;
import ru.se.ifmo.cli.DefaultCommand;

public class HelpCommand extends DefaultCommand {
    private static final Logger logger = LoggerFactory.getLogger(HelpCommand.class);

    @CommandAction
    void help() {
        getProject().getCommands().forEach(command -> {
            String name = getProject().getCommands().getNameByType(command.getClass());
            String mask = command.getMask();
            String caption = command.getCaption();
            logger.info("{} - Command usage mask: {}\nDescription: {}", name, mask, caption);
        });

        logger.info("exit - Command usage mask: exit\nDescription: exit the program (without saving to file)\n");
    }

    @Override
    public String getCaption() {
        return "Display help information for available commands.";
    }
}
