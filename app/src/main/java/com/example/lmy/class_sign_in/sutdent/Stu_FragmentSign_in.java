package com.example.lmy.class_sign_in.sutdent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.lmy.class_sign_in.javabean.CourseList;
import com.example.lmy.class_sign_in.javabean.Kouling;
import com.example.lmy.class_sign_in.javabean.KoulingList;
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.javabean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class Stu_FragmentSign_in extends Fragment{

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private String get_address;
    private TextView kouling_info;
    private TextView signin_view;
    private User Stuinfo;
    private String stu_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button Sign_in = getActivity().findViewById(R.id.Sign_in);
        Button View_Signin = getActivity().findViewById(R.id.View_Signin);
        kouling_info = getActivity().findViewById(R.id.kouling_info);
        signin_view = getActivity().findViewById(R.id.signin_view);

        if (Build.VERSION.SDK_INT >= 23){
            //动态添加权限
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED
                    && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),"没有权限，请手动开启定位权限！",Toast.LENGTH_SHORT).show();
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
            }else { //有权限直接定位
                //获取定位
                startLocate();
            }
        }else { //低版本直接定位
            //获取定位
            startLocate();
        }

        //显示口令信息
        showKouling();

        //签到
        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.signin_dialog, null);
                final EditText tv_cno = (EditText) view.findViewById(R.id.tv_cno);
                final EditText tv_kouling = (EditText) view.findViewById(R.id.tv_kouling);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("请输入要签到的课程课号和口令")
                        .setView(view)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String cno = tv_cno.getText().toString();
                                final String kling = tv_kouling.getText().toString();
                                if (cno != null && !cno.isEmpty() && kling != null && !kling.isEmpty()){

                                    final BmobQuery<CourseList> querycourse = new BmobQuery<CourseList>();
                                    querycourse.addWhereEqualTo("studentid",stu_id);
                                    querycourse.findObjects(new FindListener<CourseList>() {
                                        @Override
                                        public void done(List<CourseList> list, BmobException e) {
                                            if (e == null){
                                                for (CourseList courseList : list){
                                                    final String courseno = courseList.getCourseno();
                                                    if (cno.equals(courseno)){
                                                        BmobQuery<Kouling> koulingBmobQuery = new BmobQuery<Kouling>();
                                                        koulingBmobQuery.addWhereEqualTo("courseno",courseno);
                                                        koulingBmobQuery.findObjects(new FindListener<Kouling>() {
                                                            @Override
                                                            public void done(List<Kouling> list, BmobException e) {
                                                                if (e == null){
                                                                    String ckouling = null;
                                                                    for (Kouling kouling : list){
                                                                         ckouling= kouling.getKouling();
                                                                        if (kling.equals(ckouling)){
                                                                            KoulingList koulingList = new KoulingList();
                                                                            koulingList.setStudentid(stu_id);
                                                                            koulingList.setCourseno(cno);
                                                                            koulingList.setKouling(kling);
                                                                            koulingList.setLocation(get_address);
                                                                            koulingList.setTeacherid(kouling.getTeacherid());
                                                                            koulingList.save(new SaveListener<String>() {
                                                                                @Override
                                                                                public void done(String s, BmobException e) {
                                                                                    if (e == null){
                                                                                        showSignin();
                                                                                        Toast.makeText(getActivity(),"签到成功！",Toast.LENGTH_SHORT).show();
                                                                                    }else{
                                                                                        Toast.makeText(getActivity(),"签到失败，请重试！",Toast.LENGTH_SHORT).show();
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
                                                                    Toast.makeText(getActivity(),"查询不到口令！",Toast.LENGTH_SHORT).show();
                                                                    return;
                                                                }
                                                            }
                                                        });
                                                    }else {
                                                        continue;
                                                    }
                                                }
                                            }else {
                                                Toast.makeText(getActivity(),"还没有选择课程！",Toast.LENGTH_SHORT).show();
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

        //查看签到信息
        View_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignin();
            }
        });

    }

    private void showKouling(){
        //显示口令信息
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
                        final String cno = courseList.getCourseno();
                        BmobQuery<Kouling> querykouling = new BmobQuery<Kouling>();
                        querykouling.addWhereEqualTo("courseno",cno);
                        querykouling.findObjects(new FindListener<Kouling>() {
                            @Override
                            public void done(List<Kouling> list, BmobException e) {
                                if (e == null){
                                    for (Kouling kouling : list){
                                        String ckouling = kouling.getKouling();
                                        sb.append("课号：" +cno +"\n" + "口令：" + ckouling + "\n");
                                    }
                                    kouling_info.setText(sb);
                                }else {
                                    Toast.makeText(getActivity(),"老师还没有发布签到！",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }

                }else {
                    Toast.makeText(getActivity(),"还没有选择课程！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //签到信息
    private void showSignin(){
        signin_view.setText("");
        final StringBuffer sb2 = new StringBuffer(256);
        BmobQuery<KoulingList> querycourse = new BmobQuery<KoulingList>();
        querycourse.addWhereEqualTo("studentid",stu_id);
        querycourse.findObjects(new FindListener<KoulingList>() {
            @Override
            public void done(List<KoulingList> list, BmobException e) {
                if (e == null){
                    for (KoulingList koulinglist : list){
                        sb2.append("教师工号：" +koulinglist.getTeacherid() +"\n" + "口令：" + koulinglist.getKouling() + "\n"+ "位置：" + koulinglist.getLocation() + "\n");
                    }
                    signin_view.setText(sb2);
                }else {
                    Toast.makeText(getActivity(),"查询失败！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setAddrType("all");
        mLocationClient.setLocOption(option); //开启定位
        mLocationClient.start();
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location != null){
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                String address = location.getAddrStr();
                if (address != null){
                    get_address = address;
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }

            }else {
                Toast.makeText(getActivity(),"无法定位！",Toast.LENGTH_SHORT).show();
                return ;
            }

        }
    }

    //申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            //requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    startLocate();
                }else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getActivity(),"获取位置权限失败，请手动开启！",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

}
