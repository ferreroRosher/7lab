package ru.se.ifmo.cli.e2e;

import ru.se.ifmo.cli.Application;
import ru.se.ifmo.cli.Project;
import ru.se.ifmo.db.CollectionManager;
import ru.se.ifmo.db.DatabaseManager;
import ru.se.ifmo.db.entity.Entity;

public class DemoApp extends Application {
    private InMemoryCollectionManager collectionController;
    private NoOpDatabaseManager databaseController;

    public static DemoApp launch() {
        return Application.launch();
    }

    @Override
    public void beforeConfigure() {
        // create in-memory controllers
        collectionController = new InMemoryCollectionManager();
        databaseController = new NoOpDatabaseManager();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends CollectionManager<?>> DatabaseManager<T> getDatabaseController() {
        DatabaseManager<T> dc = (DatabaseManager<T>) databaseController;
        return dc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Entity> CollectionManager<T> getCollectionController() {
        CollectionManager<T> cc = (CollectionManager<T>) collectionController;
        return cc;
    }

    @Override
    public void configure(Project project) {
        var cmds = project.getCommands();
        cmds.register("hello", HelloCommand.class);
        cmds.register("add", AddEntityCommand.class);
        cmds.register("list", ListEntitiesCommand.class);
    }
}
