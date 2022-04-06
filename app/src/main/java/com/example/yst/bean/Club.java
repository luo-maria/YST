package com.example.yst.bean;

import java.io.Serializable;
import java.util.Date;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Club extends BmobObject implements Serializable {
    private String club_name;
    private String club_category;
    private String club_rank;
    private String club_president;
    private String club_colle;
    private String club_state;
    private String club_member;
    private Integer club_number;
    private String club_campus;
    private String pre_number;
    private String club_intro;
    private String stu_id;
    private String logo_url;
    private String audit_state;


    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getClub_category() {
        return club_category;
    }

    public void setClub_category(String club_category) {
        this.club_category = club_category;
    }

//    public BmobFile getClub_logo() {
//        return club_logo;
//    }
//
//    public void setClub_logo(BmobFile club_logo) {
//        this.club_logo = club_logo;
//    }

    public String getClub_rank() {
        return club_rank;
    }

    public void setClub_rank(String club_rank) {
        this.club_rank = club_rank;
    }

    public String getClub_president() {
        return club_president;
    }

    public void setClub_president(String club_president) {
        this.club_president = club_president;
    }

    public String getClub_colle() {
        return club_colle;
    }

    public void setClub_colle(String club_colle) {
        this.club_colle = club_colle;
    }

    public String getClub_state() {
        return club_state;
    }

    public void setClub_state(String club_state) {
        this.club_state = club_state;
    }

    public String getClub_member() {
        return club_member;
    }

    public void setClub_member(String club_member) {
        this.club_member = club_member;
    }

    public Integer getClub_number() {
        return club_number;
    }

    public void setClub_number(Integer club_number) {
        this.club_number = club_number;
    }

    public String getClub_campus() {
        return club_campus;
    }

    public void setClub_campus(String club_campus) {
        this.club_campus = club_campus;
    }

    public String getPre_number() {
        return pre_number;
    }

    public void setPre_number(String pre_number) {
        this.pre_number = pre_number;
    }

    public String getClub_intro() {
        return club_intro;
    }

    public void setClub_intro(String club_intro) {
        this.club_intro = club_intro;
    }


    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getAudit_state() {
        return audit_state;
    }

    public void setAudit_state(String audit_state) {
        this.audit_state = audit_state;
    }
}

