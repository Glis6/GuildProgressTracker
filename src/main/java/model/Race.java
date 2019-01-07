package model;

import lombok.Data;

/**
 * A race in World of Warcraft.
 *
 * @author Glis
 */
@Data
public final class Race {
    /**
     * The id of the race.
     */
    private String _id;

    /**
     * The faction the race is in.
     */
    private Faction faction;

    /**
     * The name of the race.
     */
    private String name;
}
