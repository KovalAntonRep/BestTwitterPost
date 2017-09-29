package service;

import model.Post;
import model.TwitterClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostsGetterFromTwitter {
    private static final String BEARER_TOKEN_CREDENTIALS = "GVTYnF5cWQyaDB6TktmTU1aUkE0NFNvTjpnallrSDIyVFlhdUxhZEd" +
            "XM21sWW81M1ZOTUVsYWFxT1hRUlJuNUZHN2NxeXVOZkFXRw==";

    private TwitterClient twitterClient = new TwitterClient(BEARER_TOKEN_CREDENTIALS);
    private static final Logger LOG = LogManager.getLogger(PostsGetterFromTwitter.class);

    public PostsGetterFromTwitter() {
    }

    public List<Post> getPostsOfUser(String userName) {
        List<Post> resultPosts = new ArrayList<Post>();

        String maxId = "999999999999999999";

        try {
            boolean firstIteration = true;

            do {
                List<Post> posts = twitterClient.getPosts(userName, maxId);

                LOG.info("Number of received posts from twitter server: " + posts.size());

                if (firstIteration) {
                    firstIteration = false;
                } else {
                    posts.remove(0);

                    LOG.info("Repeated post is deleted");
                }

                if (posts.size() == 0) break;

                maxId = posts.get(posts.size() - 1).getId();
                resultPosts.addAll(posts);
            } while (true);
        } catch (Exception e) {
            LOG.error("Error in getting all posts of user", e);
        }

        LOG.info("All available posts of twitter user are received. Final number of tweets: " + resultPosts.size());

        return resultPosts;
    }
}