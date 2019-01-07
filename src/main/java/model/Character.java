package model;

import lombok.Data;

/**
 * @author Glis
 */
@Data
public final class Character {
    /**
     * The id of the character.
     */
    private String _id;

    /**
     * The name of the character.
     */
    private String name;

    /**
     * The race of the character.
     */
    private String raceId;

    /**
     * The class of the character.
     */
    private String classId;

    /**
     * The role of the character.
     */
    private String role;

    /**
     * The gender of the character.
     */
    private String gender;

    /**
     * The rank the player has in the guild.
     */
    private int guildRank;

    /**
     * The {@link CharacterLocation}.
     */
    private CharacterLocation characterLocation = new CharacterLocation();

    /**
     * The {@link CharacterUrls}.
     */
    private CharacterUrls characterUrls = new CharacterUrls();
}
