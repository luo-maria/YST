package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class Stu_activity extends BmobObject {
    private String stu_id;
    private String activity_id;

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }
}
