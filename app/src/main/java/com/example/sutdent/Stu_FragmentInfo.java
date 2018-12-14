package com.example.sutdent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmy.class_sign_in.LoginActivity;
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class Stu_FragmentInfo extends Fragment {

    private TextView student_info;
    private String stu_name;
    private String stu_id;
    private String stu_real;
    private User Stuinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student3, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn_exit = getActivity().findViewById(R.id.btn_exit);
        Button update_info = getActivity().findViewById(R.id.update_info);
        student_info = getActivity().findViewById(R.id.student_info);

        //显示个人信息
        showInfo();


        //退出登录
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();  //清楚缓存对象
                BmobUser currentUser = BmobUser.getCurrentUser(User.class);   //现在的currentUser是null
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.putExtra("exit","true");
                Toast.makeText(getActivity(),"退出登录成功！",Toast.LENGTH_SHORT).show();
                startActivity(intent);      //跳转登录界面
            }
        });

        //修改个人信息
        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.update_dialog,null);
                final EditText tv_name = (EditText)view.findViewById(R.id.tv_name);
                final EditText tv_stuid = (EditText)view.findViewById(R.id.tv_userid);
                final EditText tv_realname = (EditText)view.findViewById(R.id.tv_realname);
                tv_name.setText(stu_name);
                tv_stuid.setText(stu_id);
                tv_realname.setText(stu_real);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请输入要更新的内容")
                        .setView(view)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String up_name = tv_name.getText().toString();
                                String up_stuid = tv_stuid.getText().toString();
                                String up_real = tv_realname.getText().toString();
                                if (up_name != null && !up_name.isEmpty() && up_stuid != null && !up_stuid.isEmpty() && up_real != null && !up_real.isEmpty()){
                                    Stuinfo.setValue("username",up_name);
                                    Stuinfo.setValue("userid",up_stuid);
                                    Stuinfo.setValue("realname",up_real);
                                    Stuinfo.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                showInfo();
                                                Toast.makeText(getActivity(),"个人信息修改成功！",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getActivity(),"个人信息修改失败！",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    Toast.makeText(getActivity(),"输入为空！",Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }

    private void showInfo(){
        //显示个人信息
        Stuinfo = BmobUser.getCurrentUser(User.class);//获取当前用户用户名
        stu_name = Stuinfo.getUsername();
        stu_id = Stuinfo.getUserid();
        stu_real = Stuinfo.getRealname();
        student_info.setText("用户名：" + stu_name + "\n"
                + "学号：" + stu_id + "\n"
                + "姓名：" + stu_real + "\n");
    }
}
