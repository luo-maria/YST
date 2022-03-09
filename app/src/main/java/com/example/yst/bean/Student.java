package com.example.yst.bean;

import cn.bmob.v3.BmobObject;

public class Student extends BmobObject {
    private int Student_id;
    private String Student__username;            //用户名
    private String Student_number;
    private String Student_password;
    private String Student_class;
    private String Student_signature;
    private String Student_gender;
    private String Student_realname;
    private  String Student_phone;
    private  String Student_college;
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Student(int Student_id,String Student__username, String Student_number, String Student_password,String Student_class,
                String Student_signature,String Student_gender, String Student_realname,String Student_phone,String Student_college ) {
        super();
        this.Student_id=Student_id;
        this.Student__username = Student__username;
        this.Student_number = Student_number;
        this.Student_password = Student_password;
        this.Student_class=Student_class;
        this.Student_signature=Student_signature;
        this.Student_gender=Student_gender;
        this.Student_realname=Student_realname;
        this.Student_phone=Student_phone;
        this.Student_college=Student_college;
    }
    public int getStudent_id() {
        return Student_id;
    }

    public void setStudent_id(int Student_id) {
        this.Student_id = Student_id;
    }

    public String getStudent__username() {
        return Student__username;
    }

    public void setStudent__username(String Student__username) {
        this.Student__username = Student__username;
    }

    public String getStudent_number() {
        return Student_number;
    }

    public void setStudent_number(String Student_number) {
        this.Student_number = Student_number;
    }

    public String getStudent_class() {
        return Student_class;
    }

    public void setStudent_class(String Student_class) {
        this.Student_class = Student_class;
    }

    public String getStudent_signature() {
        return Student_signature;
    }

    public void setStudent_signature(String Student_signature) {
        this.Student_signature = Student_signature;
    }

    public String getStudent_Gender() {
        return Student_gender;
    }

    public void setStudent_Gender(String Student_gender) {
        this.Student_gender = Student_gender;
    }

    public String getStudent_password() {
        return Student_password;
    }

    public void setStudent_password(String Student_password) {
        this.Student_password = Student_password;
    }

    public String getStudent_phone() {
        return Student_phone;
    }

    public void setStudent_phone(String Student_phone) {
        this.Student_phone = Student_phone;
    }

    public String getStudent_realname() {
        return Student_realname;
    }

    public void setStudent_realname(String Student_realname) {
        this.Student_realname = Student_realname;
    }
    public String getStudent_college(){return  Student_college;}
    public void setStudent_college(String Student_college){this.Student_college=Student_college;}
}
