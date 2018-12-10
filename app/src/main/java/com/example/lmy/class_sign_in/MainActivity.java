package com.example.lmy.class_sign_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BmobUser bmobUser = BmobUser.getCurrentUser(Student.class);
        if (bmobUser != null){
            /*startActivity(new Intent(MainActivity.this, MainActivity.class));*/

        }else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e("Newest UserInfo is " , s);
                } else {
                    Log.e("","",e);
                }
            }
        });
    }
}
