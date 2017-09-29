package model;

public class Post {
    private String time;
    private String content;
    private int numberOfLikes;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Post() {
        time = "";
        content = "";
        numberOfLikes = 0;
        id = "";
    }

    public Post(String time, String content, int numberOfLikes, String id) {
        this.time = time;
        this.content = content;
        this.numberOfLikes = numberOfLikes;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }
}