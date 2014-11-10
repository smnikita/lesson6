package ru.ifmo.md.lesson6;

public class RssItem {
    private String title;
    private String url;
    private String description;
    private String date;
    private String channelTitle;

    public RssItem(String title, String url, String description, String date, String channelTitle) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.date = date;
        this.channelTitle = channelTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getChannelTitle() {
        return channelTitle;
    }
}
