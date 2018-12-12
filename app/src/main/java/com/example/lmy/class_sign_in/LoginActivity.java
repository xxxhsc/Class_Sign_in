package com.example.lmy.class_sign_in;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sutdent.Stu_MainActivity;
import com.example.teacher.Tch_MainActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity{

    //布局内的控件
    private EditText et_name;
    private EditText et_password;
    private Button mLoginBtn;
    private CheckBox checkBox_password;
    private CheckBox checkBox_login;
    private TextView register;
    private ImageView iv_see_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this,"a7cedd84e1f6b961d258a9ec42fcaf66");
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        et_name = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox_password = (CheckBox) findViewById(R.id.checkBox_password);
        checkBox_login = (CheckBox) findViewById(R.id.checkBox_login);
        register = (TextView) findViewById(R.id.register);
        iv_see_password = (ImageView) findViewById(R.id.iv_see_password);

        mLoginBtn.setOnClickListener(m_login_Listener);
        checkBox_password.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox_login.setOnCheckedChangeListener(onCheckedChangeListener);
        iv_see_password.setOnClickListener(m_login_Listener);
        register.setOnClickListener(m_login_Listener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initData();

    }

    private void initData(){
        //判断用户第一次登陆
        if (firstLogin()) {
            checkBox_password.setChecked(false);//取消记住密码的复选框
            checkBox_login.setChecked(false);//取消自动登录的复选框
        }
        //判断是否记住密码
        if (remenberPassword()) {
            //判断用户是否为退出登录之后再登录
            if(exitLogin()) {
                checkBox_password.setChecked(false);//取消记住密码的复选框
                checkBox_login.setChecked(false);//取消自动登录的复选框
                et_name.setText("");
                et_password.setText("");
            }else{
                checkBox_password.setChecked(true);//勾选记住密码
                setTextNameAndPassword();//把密码和账号输入到输入框中
            }
        } else {
            setTextName();//把用户账号放到输入账号的输入框中
        }

        //判断是否自动登录
        if (autoLogin()) {
            //判断用户是否为退出登录之后再登录
            if(exitLogin()) {
                checkBox_password.setChecked(false);//取消记住密码的复选框
                checkBox_login.setChecked(false);//取消自动登录的复选框
                et_name.setText("");
                et_password.setText("");
            }else{
                checkBox_login.setChecked(true);
                login();//去登录就可以
            }

        }
    }

    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        et_name.setText("" + getLocalName());
        et_password.setText("" + getLocalPassword());
    }

    /**
     * 设置数据到输入框中
     */
    public void setTextName() {
        et_name.setText("" + getLocalName());
    }


    /**
     * 获得保存在本地的用户名
     */
    public String getLocalName() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String name = helper.getString("name");
        return name;
    }


    /**
     * 获得保存在本地的密码
     */
    public String getLocalPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        return Base64Utils.decryptBASE64(password);   //解码一下
//       return password;   //解码一下

    }

    /**
     * 判断是否自动登录
     */
    private boolean autoLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean autoLogin = helper.getBoolean("autoLogin", false);
        return autoLogin;
    }

    /**
     * 判断是否记住密码
     */
    private boolean remenberPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean remenberPassword = helper.getBoolean("remenberPassword", false);
        return remenberPassword;
    }

    /**
     * 判断是否是第一次登陆
     */
    private boolean firstLogin() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean first = helper.getBoolean("first", true);
        if (first) {
            //创建一个ContentVa对象（自定义的）设置不是第一次登录，,并创建记住密码和自动登录是默认不选，创建账号和密码为空
            helper.putValues(new SharedPreferencesUtils.ContentValue("first", false),
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("name", ""),
                    new SharedPreferencesUtils.ContentValue("password", ""));
            return true;
        }
        return false;
    }
    //判断用户是否为退出登录之后再登录
    private boolean exitLogin(){
        Intent intent = getIntent();
        String exit = intent.getStringExtra("exit");
        boolean exitLogin = false;
        if (exit!=null){
            exitLogin=true;
        }
        return exitLogin;
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView == checkBox_password) {  //记住密码选框发生改变时
                if (!isChecked) {   //如果取消“记住密码”，那么同样取消自动登陆
                    checkBox_login.setChecked(false);
                }
            } else if (buttonView == checkBox_login) {   //自动登陆选框发生改变时
                if (isChecked) {   //如果选择“自动登录”，那么同样选中“记住密码”
                    checkBox_password.setChecked(true);
                }
            }
        }
    };

    View.OnClickListener m_login_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    login();
                    loadUserName();
                    break;
                case R.id.register:
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    finish();//关闭页面
                    break;
                case R.id.iv_see_password:
                    setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                    break;

            }
        }
    };

    //模拟登录情况
    private void login() {
        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误

        if (isMessageValid()){
            //判断账号和密码
            String username = et_name.getText().toString().trim();
            String password = et_password.getText().toString().trim();
            final BmobUser user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser,BmobException e){
                    if (e==null){
                        User userInfo = BmobUser.getCurrentUser(User.class);//获取当前用户数据对象；

                        if(userInfo.getIdentity())
                        {
                            showToast(bmobUser.getUsername()+"登录成功！");
                            loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
                            Log.e("登录成功", bmobUser.getUsername(), e);
                            startActivity(new Intent(LoginActivity.this, Tch_MainActivity.class));
                            finish();//关闭页面
                        }
                            else{
                            showToast(bmobUser.getUsername()+"登录成功！");
                            loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
                            Log.e("登录成功", bmobUser.getUsername(), e);
                            startActivity(new Intent(LoginActivity.this, Stu_MainActivity.class));
                            finish();//关闭页面
                        }
                    } else {
                        showToast("登录失败，用户名或密码不正确！");
                        Log.e("登录失败","原因：",e);
                    }
                }
            });
        }

    }



    /**
     * 保存用户账号
     */
    public void loadUserName() {
        if (!getAccount().equals("") || !getAccount().equals("请输入登录账号")) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            helper.putValues(new SharedPreferencesUtils.ContentValue("name", getAccount()));
        }

    }

    /**
     * 保存用户选择“记住密码”和“自动登陆”的状态
     */
    private void loadCheckBoxState() {
        loadCheckBoxState(checkBox_password, checkBox_login);
    }

    /**
     * 保存按钮的状态值
     */
    public void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login) {

        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");

        //如果设置自动登录
        if (checkBox_login.isChecked()) {
            //创建记住密码和自动登录是都选择,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", true),
                    new SharedPreferencesUtils.ContentValue("password", Base64Utils.encryptBASE64(getPassword())));

        } else if (!checkBox_password.isChecked()) { //如果没有保存密码，那么自动登录也是不选的
            //创建记住密码和自动登录是默认不选,密码为空
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", ""));
        } else if (checkBox_password.isChecked()) {   //如果保存密码，没有自动登录
            //创建记住密码为选中和自动登录是默认不选,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", Base64Utils.encryptBASE64(getPassword())));
        }
    }

    public boolean isMessageValid() {
        if (et_name.getText().toString().trim().equals("")) {
            showToast(getString(R.string.account_empty));
            return false;
        } else if (et_password.getText().toString().trim().equals("")) {
            showToast(getString(R.string.pwd_empty));
            return false;
        }
        return true;
    }

    /**
     * 获取账号
     */
    public String getAccount() {
        return et_name.getText().toString().trim();//去掉空格
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return et_password.getText().toString().trim();//去掉空格
    }

    private void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setSelected(false);
            //密码不可见
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password.setSelected(true);
            //密码可见
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
