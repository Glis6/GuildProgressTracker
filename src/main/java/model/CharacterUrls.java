package model;

import lombok.Data;

/**
 * Represents the URL's to the character's online locations.
 *
 * @author Glis
 */
@Data
public final class CharacterUrls {
    /**
     * The URL to the thumbnail of the character.
     */
    private String thumbnailUrl;

    /**
     * The URL to the raider.io profile of the character.
     */
    private String profileUrl;
}
