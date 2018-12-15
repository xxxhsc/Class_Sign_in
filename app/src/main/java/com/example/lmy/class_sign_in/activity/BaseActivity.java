package com.example.lmy.class_sign_in.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 何盛昌 on 2018/12/15.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManage.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.removeActivity(this);
    }
}
