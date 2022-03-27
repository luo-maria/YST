package com.example.yst.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Activity extends BmobObject {

    private String activity_name;
    private String activity_club_name;
    private String activity_leader;
    private String activity_leader_phone;
    private String activity_info;
    private BmobFile activity_image;

    public Activity () {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public String getActivity_club_name() {
        return activity_club_name;
    }

    public void setActivity_club_name(String activity_club_name) {
        this.activity_club_name = activity_club_name;
    }

    public String getActivity_leader() {
        return activity_leader;
    }

    public void setActivity_leader(String activity_leader) {
        this.activity_leader = activity_leader;
    }

    public String getActivity_leader_phone() {
        return activity_leader_phone;
    }

    public void setActivity_leader_phone(String activity_leader_phone) {
        this.activity_leader_phone = activity_leader_phone;
    }

    public String getActivity_info() {
        return activity_info;
    }

    public void setActivity_info(String activity_info) {
        this.activity_info = activity_info;
    }

    public BmobFile getActivity_image() {
        return activity_image;
    }

    public void setActivity_image(BmobFile activity_image) {
        this.activity_image = activity_image;
    }
}
