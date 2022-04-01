package com.example.yst.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Vote extends BmobObject {
    private String headline;
    private String introduction;
    private String option1;
    private String option2;
    private String option3;
    private String club_id;
    private String end_time;
    private String result;
    private String vote_number;
    private Integer option1_num;
    private Integer option2_num;
    private Integer option3_num;
    private Date end;
//    BmobDate



    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getVote_number() {
        return vote_number;
    }

    public void setVote_number(String vote_number) {
        this.vote_number = vote_number;
    }

    public Integer getOption1_num() {
        return option1_num;
    }

    public void setOption1_num(Integer option1_num) {
        this.option1_num = option1_num;
    }

    public Integer getOption2_num() {
        return option2_num;
    }

    public void setOption2_num(Integer option2_num) {
        this.option2_num = option2_num;
    }

    public Integer getOption3_num() {
        return option3_num;
    }

    public void setOption3_num(Integer option3_num) {
        this.option3_num = option3_num;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
