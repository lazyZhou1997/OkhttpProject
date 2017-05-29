package com.example.asus.okhttpproject;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleOkhttpActivity extends Activity implements View.OnClickListener{

    private Button mLoginView;
    private Button mCookieView;
    private TextView mCookieTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_cookie_view:
                Toast.makeText(this, "getRequest", Toast.LENGTH_SHORT).show();
                getRequest();
                break;
            case R.id.login_view:
                Toast.makeText(this, "getResponse", Toast.LENGTH_SHORT).show();
                postRequest();
                break;
        }
    }

    //初始化UI
    private void initView() {
        mLoginView = (Button) findViewById(R.id.login_view);
        mCookieView = (Button) findViewById(R.id.get_cookie_view);
        mCookieTextView = (TextView) findViewById(R.id.cookie_show_view);
        mLoginView.setOnClickListener(this);
        mCookieView.setOnClickListener(this);
    }

    /**
     * 发送一个get请求
     */
    private void getRequest(){
        final Request request = new Request.Builder().
                url("https://www.baidu.com").build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCookieTextView.setText(res);
                    }
                });
            }
        });
    }

    /**
     * 发送一个post请求
     */
    private void postRequest(){

        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        formBodyBuild.add("mb","18911230100");
        formBodyBuild.add("pwd","999999w");
        Request request = new Request.Builder()
                .url("http://118.114.41.120:8080/FirstJavaWeb/errorDeal.jsp")
                .post(formBodyBuild.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SimpleOkhttpActivity.this, "出错了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().toString();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCookieTextView.setText(res);
                    }
                });
            }
        });
    }
}
