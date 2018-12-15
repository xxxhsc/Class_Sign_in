package com.example.lmy.class_sign_in.teacher;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lmy.class_sign_in.javabean.Course;
import com.example.lmy.class_sign_in.R;
import com.example.lmy.class_sign_in.javabean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class Tch_FragmentHome extends Fragment {

    //定义组件
    private User Teainfo;
    private String tea_id;
    private TextView course_info;
    private ContactInfo contactInfo;

    //类成员
    private Tch_RecycleViewAdapter adapter;
    List<ContactInfo> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher0, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button course_add = getActivity().findViewById(R.id.course_add);
        course_info = getActivity().findViewById(R.id.course_info);



//      初始化mList
        initInfo();
        initView();

      //  course_add.setOnClickListener(ButtonListener);
        //显示课程信息
//        showCourse();



    }
    private void initView(){

        //获取RecyclerView的引用，并对其进行设置
        RecyclerView mRecyclerView = getActivity().findViewById(R.id.card_list);
        mRecyclerView.setHasFixedSize(true);

        //RecyclerView 需要一个layoutManager，也就是布局管理器
//布局管理器能确定RecyclerView内各个子视图（项目视图）的位置
//并能决定何时重新使用对用户已不可见的项目视图
//安卓为我们预先准备好了三种视图管理器：LinearLayoutManager、
//GridLayoutManager、StaggeredGridLayoutManager（详见文档）

//这里我们选择创建一个LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//为RecyclerView对象指定我们创建得到的layoutManager
        mRecyclerView.setLayoutManager(layoutManager);

//实例化MyAdapter并传入mList对象
        adapter = new Tch_RecycleViewAdapter(mList);
        adapter.setOnItemClickLitener(new Tch_RecycleViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"长按了"+position,Toast.LENGTH_SHORT).show();
            }
        });




//为RecyclerView对象mRecyclerView设置adapter
        mRecyclerView.setAdapter(adapter);



    }
    private void initInfo() {
        //        测试数据


        contactInfo=new ContactInfo("UML实验","1600300414","谢武");
        mList.add(contactInfo);

        ContactInfo element1 = new ContactInfo("计算机网络实验","10086","蛇建章");
        ContactInfo element2 = new ContactInfo("计算机网络实验","10086","蛇建章");
        ContactInfo element3 = new ContactInfo("计算机网络实验","10086","蛇建章");
        ContactInfo element4 = new ContactInfo("计算机网络实验","10086","蛇建章");





        mList.add(element1);
        mList.add(element2);
        mList.add(element3);
        mList.add(element4);


    }


    //添加课程
        View.OnClickListener ButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.course_add:
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
                    break;

        }
        }
    };




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

