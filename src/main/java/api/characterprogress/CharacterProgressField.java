package api.characterprogress;

import com.google.gson.JsonObject;
import lombok.NonNull;
import model.Character;
import model.CharacterProgress;
import model.ICharacterProgress;

/**
 * @author Glis
 */
public interface CharacterProgressField {
    /**
     * @return The name of the field that is being edited.
     */
    String getField();

    /**
     * @param character The character in the beginning state.
     * @param dataObject The data object that was gathered.
     * @return The character adjusted according to the data object.
     */
    Character doCharacterChanges(final @NonNull Character character, final @NonNull JsonObject dataObject);

    /**
     * @param characterProgress The current {@link CharacterProgress}.
     * @param dataObject The data object that was gathered.
     * @return The character progress adjusted to the data object.
     */
    ICharacterProgress doCharacterProgressChanges(final @NonNull ICharacterProgress characterProgress, final @NonNull JsonObject dataObject);
}
