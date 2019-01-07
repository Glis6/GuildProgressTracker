package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.NonNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.function.Function;

/**
 * @author Glis
 */
public class RaiderIoApi {
    /**
     * The base url to access the Raider IO API.
     */
    private final static Function<String, String> URL_CONSTRUCTOR = url -> "https://raider.io/api/v1/" + url;
    /**
     * The {@link JsonParser}
     */
    private final static JsonParser JSON_PARSER = new JsonParser();

    /**
     * @param uri The URI to attempt to reach.
     * @return The JSON object requested from the uri.
     */
    protected final JsonObject attemptRequest(final @NonNull String uri) throws Exception {
        final Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URL_CONSTRUCTOR.apply(uri));
        return JSON_PARSER.parse(target.request(MediaType.APPLICATION_JSON).get(String.class)).getAsJsonObject();
    }
}
