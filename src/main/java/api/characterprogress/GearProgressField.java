package api.characterprogress;

import com.google.gson.JsonObject;
import exceptions.WrongFieldClassException;
import lombok.Data;
import lombok.NonNull;
import model.Character;
import model.ICharacterProgress;

/**
 * @author Glis
 */
public final class GearProgressField implements CharacterProgressField {
    /**
     * The field name of the item level equipped.
     */
    private final static String ITEM_LEVEL_EQUIPPED_FIELD_NAME = "item_level_equipped";

    /**
     * The field name of the total item level.
     */
    private final static String ITEM_LEVEL_TOTAL_FIELD_NAME = "item_level_total";

    /**
     * The field name of the the total artifact traits level field name.
     */
    private final static String ARTIFACT_TRAITS_FIELD_NAME = "artifact_traits";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getField() {
        return "gear";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Character doCharacterChanges(final @NonNull Character character, final @NonNull JsonObject dataObject) {
        //Nothing changes here
        return character;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICharacterProgress doCharacterProgressChanges(final @NonNull ICharacterProgress characterProgress, final @NonNull JsonObject dataObject) {
        //If we don't have the field, ignore it.
        if (!dataObject.has(getField())) {
            return characterProgress;
        }
        final JsonObject gearObject = dataObject.get(getField()).getAsJsonObject();
        GearField gearField = new GearField();
        try {
            gearField = characterProgress.getField(getField(), GearField.class);
        } catch (NoSuchFieldException | WrongFieldClassException ignored) {
        }
        if (gearObject.has(ITEM_LEVEL_EQUIPPED_FIELD_NAME)) {
            gearField.setItemLevelEquipped(gearObject.get(ITEM_LEVEL_EQUIPPED_FIELD_NAME).getAsInt());
        }
        if (gearObject.has(ITEM_LEVEL_TOTAL_FIELD_NAME)) {
            gearField.setItemLevelTotal(gearObject.get(ITEM_LEVEL_TOTAL_FIELD_NAME).getAsInt());
        }
        if (gearObject.has(ARTIFACT_TRAITS_FIELD_NAME)) {
            gearField.setArtifactTraits(gearObject.get(ARTIFACT_TRAITS_FIELD_NAME).getAsInt());
        }
        characterProgress.addField("gear", gearField);
        return characterProgress;
    }

    /**
     * The gear field that is being added to the object.
     */
    @Data
    public static class GearField {
        /**
         * The item level that the character has equipped.
         */
        private int itemLevelEquipped;

        /**
         * The total item level that the character has.
         */
        private int itemLevelTotal;

        /**
         * The total amount of artifact power the character has.
         */
        private int artifactTraits;
    }
}
