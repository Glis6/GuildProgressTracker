package model;

import lombok.Data;

/**
 * Represents the physical location of the character on the servers.
 *
 * @author Glis
 */
@Data
public final class CharacterLocation {
    /**
     * The region the character is in.
     */
    private String region;

    /**
     * The realm the character is on.
     */
    private String realm;
}
