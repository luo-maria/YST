package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class Stu_Vote extends BmobObject {
    private String stu_id;
    private String vote_id;
    private String choice;

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getVote_id() {
        return vote_id;
    }

    public void setVote_id(String vote_id) {
        this.vote_id = vote_id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
