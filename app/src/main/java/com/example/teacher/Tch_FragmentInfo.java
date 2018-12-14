package com.example.teacher;
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


public class Tch_FragmentInfo extends Fragment {

    private TextView teacher_info;
    private User Teainfo;
    private String tea_name;
    private String tea_id;
    private String tea_real;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher3, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn_exit = getActivity().findViewById(R.id.btn_exit);
        Button update_info = getActivity().findViewById(R.id.update_info);
        teacher_info = getActivity().findViewById(R.id.teacher_info);

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
                final EditText tv_teaid = (EditText)view.findViewById(R.id.tv_userid);
                final EditText tv_realname = (EditText)view.findViewById(R.id.tv_realname);
                tv_name.setText(tea_name);
                tv_teaid.setText(tea_id);
                tv_realname.setText(tea_real);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请输入要更新的内容")
                        .setView(view)
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String up_name = tv_name.getText().toString();
                                String up_teaid = tv_teaid.getText().toString();
                                String up_real = tv_realname.getText().toString();
                                if (up_name != null && !up_name.isEmpty() && up_teaid != null && !up_teaid.isEmpty() && up_real != null && !up_real.isEmpty()){
                                    Teainfo.setValue("username",up_name);
                                    Teainfo.setValue("userid",up_teaid);
                                    Teainfo.setValue("realname",up_real);
                                    Teainfo.update(new UpdateListener() {
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
        Teainfo = BmobUser.getCurrentUser(User.class);//获取当前用户用户名
        tea_name = Teainfo.getUsername();
        tea_id = Teainfo.getUserid();
        tea_real = Teainfo.getRealname();
        teacher_info.setText("用户名：" + tea_name + "\n"
                + "工号：" + tea_id + "\n"
                + "姓名：" + tea_real + "\n");
    }
}
