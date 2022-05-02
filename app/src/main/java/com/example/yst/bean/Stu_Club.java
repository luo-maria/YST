package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class Stu_Club extends BmobObject{
    private String club_name;
    private String logo_url;
    private String club_category;
    private String club_rank;
    private String club_campus;
    private String club_intro;
    private String stu_id;
    private String club_id;

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getClub_category() {
        return club_category;
    }

    public void setClub_category(String club_category) {
        this.club_category = club_category;
    }

    public String getClub_rank() {
        return club_rank;
    }

    public void setClub_rank(String club_rank) {
        this.club_rank = club_rank;
    }

    public String getClub_campus() {
        return club_campus;
    }

    public void setClub_campus(String club_campus) {
        this.club_campus = club_campus;
    }

    public String getClub_intro() {
        return club_intro;
    }

    public void setClub_intro(String club_intro) {
        this.club_intro = club_intro;
    }
}
