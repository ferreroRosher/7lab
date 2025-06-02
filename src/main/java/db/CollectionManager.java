package db;

import java.model.Entity;
import ru.se.ifmo.db.entity.User;

import java.time.LocalDate;
import java.util.Map;

/**
 * Manages a collection of entities and provides CRUD operations.
 *
 * @param <T> type of entity managed by this controller
 */
public interface CollectionManager<T extends Entity> {

    /**
     * Returns the date when this controller was initialized.
     *
     * @return initialization date
     */
    LocalDate getInitializeDate();

    /**
     * Adds the specified entity to the collection.
     *
     * @param entity entity to add
     */
    void add(Long key, T entity);

    /**
     * Removes the specified entity from the collection.
     *
     * @param entity entity to remove
     */
    void remove(Long key);

    /**
     * Retrieves the entity with the given identifier.
     *
     * @param key
     * @return the matching entity, or null if none found
     */
    T get(long key);

    /**
     * Updates the entity with the given identifier.
     *
     * @param key
     * @param entity new state for the entity
     */
    void update(long key, T entity);

    /**
     * Returns a snapshot of all entities in the collection.
     *
     * <p>Modifications to the returned collection have no effect
     * on the controller's internal state.</p>
     *
     * @return unmodifiable collection of entities
     */
    Map<Long, T> getMap();

    /**
     * Removes all entities from the collection.
     */
    void clear();

    /**
     * Adds all entities from the specified collection.
     *
     * @param entities entities to add
     */
    void addAll(Map<Long, T> entities);

    /**
     * Returns the owner of this controller.
     *
     * @return the user who owns this collection
     */
    User getOwner();
}

