package model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Glis
 */
@Data
public final class ProgressBatchJob {
    /**
     * The start time of the batch job.
     */
    private LocalDateTime startTime;

    /**
     * The end time of the batch job.
     */
    private LocalDateTime endTime;

    /**
     * The id's of all characters the system attempted to check.
     */
    private Collection<String> attemptedCharacterIdList = new HashSet<>();

    /**
     * All character progress received from the API.
     */
    private Collection<ICharacterProgress> ICharacterProgresses = new HashSet<>();
}
