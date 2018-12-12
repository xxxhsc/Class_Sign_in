package com.example.sutdent;


/**
 * Created by 何盛昌 on 2018/12/11.
 */


import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lmy.class_sign_in.R;

import java.util.ArrayList;
import java.util.List;

public class Stu_MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    //定义Fragment
    private Fragment fragment_student1;
    private Fragment fragment_student2;
    private Fragment fragment_student3;


    //定义FragmentManager
    private FragmentManager fragmentManager;

//    定义组件
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private Stu_FragmentAdapter stuFragmentAdapter;
    private RadioGroup radioGroup;
    private RadioButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        init();
        initViewPager();


    }

    private  void   initViewPager(){
        fragment_student1= new Stu_FragmentHome();
        fragment_student2= new Stu_FragmentSign_in();
        fragment_student3= new Stu_FragmentInfo();


        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment_student1);
        fragmentList.add(fragment_student2);
        fragmentList.add(fragment_student3);


        // 获取Fragmentmanager对象

        fragmentManager =getSupportFragmentManager();
        // 获取fragmentAdapter对象

        stuFragmentAdapter =new Stu_FragmentAdapter(fragmentManager,fragmentList);

        viewPager.setAdapter(stuFragmentAdapter);
        //设置ViewPager默认显示第一个View
        viewPager.setCurrentItem(0);
        //设置第一个RadioButton为默认选中状态
        home.setChecked(true);
        //ViewPager页面切换监听
        viewPager.addOnPageChangeListener(this);



    }

    private void init() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGrop);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        home = (RadioButton) findViewById(R.id.main_home);
        //RadioGroup状态改变监听
        radioGroup.setOnCheckedChangeListener(this);
    }




    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
    switch (i){
        case 0:
            radioGroup.check(R.id.main_home);
            break;
        case 1:
            radioGroup.check(R.id.main_sign_in);
            break;
        case 2:
            radioGroup.check(R.id.main_user);
            break;
    }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.main_home: // 首页
                //显示第一个Fragment并关闭动画效果
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.main_sign_in: // 签到碎片
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.main_user: // 我的信息
                viewPager.setCurrentItem(2,false);
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}





