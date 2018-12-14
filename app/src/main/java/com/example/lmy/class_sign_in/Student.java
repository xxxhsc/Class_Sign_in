package com.example.lmy.class_sign_in;

import cn.bmob.v3.BmobObject;

public class Student extends BmobObject {
    private String username;
    private String password;
    private String teaname;
    private String studentid;                       //用户学号
    private String realname;                   //用户姓名

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    private String getTeaname(){
        return teaname;
    }

    private void setTeaname(String teaname){
        this.teaname = teaname;
    }

    //获取用户学号
    public String getStudentid() {                   //获取用户学号
        return studentid;
    }
    //设置用户学号
    public void setStudentid(String studentid) {       //输入用户学号
        this.studentid = studentid;
    }
    //获取用户姓名
    public String getRealname() {                   //获取用户姓名
        return realname;
    }
    //设置用户姓名
    public void setRealname(String realname) {       //输入用户姓名
        this.realname = realname;
    }
}
