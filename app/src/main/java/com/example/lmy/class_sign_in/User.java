package com.example.lmy.class_sign_in;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String userid;                       //用户学号
    private String realname;                   //用户姓名
    private Boolean identity;                   //用户身份

    //获取用户学号
    public String getUserid() {                   //获取用户学号
        return userid;
    }
    //设置用户学号
    public void setUserid(String userid) {       //输入用户学号
        this.userid = userid;
    }
    //获取用户姓名
    public String getRealname() {                   //获取用户姓名
        return realname;
    }
    //设置用户姓名
    public void setRealname(String realname) {       //输入用户姓名
        this.realname = realname;
    }
    //获取身份
    public Boolean getIdentity(){
        return identity;
    }
    //设置用户身份
    public void setIdentity(Boolean identity){
        this.identity=identity;
    }


}
