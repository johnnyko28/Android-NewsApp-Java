package com.example.newsapp;

public class ListItem {
    private String head;
    private String section;
    private String time;
    private String imageUrl;
    private String articleID;
    private String webUrl;



    public ListItem(String head, String section, String time, String imageUrl, String articleID, String webUrl) {
        this.head = head;
        this.section = section;
        this.time = time;
        this.imageUrl = imageUrl;
        this.articleID = articleID;
        this.webUrl = webUrl;
    }

    public String getHead() { return head; }

    public String getSection() {
        return section;
    }

    public String getTime() {
        return time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArticleID() {
        return articleID;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
