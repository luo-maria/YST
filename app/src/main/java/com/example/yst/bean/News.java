package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class News extends BmobObject {
    private String club_id;
    private String news_title;
    private String news_content;
    private String news_image;
    private Integer heat;
    private String club_name;
    private Integer likes;
    private String news_kind;

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getNews_kind() {
        return news_kind;
    }

    public void setNews_kind(String news_kind) {
        this.news_kind = news_kind;
    }
}
