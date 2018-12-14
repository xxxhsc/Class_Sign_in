package com.example.sutdent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmy.class_sign_in.Course;
import com.example.lmy.class_sign_in.CourseList;
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Stu_FragmentHome extends Fragment {

    private User Stuinfo;
    private TextView course_info;
    private String stu_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button course_select = getActivity().findViewById(R.id.course_select);
        Button all_select = getActivity().findViewById(R.id.all_select);
        course_info = getActivity().findViewById(R.id.course_info);

        //显示课程信息
        showCourse();

        //选择课程
        course_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.selectcourse_dialog, null);
                final EditText tv_cno = (EditText) view.findViewById(R.id.tv_courseno);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请输入要选择的课程的课号")
                        .setView(view)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String cno = tv_cno.getText().toString();
                                if (cno != null && !cno.isEmpty()){
                                    BmobQuery<Course> querycourse = new BmobQuery<Course>();
                                    querycourse.findObjects(new FindListener<Course>() {
                                        @Override
                                        public void done(List<Course> list, BmobException e) {
                                            if (e == null){
                                                String courseno = null;
                                                for (Course course : list){
                                                    courseno = course.getCourseno();
                                                    if (cno.equals(courseno)){
                                                        CourseList courselist = new CourseList();
                                                        courselist.setStudentid(stu_id);
                                                        courselist.setCourseno(courseno);
                                                        courselist.setTeacherid(course.getTeacherid());
                                                        courselist.save(new SaveListener<String>() {
                                                            @Override
                                                            public void done(String s, BmobException e) {
                                                                if (e == null){
                                                                    Toast.makeText(getActivity(),"选课成功！",Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(getActivity(),"选课失败，请重试！",Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                            }
                                                        });
                                                        return;
                                                    }else {
                                                        continue;
                                                       /* Toast.makeText(getActivity(),"找不到此课号，请重新输入！" + e.getMessage(),Toast.LENGTH_SHORT).show();*/
                                                    }
                                                }

                                            }else {
                                                Toast.makeText(getActivity(),"查询不到课程！",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    Toast.makeText(getActivity(),"输入为空，请重新输入！",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                dialog.dismiss();

                            }
                        }).create().show();
            }
        });

        //查看已选课程
        all_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });
    }

    private void showCourse(){
        //显示课程信息
        final StringBuffer sb = new StringBuffer(256);
        Stuinfo = BmobUser.getCurrentUser(User.class);//获取当前用户用户名
        stu_id = Stuinfo.getUserid();
        BmobQuery<Course> querycourse = new BmobQuery<Course>();
        querycourse.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                if (e == null){
                    for (Course course : list){
                        sb.append("课程名称：" +course.getCoursename() +"\n" + "课号：" + course.getCourseno() + "\n" + "教师工号：" + course.getTeacherid() + "\n");
                    }
                    course_info.setText(sb);
                }else {
                    Toast.makeText(getActivity(),"查询失败！" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showSelect(){
        //显示已选课程信息
        final StringBuffer sb = new StringBuffer(256);
        Stuinfo = BmobUser.getCurrentUser(User.class);//获取当前用户用户名
        stu_id = Stuinfo.getUserid();
        BmobQuery<CourseList> querycourse = new BmobQuery<CourseList>();
        querycourse.addWhereEqualTo("studentid",stu_id);
        querycourse.findObjects(new FindListener<CourseList>() {
            @Override
            public void done(List<CourseList> list, BmobException e) {
                if (e == null){
                    for (CourseList courseList : list){


                    }
                }
            }
        });

        /*querycourse.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                if (e == null){
                    for (Course course : list){
                        sb.append("课程名称：" +course.getCoursename() +"\n" + "课号：" + course.getCourseno() + "\n" + "教师工号：" + course.getTeacherid() + "\n");
                    }
                    course_info.setText(sb);
                }else {
                    Toast.makeText(getActivity(),"查询失败！" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }
}

