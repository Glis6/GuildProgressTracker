package api.characterprogress;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.NonNull;
import model.Character;
import model.Dungeon;
import model.ICharacterProgress;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Glis
 */
public final class WeeklyHighestMythicPlusProgressField implements CharacterProgressField {
    @Override
    public String getField() {
        return "mythic_plus_weekly_highest_level_runs";
    }

    @Override
    public Character doCharacterChanges(@NonNull Character character, @NonNull JsonObject dataObject) {
        //Do nothing here
        return character;
    }

    @Override
    public ICharacterProgress doCharacterProgressChanges(@NonNull ICharacterProgress characterProgress, @NonNull JsonObject dataObject) {
        if (!dataObject.has(getField())) {
            return characterProgress;
        }
        final JsonArray dungeonsField = dataObject.get(getField()).getAsJsonArray();
        final Collection<Dungeon> dungeons = new HashSet<>();
        dungeonsField.forEach(jsonElement -> {
            final JsonObject dungeonObject = jsonElement.getAsJsonObject();
            final Dungeon dungeon = new Dungeon();
            if(dungeonObject.has("dungeon")) {
                dungeon.setName(dungeonObject.get("dungeon").getAsString());
            }
            if(dungeonObject.has("mythic_level")) {
                dungeon.setLevel(dungeonObject.get("mythic_level").getAsInt());
            }
            if(dungeonObject.has("completed_at")) {
                dungeon.setCompletionTime(ZonedDateTime.parse(dungeonObject.get("completed_at").getAsJsonPrimitive().getAsString()).toLocalDateTime());
            }
            if(dungeonObject.has("clear_time_ms")) {
                dungeon.setClearTimeInMs(dungeonObject.get("clear_time_ms").getAsLong());
            }
            if(dungeonObject.has("num_keystone_upgrades")) {
                dungeon.setUpgrades(dungeonObject.get("num_keystone_upgrades").getAsInt());
            }
            if(dungeonObject.has("score")) {
                dungeon.setScore(dungeonObject.get("score").getAsInt());
            }
            if(dungeonObject.has("url")) {
                dungeon.setUrl(dungeonObject.get("url").getAsString());
            }
            if(dungeonObject.has("affixes")) {
                final JsonArray affixesArray = dungeonObject.getAsJsonArray("affixes");
                final String[] affixes = new String[affixesArray.size()];
                for (int i = 0; i < affixesArray.size(); i++) {
                    final JsonObject affix = affixesArray.get(i).getAsJsonObject();
                    if(affix.has("name")) {
                        affixes[i] = affix.get("name").getAsString();
                    }
                }
                dungeon.setAffixes(affixes);
            }

            dungeons.add(dungeon);
        });
        characterProgress.addField("highestWeeklyDungeons", dungeons);
        return characterProgress;
    }
}
