package jobs;

import api.characterprogress.CharacterProgressField;
import model.ICharacterProgress;
import model.MinimalCharacterInformation;

import java.util.Collection;
import java.util.function.BiFunction;

/**
 * @author Glis
 */
public interface ICharacterProgressJob extends BiFunction<Collection<MinimalCharacterInformation>, Collection<CharacterProgressField>, Collection<ICharacterProgress>> {
}
