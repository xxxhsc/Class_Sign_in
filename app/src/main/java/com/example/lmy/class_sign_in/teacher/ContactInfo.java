package com.example.lmy.class_sign_in.teacher;

/**
 * Created by 何盛昌 on 2018/12/15.
 */

public class ContactInfo {
    protected String Cname = "计算机网络实验";
    protected String Cno = "10086";
    protected String Tchname = "蛇建章";
    protected static final String CNO_ = "课号：";
    protected static final String TCHNAME_ = "老师：";

    public ContactInfo(String cname, String cno, String teachername){
        this.Cname = cname;
        this.Cno = cno;
        this.Tchname = teachername;
    }

}
