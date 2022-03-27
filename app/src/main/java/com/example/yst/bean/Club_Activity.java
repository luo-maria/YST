package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class Club_Activity extends BmobObject{
    //    String stu_club_id;
    private String Club_id;
    private String Activity_id;

    public String getClub_id() {
        return Club_id;
    }

    public void setClub_id(String club_id) {
        Club_id = club_id;
    }

    public String getActivity_id() {
        return Activity_id;
    }

    public void setActivity_id(String activity_id) {
        Activity_id = activity_id;
    }
}
