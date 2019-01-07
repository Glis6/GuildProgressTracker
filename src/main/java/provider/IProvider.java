package provider;

import lombok.NonNull;

import java.util.Collection;

/**
 * @author Glis
 */
public interface IProvider<T> {
    /**
     * @return A {@link Collection} of all <tag>T</tag>'s
     */
    Collection<T> findAll();

    /**
     * @param id The id of the object to look for.
     * @return The found object.
     */
    T findOne(final @NonNull String id);
}
