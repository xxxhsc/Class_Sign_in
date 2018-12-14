package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmy.class_sign_in.Course;
import com.example.lmy.class_sign_in.CourseList;
import com.example.lmy.class_sign_in.Kouling;
import com.example.lmy.class_sign_in.KoulingList;
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class Tch_FragmentSign_in extends Fragment{

    private User Teainfo;
    private String tea_id;
    private TextView kouling_info;
    private TextView kouling_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        kouling_info = getActivity().findViewById(R.id.kouling_info);
        kouling_view = getActivity().findViewById(R.id.kouling_view);
        Button Send_kouling = getActivity().findViewById(R.id.Send_kouling);
        Button View_kouling = getActivity().findViewById(R.id.View_kouling);

        //显示口令信息
        showKouling();

        //发布签到
        Send_kouling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.sendkouling_dialog, null);
                final EditText tv_cno = (EditText) view.findViewById(R.id.tv_cno);
                final EditText tv_kouling = (EditText) view.findViewById(R.id.tv_kouling);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请输入要发布签到的课程课号和口令")
                        .setView(view)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String cno = tv_cno.getText().toString();
                                final String kling = tv_kouling.getText().toString();
                                if (cno != null && !cno.isEmpty() && kling != null && !kling.isEmpty()){

                                    BmobQuery<Course> querycourse = new BmobQuery<Course>();
                                    querycourse.addWhereEqualTo("teacherid",tea_id);
                                    querycourse.findObjects(new FindListener<Course>() {
                                        @Override
                                        public void done(List<Course> list, BmobException e) {
                                            if (e == null){
                                                for (Course course : list){
                                                    String courseno = course.getCourseno();
                                                    if (cno.equals(courseno)){
                                                        Kouling kouling = new Kouling();
                                                        kouling.setCourseno(cno);
                                                        kouling.setKouling(kling);
                                                        kouling.setTeacherid(tea_id);
                                                        kouling.save(new SaveListener<String>() {
                                                            @Override
                                                            public void done(String s, BmobException e) {
                                                                if (e == null){
                                                                    showKouling();
                                                                    Toast.makeText(getActivity(),"口令发布成功，通知学生们签到吧！",Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(getActivity(),"口令发布失败，请重试！",Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                            }
                                                        });
                                                        return;
                                                    }else {
                                                        continue;
                                                    }
                                                }

                                            }else {
                                                Toast.makeText(getActivity(),"查询失败！",Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                }else if (cno.equals("") || kling.equals("")){
                                    Toast.makeText(getActivity(),"输入为空，请重新输入！",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                dialog.dismiss();
                            }
                        }).create().show();

            }
        });

        //查看签到
        View_kouling.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showView();

            }
        });

    }

    //签到信息
    private void showView(){

        kouling_view.setText("");
        final StringBuffer sb2 = new StringBuffer(256);
        BmobQuery<KoulingList> querycourse = new BmobQuery<KoulingList>();
        querycourse.addWhereEqualTo("teacherid",tea_id);
        querycourse.findObjects(new FindListener<KoulingList>() {
            @Override
            public void done(List<KoulingList> list, BmobException e) {
                if (e == null){
                    for (KoulingList koulinglist : list){
                        sb2.append("学号：" +koulinglist.getStudentid() +"\n" + "口令：" + koulinglist.getKouling() + "\n"+ "位置：" + koulinglist.getLocation() + "\n");
                    }
                    kouling_view.setText(sb2);
                }else {
                    Toast.makeText(getActivity(),"查询失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showKouling(){

        kouling_info.setText("");
        //显示口令信息
        final StringBuffer sb = new StringBuffer(256);
        Teainfo = BmobUser.getCurrentUser(User.class);//获取当前用户用户名
        tea_id = Teainfo.getUserid();
        BmobQuery<Kouling> querycourse = new BmobQuery<Kouling>();
        querycourse.addWhereEqualTo("teacherid",tea_id);
        querycourse.findObjects(new FindListener<Kouling>() {
            @Override
            public void done(List<Kouling> list, BmobException e) {
                if (e == null){
                    for (Kouling kouling : list){
                        sb.append("课号：" +kouling.getCourseno() +"\n" + "口令：" + kouling.getKouling() + "\n");
                    }
                    kouling_info.setText(sb);
                }else {
                    Toast.makeText(getActivity(),"查询失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
