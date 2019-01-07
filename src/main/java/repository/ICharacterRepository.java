package repository;

import lombok.NonNull;
import model.Character;

/**
 * @author Glis
 */
public interface ICharacterRepository extends IRepository<Character> {
    /**
     * Updates the character and gets the id in the database.
     *
     * @param character The character to update.
     * @return The id of the character.
     */
    String updateAndGetId(final @NonNull Character character);
}
