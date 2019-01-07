package model;

import exceptions.WrongFieldClassException;
import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glis
 */
@Data
public final class CharacterProgress implements ICharacterProgress {
    /**
     * The id of the character this class is linked to.
     */
    private String characterId;

    /**
     * All extra fields added to the character progress.
     */
    private final Map<String, Object> fields = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addField(final @NonNull String key, final @NonNull Object field) {
        fields.put(key, field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getField(@NonNull String key, @NonNull Class<T> clazz) throws NoSuchFieldException, WrongFieldClassException {
        if(!fields.containsKey(key)) {
            throw new NoSuchFieldException();
        }
        final Object object = fields.get(key);
        if(!object.getClass().isAssignableFrom(clazz)) {
            throw new WrongFieldClassException();
        }
        return (T)object;
    }
}
