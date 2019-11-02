package com.example.emergencyapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.emergencyapp.tools.Constant;
import com.example.emergencyapp.tools.OkCallback;
import com.example.emergencyapp.tools.OkHttp;
import com.example.emergencyapp.tools.Result;
import com.example.emergencyapp.tools.SMSTextView;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends Activity implements View.OnClickListener {
    EditText phone, password, indentifyCode;
    Button passwordRegister, indentifyCodeRegister, register;
    LinearLayout passwordLinear, identifyCodeLinear;
    SMSTextView indentifyCodeGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone = (EditText) findViewById(R.id.phoneText);
        password = (EditText) findViewById(R.id.passwordText);
        indentifyCode = (EditText) findViewById(R.id.identityCodeText);

        passwordRegister = (Button) findViewById(R.id.passwordRegister);
        indentifyCodeRegister = (Button) findViewById(R.id.idetifyCodeRegister);
        register = (Button) findViewById(R.id.register);
        passwordRegister.setOnClickListener(this);
        indentifyCodeRegister.setOnClickListener(this);
        register.setOnClickListener(this);

        indentifyCodeGet = (SMSTextView) findViewById(R.id.indentifyCodeGet);
        indentifyCodeGet.setOnClickListener(this);


        passwordLinear = (LinearLayout) findViewById(R.id.passwordLinear);
        identifyCodeLinear = (LinearLayout) findViewById(R.id.identifyCodeLinear);
        identifyCodeLinear.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passwordRegister:
                passwordLinear.setVisibility(View.VISIBLE);
                identifyCodeLinear.setVisibility(View.GONE);
                break;
            case R.id.idetifyCodeRegister:
                identifyCodeLinear.setVisibility(View.VISIBLE);
                passwordLinear.setVisibility(View.GONE);
                break;
            case R.id.indentifyCodeGet:
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone.getText().toString());
                OkHttp.post(RegisterActivity.this, Constant.get_code, map, new OkCallback() {
                    @Override
                    public void onResponse(Result response) {
                        indentifyCodeGet.start();//开启验证码倒计时
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification.Builder builder1 = new Notification.Builder(RegisterActivity.this);
                        builder1.setSmallIcon(R.drawable.ic_preview_radio_on); //设置图标
                        builder1.setTicker("收到验证码");
                        builder1.setContentTitle("通知"); //设置标题
                        builder1.setContentText(response.getData() + ""); //消息内容
                        builder1.setWhen(System.currentTimeMillis()); //发送时间
                        builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
                        builder1.setAutoCancel(true);//打开程序后图标消失
                        Notification notification1 = builder1.build();
                        notificationManager.notify(124, notification1); // 通过通知管理器发送通知
                    }

                    @Override
                    public void onFailure(String state, String msg) {
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        }
    }
}
