package com.jerome.okhttpcapture;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private OkHttpClient okHttpClient;
    private Button mRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitCapture.init(this);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CaptureInfoInterceptor())
                .build();

        mRequest = findViewById(R.id.request_net);
        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testNet1();
            }
        });
        testNet();
    }

    public void testNet() {
        Request request =  new Request.Builder()
                .addHeader("test1","testValue1")
                .addHeader("test2","testValue2")
                .url("http://www.baidu.com/test1")
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void testNet1() {
        Request request1 =  new Request.Builder()
                .addHeader("test1","testValue1")
                .addHeader("test2","testValue2")
                .url("https://github.com/seiginonakama/BlockCanaryEx")
                .get()
                .build();

        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG , ""+response.toString());
            }
        });

    }
}