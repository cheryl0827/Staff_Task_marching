package com.example.emergencyapp.tools;

import android.content.Context;

import android.os.Handler;

import android.os.Looper;



import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;


import org.json.JSONException;

import org.json.JSONObject;


import java.io.IOException;


import okhttp3.Call;

import okhttp3.Callback;

import okhttp3.Response;


public abstract class OkCallback<T> implements Callback {

    private static final String SUCCESS_LOGIN = "200";

    public static final String ERROR_LOGIN = "-11";

    private static final String UN_REGIST = "-12";

    private static final String ERROR_1000 = "1000";

    private static final String CODE = "code";

    private static final String MSG = "message";

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static final Gson GSON = new Gson();


    @Override
//失败的回调
    public void onFailure(Call call, final IOException e) {

        handler.post(new Runnable() {

            @Override

            public void run() {

                onFailure(ERROR_1000, e.getMessage());

            }

        });

    }


    private Context context;


    public void setContext(Context context) {

        this.context = context;

    }


    @Override
//响应事件，成功回调
    public void onResponse(Call call, final Response response) throws IOException {

        try {

            final String resStr = response.body().string();


            final Result<T> result = GSON.fromJson(resStr, new TypeToken<Result<T>>() {
            }.getType());

            if (result.getCode().equals(SUCCESS_LOGIN)) {

                handler.post(new Runnable() {

                    @Override

                    public void run() {

                        onResponse(result);

                    }

                });

                return;

            }


            handler.post(new Runnable() {

                @Override

                public void run() {

                    onFailure(result.getCode(), result.getMessage());

                }

            });

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

//抽象类，可以自己在重新定义想写的内容
    public abstract void onResponse(Result<T> response);


    public abstract void onFailure(String state, String msg);


}
