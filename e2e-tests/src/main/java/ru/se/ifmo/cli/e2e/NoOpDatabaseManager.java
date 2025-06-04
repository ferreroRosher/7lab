package ru.se.ifmo.cli.e2e;

import ru.se.ifmo.db.DatabaseManager;

public class NoOpDatabaseManager implements DatabaseManager<InMemoryCollectionManager> {
    @Override
    public boolean load(InMemoryCollectionManager c) {
        return true;
    }

    @Override
    public boolean save(InMemoryCollectionManager c) {
        return true;
    }
}
