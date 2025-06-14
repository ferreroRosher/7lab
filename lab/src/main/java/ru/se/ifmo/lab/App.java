package ru.se.ifmo.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.se.ifmo.cli.Application;
import ru.se.ifmo.cli.AuthenticationController;
import ru.se.ifmo.cli.CommandsScanner;
import ru.se.ifmo.cli.Project;
import ru.se.ifmo.db.table.UserTable;
import ru.se.ifmo.lab.auth.Sha256Encoder;
import ru.se.ifmo.lab.command.ClearCommand;
import ru.se.ifmo.lab.command.ExecuteScriptCommand;
import ru.se.ifmo.lab.command.FilterLessThanNationalityCommand;
import ru.se.ifmo.lab.command.HelpCommand;
import ru.se.ifmo.lab.command.InfoCommand;
import ru.se.ifmo.lab.command.InsertCommand;
import ru.se.ifmo.lab.command.PrintFieldAscendingPassportIdCommand;
import ru.se.ifmo.lab.command.PrintUniqueNationalityCommand;
import ru.se.ifmo.lab.command.RemoveGreaterKeyCommand;
import ru.se.ifmo.lab.command.RemoveKeyCommand;
import ru.se.ifmo.lab.command.RemoveLowerCommand;
import ru.se.ifmo.lab.command.ShowCommand;
import ru.se.ifmo.lab.command.UpdateIdCommand;
import ru.se.ifmo.lab.db.PersonCollectionManager;
import ru.se.ifmo.lab.db.PersonDatabaseManager;
import ru.se.ifmo.lab.db.PersonTable;

public class App extends Application {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    private PersonCollectionManager collectionManager;
    private PersonDatabaseManager databaseManager;

    private UserTable userTable;

    @Override
    public void beforeConfigure() {
        collectionManager = new PersonCollectionManager();
        databaseManager = new PersonDatabaseManager();
    }

    @Override
    public void configure(Project project) {
        databaseManager.setPersonTable(getInjector().getInstance(PersonTable.class));
        project.getCommands().register("help", HelpCommand.class);
        project.getCommands().register("clear", ClearCommand.class);
        project.getCommands().register("execute_script", ExecuteScriptCommand.class);
        project.getCommands().register("fltn", FilterLessThanNationalityCommand.class);
        project.getCommands().register("info", InfoCommand.class);
        project.getCommands().register("insert", InsertCommand.class);
        project.getCommands().register("pfapi", PrintFieldAscendingPassportIdCommand.class);
        project.getCommands().register("pun", PrintUniqueNationalityCommand.class);
        project.getCommands().register("remove_greater", RemoveGreaterKeyCommand.class);
        project.getCommands().register("remove_key", RemoveKeyCommand.class);
        project.getCommands().register("remove_lower", RemoveLowerCommand.class);
//        project.getCommands().register("save", SaveCommand.class);
        project.getCommands().register("show", ShowCommand.class);
        project.getCommands().register("update", UpdateIdCommand.class);
    }

    @Override
    public void afterConfigure(Project project) {
        userTable = new UserTable(project.getSQLConnectionController().getConnection());
    }

    @Override
    public PersonDatabaseManager getDatabaseController() {
        return databaseManager;
    }

    @Override
    public PersonCollectionManager getCollectionController() {
        return collectionManager;
    }

    public static void main(String[] args) {
        App app = launch();

        CommandsScanner scanner = new CommandsScanner(System.in);
        AuthenticationController authenticationController = new AuthenticationController(
                app.userTable, scanner, new Sha256Encoder()
        );
        app.getCollectionController().setOwner(authenticationController.authenticate());

        try {
            if (!app.getDatabaseController().load(app.getCollectionController())) {
                System.out.printf("Не удалось загрузить данные базы данных.%n");
                System.exit(1);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        while (true) {
            try {
                System.out.print("Enter command: ");
                String[] command = scanner.nextCommand().split(" ");
                execute(command);
            } catch (Exception exception) {
                log.error("Something went wrong", exception);
            }
        }
    }
}
