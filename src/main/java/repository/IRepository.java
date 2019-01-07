package repository;

import lombok.NonNull;
import provider.IProvider;

/**
 * @author Glis
 */
public interface IRepository<T> extends IProvider<T> {
    /**
     * Attempts to save the object.
     *
     * @param object The object to save.
     * @return Whether or not the save was successful.
     */
    boolean save(final @NonNull T object);

    /**
     * Attempts to update the object.
     *
     * @param object The object to update.
     */
    void update(final @NonNull T object);
}
