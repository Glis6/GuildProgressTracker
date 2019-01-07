package jobs;

import api.RaiderIoCharacterProgress;
import api.characterprogress.CharacterProgressField;
import lombok.Data;
import model.ICharacterProgress;
import model.MinimalCharacterInformation;
import repository.ICharacterRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Glis
 */
@Data
public final class CharacterProgressJob implements ICharacterProgressJob {
    /**
     * The {@link ExecutorService} that does the event execution.
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link ICharacterRepository} to save the character in.
     */
    private final ICharacterRepository characterRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<ICharacterProgress> apply(Collection<MinimalCharacterInformation> input, Collection<CharacterProgressField> characterProgressFields) {
        final Collection<ICharacterProgress> characterProgressList = new HashSet<>();

        // Register a new completion service for this loop.
        final CompletionService<RaiderIoCharacterProgress.CombinedResult> completionService = new ExecutorCompletionService<>(executor);
        input.forEach(minimalCharacterInformation -> completionService.submit(() -> new RaiderIoCharacterProgress(minimalCharacterInformation, characterProgressFields).getCharacterProgress()));

        for (int i = 0; i < input.size(); i++) {
            try {
                // Get the next completed future.
                Future<RaiderIoCharacterProgress.CombinedResult> future = completionService.take();

                // If the future is null, then that means there was no completion in time.
                if (future == null) {
                    continue;
                }

                final RaiderIoCharacterProgress.CombinedResult combinedResult = future.get();
                //Update the character with the latest information
                final String characterId = characterRepository.updateAndGetId(combinedResult.getCharacter());

                final ICharacterProgress characterProgress = combinedResult.getCharacterProgress();
                characterProgress.setCharacterId(characterId);

                characterProgressList.add(characterProgress);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Getting the response timed out.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An exception occurred while getting data.", e);
            }
        }
        return characterProgressList;
    }
}
