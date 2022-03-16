package com.example.yst.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Student extends BmobUser {

    private String number;
    private String  sclass;
    private String signature;
    private String gender;
    private  String college;
    private  String university;
    private String nickname;
    private BmobFile photoImage;
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public BmobFile getPhotoImage() {
        return photoImage;
    }

    public void setPhotoImage(BmobFile photoImage) {
        this.photoImage = photoImage;
    }
}
