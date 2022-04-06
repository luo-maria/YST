package com.example.yst.bean;

import android.widget.ImageView;

import cn.bmob.v3.BmobObject;

public class Activities extends BmobObject {

    private String activity_name;
    private String club_name;
    private String activity_leader;
    private String phone;
    private String start_time;
    private String end_time;
    private String place;
    private String activity_info;
    private String club_id;
    private String club_logo;
    private String activity_imgurl;
    private Integer applynum;
    private String act_status;

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getClub_logo() {
        return club_logo;
    }

    public void setClub_logo(String club_logo) {
        this.club_logo = club_logo;
    }

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getActivity_leader() {
        return activity_leader;
    }

    public void setActivity_leader(String activity_leader) {
        this.activity_leader = activity_leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getActivity_info() {
        return activity_info;
    }

    public void setActivity_info(String activity_info) {
        this.activity_info = activity_info;
    }

//    public BmobFile getActivity_image() {
//        return activity_image;
//    }
//
//    public void setActivity_image(BmobFile activity_image) {
//        this.activity_image = activity_image;
//    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getActivity_imgurl() {
        return activity_imgurl;
    }

    public void setActivity_imgurl(String activity_imgurl) {
        this.activity_imgurl = activity_imgurl;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public Integer getApplynum() {
        return applynum;
    }

    public void setApplynum(Integer applynum) {
        this.applynum = applynum;
    }

    public String getAct_status() {
        return act_status;
    }

    public void setAct_status(String act_status) {
        this.act_status = act_status;
    }
}
