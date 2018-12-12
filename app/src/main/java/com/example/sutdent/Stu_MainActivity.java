package com.example.sutdent;


/**
 * Created by 何盛昌 on 2018/12/11.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lmy.class_sign_in.R;

import java.util.ArrayList;
import java.util.List;

public class Stu_MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    //定义Fragment
    private Fragment fragment_student1;
    private Fragment fragment_student2;


    //定义FragmentManager
    private FragmentManager fragmentManager;

//    定义组件
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;
    private RadioGroup radioGroup;
    private RadioButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViewPager();


    }

    private  void   initViewPager(){
        fragment_student1= new FragmentHome();
        fragment_student2= new FragmentInfo();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragment_student1);
        fragmentList.add(fragment_student2);


        // 获取Fragmentmanager对象

        fragmentManager =getSupportFragmentManager();
        // 获取fragmentAdapter对象

        fragmentAdapter=new FragmentAdapter(fragmentManager,fragmentList);

        viewPager.setAdapter(fragmentAdapter);
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
            case R.id.main_user: // 我的信息
                viewPager.setCurrentItem(1,false);
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}





