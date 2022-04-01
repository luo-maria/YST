package com.example.yst.bean;

import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.BmobObject;

public class CalenderEvent extends BmobObject {
    private String message;
    private Calendar mCalendar;
    private String stud_id;
    private String messtime;
    private String massdate;
    private String time;
    public CalenderEvent() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }


    public String getMesstime() {
        return messtime;
    }

    public void setMesstime(String messtime) {
        this.messtime = messtime;
    }

    public String getMassdate() {
        return massdate;
    }

    public void setMassdate(String massdate) {
        this.massdate = massdate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
