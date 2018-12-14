package com.example.lmy.class_sign_in;

import cn.bmob.v3.BmobObject;

public class CourseList extends BmobObject {
    private String courseno;
    private String teacherid;
    private String studentid;

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

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
}
