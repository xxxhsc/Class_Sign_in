package com.example.lmy.class_sign_in;

import cn.bmob.v3.BmobObject;

public class Kouling extends BmobObject {
    private String kouling;
    private String teacherid;
    private String courseno;

    public String getKouling(){
        return kouling;
    }

    public void setKouling(String kouling){
        this.kouling = kouling;
    }

    public String getTeacherid(){
        return teacherid;
    }

    public void setTeacherid(String teacherid){
        this.teacherid = teacherid;
    }

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String courseno) {
        this.courseno = courseno;
    }
}
