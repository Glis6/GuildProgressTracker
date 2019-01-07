import api.characterprogress.GearProgressField;
import api.characterprogress.WeeklyHighestMythicPlusProgressField;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jobs.CharacterProgressJob;
import lombok.NonNull;
import model.Character;
import model.ICharacterProgress;
import model.MinimalCharacterInformation;
import repository.ICharacterRepository;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Glis
 */
public class StartUp {
    public static void main(String[] args) throws Exception {
        final Collection<ICharacterProgress> characterProgress = new CharacterProgressJob(new ICharacterRepository() {
            @Override
            public boolean save(@NonNull Character object) {
                return false;
            }

            @Override
            public void update(@NonNull Character object) {
            }

            @Override
            public Collection<Character> findAll() {
                return null;
            }

            @Override
            public Character findOne(@NonNull String id) {
                return null;
            }

            @Override
            public String updateAndGetId(@NonNull Character character) {
                System.out.println(character);
                return character.getName();
            }
        }).apply(
                Arrays.asList(
                        /*new MinimalCharacterInformation("eu", "draenor", "glis"),
                        new MinimalCharacterInformation("eu", "draenor", "innbeesix"),
                        new MinimalCharacterInformation("eu", "draenor", "makamash"),
                        new MinimalCharacterInformation("eu", "draenor", "weeiz"),
                        new MinimalCharacterInformation("eu", "draenor", "zizaapriest")*/
                        new MinimalCharacterInformation("eu", "draenor", "bexey")
                ),
                Arrays.asList(
                        new GearProgressField(),
                        new WeeklyHighestMythicPlusProgressField()
                ));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(characterProgress);
        System.out.println(json);
    }
}
