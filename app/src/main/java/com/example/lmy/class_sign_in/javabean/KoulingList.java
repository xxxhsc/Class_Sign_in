package com.example.lmy.class_sign_in.javabean;

import cn.bmob.v3.BmobObject;

public class KoulingList extends BmobObject {
    private String studentid;
    private String kouling;
    private String teacherid;
    private String location;
    private String courseno;

    public String getStudentid(){
        return studentid;
    }

    public void setStudentid(String studentid){
        this.studentid = studentid;
    }

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

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String courseno) {
        this.courseno = courseno;
    }
}
