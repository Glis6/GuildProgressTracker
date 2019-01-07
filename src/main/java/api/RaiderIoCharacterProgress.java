package api;

import api.characterprogress.CharacterProgressField;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import model.Character;
import model.CharacterProgress;
import model.ICharacterProgress;
import model.MinimalCharacterInformation;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Glis
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class RaiderIoCharacterProgress extends RaiderIoApi {
    /**
     * Creates the request URL for the {@link MinimalCharacterInformation}.
     */
    private final static Function<MinimalCharacterInformation, String> GENERATE_REQUEST_URL = characterInformation -> String.format("characters/profile?region=%s&realm=%s&name=%s", characterInformation.getRegion(), characterInformation.getRealm(), characterInformation.getName());

    /**
     * Creates the request fields for the URL.
     */
    private final static Function<Collection<CharacterProgressField>, String> GENERATE_REQUEST_FIELDS = characterProgressFields -> {
        if(characterProgressFields == null || characterProgressFields.isEmpty()) {
            return "";
        }
        return "&fields=" + characterProgressFields.stream().map(CharacterProgressField::getField).collect(Collectors.joining(","));
    };

    /**
     * The {@link MinimalCharacterInformation} about the character we're looking up.
     */
    private final MinimalCharacterInformation characterInformation;

    /**
     * The {@link Collection} of {@link CharacterProgressField}s that we're processing.
     */
    private final Collection<CharacterProgressField> characterProgressFields = new HashSet<>();

    /**
     * @param characterInformation The {@link MinimalCharacterInformation} about the character we're looking up.
     * @param characterProgressFields The {@link Collection} of {@link CharacterProgressField}s that we're processing.
     */
    public RaiderIoCharacterProgress(final MinimalCharacterInformation characterInformation, Collection<CharacterProgressField> characterProgressFields) {
        this.characterInformation = characterInformation;
        this.characterProgressFields.addAll(characterProgressFields);
    }

    /**
     * @return The character progress in a json object.
     */
    public CombinedResult getCharacterProgress() throws Exception {
        final JsonObject jsonObject = attemptRequest(GENERATE_REQUEST_URL.apply(characterInformation) + GENERATE_REQUEST_FIELDS.apply(characterProgressFields));
        Character character = new Character();

        for (final BasicInformation basicInformation : BasicInformation.values()) {
            if(jsonObject.has(basicInformation.getField())) {
                basicInformation.extractionConsumer.accept(jsonObject.get(basicInformation.getField()), character);
            }
        }

        ICharacterProgress characterProgress = new CharacterProgress();
        for (CharacterProgressField characterProgressField : characterProgressFields) {
            character = characterProgressField.doCharacterChanges(character, jsonObject);
            characterProgress = characterProgressField.doCharacterProgressChanges(characterProgress, jsonObject);
        }
        return new CombinedResult(character, characterProgress);
    }

    /**
     * A wrapper class for the result from getting character progress.
     */
    @Data
    public static class CombinedResult {
        /**
         * The {@link Character} that got changed.
         */
        private final Character character;

        /**
         * The {@link CharacterProgress} that was gathered.
         */
        private final ICharacterProgress characterProgress;
    }

    /**
     * All basic information that gets collected about a character.
     */
    private enum BasicInformation {
        /**
         * The name of the character.
         */
        NAME((jsonElement, character) -> character.setName(jsonElement.getAsString())),

        /**
         * The race of the character.
         */
        RACE((jsonElement, character) -> character.setRaceId(jsonElement.getAsString())),

        /**
         * The class of the character.
         */
        CLASS((jsonElement, character) -> character.setClassId(jsonElement.getAsString())),

        /**
         * The gender of the character.
         */
        GENDER((jsonElement, character) -> character.setGender(jsonElement.getAsString())),

        /**
         * The thumbnail url of the character.
         */
        THUMBNAIL_URL((jsonElement, character) -> character.getCharacterUrls().setThumbnailUrl(jsonElement.getAsString())),

        /**
         * The profile url of the character.
         */
        PROFILE_URL((jsonElement, character) -> character.getCharacterUrls().setProfileUrl(jsonElement.getAsString()));

        /**
         * A {@link java.util.function.Consumer} to extract from the element to the requested object type.
         */
        private final BiConsumer<JsonElement, Character> extractionConsumer;

        /**
         * @param extractionConsumer A {@link java.util.function.Consumer} to extract from the element to the requested object type.
         */
        BasicInformation(final @NonNull BiConsumer<JsonElement, Character> extractionConsumer) {
            this.extractionConsumer = extractionConsumer;
        }

        /**
         * @return The field to take.
         */
        public final String getField() {
            return name().toLowerCase();
        }
    }
}
