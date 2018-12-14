package com.example.lmy.class_sign_in;

import cn.bmob.v3.BmobObject;

public class Course extends BmobObject {
    private String coursename;
    private String courseno;
    private String teacherid;

    public String getCoursename(){
        return coursename;
    }

    public void setCoursename(String coursename){
        this.coursename = coursename;
    }

    public String getCourseno(){
        return courseno;
    }

    public void setCourseno(String courseno){
        this.courseno = courseno;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }
}
