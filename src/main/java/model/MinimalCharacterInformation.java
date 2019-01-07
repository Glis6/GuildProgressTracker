package model;

import lombok.Data;

/**
 * The minimal amount of required information to find a character.
 *
 * @author Glis
 */
@Data
public final class MinimalCharacterInformation {
    /**
     * The region the character is in.
     */
    private final String region;

    /**
     * The realm the character is in.
     */
    private final String realm;

    /**
     * The name of the character.
     */
    private final String name;
}