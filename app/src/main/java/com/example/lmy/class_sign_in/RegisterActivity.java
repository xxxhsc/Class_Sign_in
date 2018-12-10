package com.example.lmy.class_sign_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private EditText mStuId;                          //学号编辑
    private EditText mRealN;                          //姓名编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮
    private ImageView iv_see_password;              //密码隐藏或显示
    private ImageView iv_see_password2;             //密码隐藏或显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
        mStuId = (EditText) findViewById(R.id.resetpwd_edit_studentId);
        mRealN = (EditText)findViewById(R.id.resetpwd_edit_realName);
        iv_see_password = (ImageView) findViewById(R.id.iv_see_password);
        iv_see_password2 = (ImageView) findViewById(R.id.iv_see_password2);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);

        mSureButton.setOnClickListener(m_register_Listener);      //注册界面按钮的监听事件
        mCancelButton.setOnClickListener(m_register_Listener);
        iv_see_password.setOnClickListener(m_register_Listener);
        iv_see_password2.setOnClickListener(m_register_Listener);

    }

    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure:                       //确认按钮的监听事件
                    signUp();
                    break;
                case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();//关闭页面
                    break;
                case R.id.iv_see_password:
                    setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                    break;
                case R.id.iv_see_password2:
                    setPasswordVisibility2();    //改变图片并设置输入框的文本可见或不可见
                    break;
            }
        }
    };

    public void signUp(){
        if (isMessageValid()){
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();
            String userStuId = mStuId.getText().toString().trim();
            String userRealN = mRealN.getText().toString().trim();

            Student student = new Student();
            student.setUsername(userName);
            student.setPassword(userPwd);
            student.setRealname(userRealN);
            student.setStudentid(userStuId);
            student.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e){
                    if (e==null){
                        showToast("注册成功！");
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();//关闭页面
                    }else {
                        showToast("注册失败！");
                        Log.e("注册失败","原因：",e);
                    }

                }
            });


        }
    }

    public boolean isMessageValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            showToast(getString(R.string.account_empty));
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            showToast(getString(R.string.pwd_empty));
            return false;
        }else if(mPwdCheck.getText().toString().trim().equals("")) {
            showToast(getString(R.string.pwd_check_empty));
            return false;
        }else if(mStuId.getText().toString().trim().equals("")){
            showToast(getString(R.string.stuid_check_empty));
            return false;
        }else if(mRealN.getText().toString().trim().equals("")){
            showToast(getString(R.string.realname_check_empty));
            return false;
        }else if (!(mPwd.getText().toString().trim().equals(mPwdCheck.getText().toString().trim()))){
            showToast(getString(R.string.pwd_not_the_same));
            return false;
        }
        return true;
    }

    /**
     * 设置密码可见和不可见的相互转换
     */
    private void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setSelected(false);
            //密码不可见
            mPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password.setSelected(true);
            //密码可见
            mPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    private void setPasswordVisibility2(){
        if (iv_see_password2.isSelected()) {
            iv_see_password2.setSelected(false);
            //密码不可见
            mPwdCheck.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password2.setSelected(true);
            //密码可见
            mPwdCheck.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
