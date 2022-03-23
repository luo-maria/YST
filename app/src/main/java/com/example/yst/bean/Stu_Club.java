package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class Stu_Club extends BmobObject{
//    String stu_club_id;
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
}
