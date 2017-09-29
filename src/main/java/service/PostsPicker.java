package service;

import model.Post;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PostsPicker {
    private static final Logger LOG = LogManager.getLogger(PostsPicker.class);

    public List<Post> pickByNumberOfLikes(List<Post> allPosts) {
        ListIterator<Post> postsIterator = allPosts.listIterator();
        List<Post> result = new ArrayList<Post>();
        result.add(postsIterator.next());

        while (postsIterator.hasNext()) {
            Post currentPost = postsIterator.next();

            if (currentPost.getNumberOfLikes() == result.get(0).getNumberOfLikes()) {
                result.add(currentPost);
            } else if (currentPost.getNumberOfLikes() > result.get(0).getNumberOfLikes()) {
                result.clear();

                result.add(currentPost);
            }
        }

        LOG.info("Posts picked by highest number of Likes. Number of posts: " + result.size());

        return result;
    }
}