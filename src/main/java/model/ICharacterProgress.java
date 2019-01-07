package model;

import exceptions.WrongFieldClassException;
import lombok.NonNull;

/**
 * @author Glis
 */
public interface ICharacterProgress {
    /**
     * @return The id of the character this is linked to.
     */
    String getCharacterId();

    /**
     * @param characterId The id of the character this is linked to.
     */
    void setCharacterId(final @NonNull String characterId);

    /**
     * @param key   The key to the field we're adding.
     * @param field The field to add to the progress object.
     */
    void addField(final @NonNull String key, final @NonNull Object field);

    /**
     * @param key The key to the field we're getting.
     * @param clazz The class of the field we're getting.
     * @return The field object.
     */
    <T> T getField(final @NonNull String key, final @NonNull Class<T> clazz) throws NoSuchFieldException, WrongFieldClassException;
}
