package com.example.yst.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ApplyToClublnfo extends BmobObject {
    private String apply_club_reason;
    private String apply_club_phone;
    private String apply_club_class;
    private String apply_club_sex;
    private String apply_club_name;
    private String student_id;
    private String club_id;
    private String application_status;
    private BmobFile stu_photo;
    public String getApply_club_reason() {
        return apply_club_reason;
    }

    public void setApply_club_reason(String apply_club_reason) {
        this.apply_club_reason = apply_club_reason;
    }

    public String getApply_club_phone() {
        return apply_club_phone;
    }

    public void setApply_club_phone(String apply_club_phone) {
        this.apply_club_phone = apply_club_phone;
    }

    public String getApply_club_class() {
        return apply_club_class;
    }

    public void setApply_club_class(String apply_club_class) {
        this.apply_club_class = apply_club_class;
    }

    public String getApply_club_sex() {
        return apply_club_sex;
    }

    public void setApply_club_sex(String apply_club_sex) {
        this.apply_club_sex = apply_club_sex;
    }

    public String getApply_club_name() {
        return apply_club_name;
    }

    public void setApply_club_name(String apply_club_name) {
        this.apply_club_name = apply_club_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getClub_id() {
        return club_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public String getApplication_status() {
        return application_status;
    }

    public void setApplication_status(String application_status) {
        this.application_status = application_status;
    }

    public BmobFile getStu_photo() {
        return stu_photo;
    }

    public void setStu_photo(BmobFile stu_photo) {
        this.stu_photo = stu_photo;
    }
}
