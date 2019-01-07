package model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Glis
 */
@Data
public class Dungeon {
    /**
     * The id of the dungeon.
     */
    private String _id;

    /**
     * The name of the dungeon.
     */
    private String name;

    /**
     * The level of the dungeon.
     */
    private int level;

    /**
     * The time the dungeon was completed.
     */
    private LocalDateTime completionTime;

    /**
     * How long it took to clear the dungeon.
     */
    private long clearTimeInMs;

    /**
     * The amount of upgrades that were received from completing the dungeon.
     */
    private int upgrades;

    /**
     * The score received for completing the dungeon.
     */
    private double score;

    /**
     * The url to raider.io for the run.
     */
    private String url;

    /**
     * A list of affixes present in the dungeon.
     */
    private String[] affixes;
}
