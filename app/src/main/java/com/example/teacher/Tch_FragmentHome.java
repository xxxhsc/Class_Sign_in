package com.example.teacher;
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
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class Tch_FragmentHome extends Fragment {

    private User Teainfo;
    private String tea_id;
    private TextView course_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button course_add = getActivity().findViewById(R.id.course_add);
        course_info = getActivity().findViewById(R.id.course_info);

        //显示课程信息
        showCourse();

        //添加课程
        course_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.addcourse_dialog, null);
                final EditText tv_cname = (EditText) view.findViewById(R.id.tv_cname);
                final EditText tv_con = (EditText) view.findViewById(R.id.tv_cno);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请输入要添加的课程信息")
                        .setView(view)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cname = tv_cname.getText().toString();
                                String cno = tv_con.getText().toString();
                                if (cname != null && !cname.isEmpty() && cno != null && !cno.isEmpty()){
                                    Course course = new Course();
                                    course.setCoursename(cname);
                                    course.setCourseno(cno);
                                    course.setTeacherid(tea_id);
                                    course.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null){
                                                showCourse();
                                                Toast.makeText(getActivity(),"课程添加成功！",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getActivity(),"课程添加失败，请重试！",Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    });
                                }else if (cname.equals("") || cno.equals("")){
                                    Toast.makeText(getActivity(),"输入为空，请重新输入！",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                dialog.dismiss();
                            }
                        }).create().show();

            }
        });

    }


    private void showCourse(){
        //显示课程信息
        final StringBuffer sb = new StringBuffer(256);
        Teainfo = BmobUser.getCurrentUser(User.class);//获取当前用户用户名
        tea_id = Teainfo.getUserid();
        BmobQuery<Course> querycourse = new BmobQuery<Course>();
        querycourse.addWhereEqualTo("teacherid",tea_id);
        querycourse.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                if (e == null){
                    for (Course course : list){
                        sb.append("课程名称：" +course.getCoursename() +"\n" + "课号：" + course.getCourseno() + "\n");
                    }
                    course_info.setText(sb);
                }else {
                    Toast.makeText(getActivity(),"查询失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

