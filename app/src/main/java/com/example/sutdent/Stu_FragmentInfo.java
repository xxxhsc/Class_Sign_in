package com.example.sutdent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lmy.class_sign_in.LoginActivity;
import com.example.lmy.class_sign_in.MainActivity;
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by 何盛昌 on 2018/12/11.
 */


public class Stu_FragmentInfo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student3, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn_exit = getActivity().findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();  //清楚缓存对象
                BmobUser currentUser = BmobUser.getCurrentUser(User.class);   //现在的currentUser是null
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.putExtra("exit","true");
                startActivity(intent);      //跳转登录界面
            }
        });
        }
}
