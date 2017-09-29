import model.Post;

import java.util.List;
import java.util.ListIterator;

import service.PostsGetterFromTwitter;
import service.PostsPicker;

public class Main {
    private static PostsGetterFromTwitter postsGetterFromTwitter = new PostsGetterFromTwitter();
    private static PostsPicker postsPicker = new PostsPicker();
    private static String userName = "dima_balashov";

    private static void displayResult(List<Post> posts) {
        ListIterator<Post> postsIterator = posts.listIterator();

        while (postsIterator.hasNext()) {
            Post post = postsIterator.next();

            System.out.print("\nMost liked tweet:\n-----------------\n" + post.getContent() +
                    "\n-----------------\n" + "Date: " + post.getTime() + "\n" +
                    "Number of likes: " + post.getNumberOfLikes() + "\n");
        }
    }

    public static void main(String[] args) {
        List<Post> allPosts = postsGetterFromTwitter.getPostsOfUser(userName);
        List<Post> bestPosts = postsPicker.pickByNumberOfLikes(allPosts);

        displayResult(bestPosts);
    }
}