package service;

import com.google.gson.*;
import model.Post;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {
    private JsonParser parser = new JsonParser();
    private static final Logger LOG = LogManager.getLogger(JsonConverter.class);

    public List<Post> convertJsonToPostsList(HttpResponse response) throws IOException {
        List<Post> result = new ArrayList<Post>();

        String input = EntityUtils.toString(response.getEntity());
        JsonArray tweetsArray = parser.parse(input).getAsJsonArray();

        for (JsonElement tweetJson : tweetsArray) {
            JsonObject jsonObject = tweetJson.getAsJsonObject();
            String tweetTime = jsonObject.get("created_at").getAsString();
            String tweetText = jsonObject.get("text").toString();
            int tweetLikes = jsonObject.get("favorite_count").getAsInt();
            String id = jsonObject.get("id").getAsString();

            result.add(new Post(tweetTime, tweetText.substring(1, tweetText.length() - 1),
                    tweetLikes, id));
        }

        return result;
    }

    public String getValueFromResponseByName(HttpResponse response, String name) throws IOException {
        String input = EntityUtils.toString(response.getEntity());

        JsonObject responseJson = parser.parse(input).getAsJsonObject();
        JsonPrimitive value = responseJson.getAsJsonPrimitive(name);

        String valueString = value.toString();

        return valueString.substring(1, valueString.length() - 1);
    }
}