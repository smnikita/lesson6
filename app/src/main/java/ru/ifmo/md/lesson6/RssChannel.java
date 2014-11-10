package ru.ifmo.md.lesson6;

public class RssChannel {
    private String title;
    private String url;

    public RssChannel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
