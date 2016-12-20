package com.sender.news;

/**
 * Created by XieShengda on 2016/12/11.
 */

public class NewsBean {

    private String title;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;
    private String url;

    public NewsBean(String title, String date, String author_name, String thumbnail_pic_s, String url) {
        this.title = title;
        this.date = date;
        this.author_name = author_name;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public String getUrl() {
        return url;
    }

}