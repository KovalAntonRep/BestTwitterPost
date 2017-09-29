package model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.JsonConverter;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.util.List;

public class TwitterClient {
    private HttpClient httpClient;
    private JsonConverter jsonConverter = new JsonConverter();
    private String bearerToken;
    private static final Logger LOG = LogManager.getLogger(TwitterClient.class);

    public TwitterClient(String bearerTokenCredentials) {
        try {
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
        } catch (Exception e) {
            LOG.error("Error occurred creating http client");
            throw new RuntimeErrorException(null);
        }

        try {
            bearerToken = getBearerToken(bearerTokenCredentials);
        } catch (Exception e) {
            LOG.error("Error occurred getting bearer token from Twitter API");
            throw new RuntimeErrorException(null);
        }
    }

    private String getBearerToken(String bearerTokenCredentials) throws IOException {
        HttpPost httpPost = new HttpPost("https://api.twitter.com/oauth2/token");
        HttpEntity entity = new StringEntity("grant_type=client_credentials",
                ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
        httpPost.setEntity(entity);
        httpPost.addHeader("Authorization", "Basic " + bearerTokenCredentials);

        HttpResponse response = null;
        try {
            LOG.info("Post request sent");
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            LOG.error("Error occurred executing authorization post request");
        }

        return jsonConverter.getValueFromResponseByName(response, "access_token");
    }

    public List<Post> getPosts(String name, String id) throws IOException {
        String pageUrl = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" +
                name + "&count=200&trim_user=1";

        HttpGet httpGet = new HttpGet(pageUrl + "&max_id=" + id);
        httpGet.addHeader("Authorization", "Bearer " + bearerToken);
        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");

        HttpResponse response = httpClient.execute(httpGet);
        LOG.info("Get request handled, requestUri = " + httpGet.getURI());

        return jsonConverter.convertJsonToPostsList(response);
    }
}