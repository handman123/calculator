package com.example.android_learn.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_learn.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Component extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = "ActStartActivity";

    private TextView tv_component_log;

    private String mStr = "";

    private Button btn_component_start, btn_component_send_rec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.component_activity);

        initComponent();

        refreshLog("onCreate");

    }

    private void initComponent() {
        btn_component_start = findViewById(R.id.btn_component_start);
        btn_component_send_rec = findViewById(R.id.btn_component_send_rec);

        tv_component_log = findViewById(R.id.tv_component_log);

        btn_component_start.setOnClickListener(this);
        btn_component_send_rec.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_component_start) {
            // 页面跳转
            Intent intent = new Intent(Component.this, ActFinishAActivity.class);
            startActivity(intent);
        } else if (id == R.id.btn_component_send_rec) {
            Intent intent = new Intent(Component.this, ActSentMsg.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshLog("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLog("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        refreshLog("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        refreshLog("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshLog("onDestroy");
    }

    private void refreshLog(String desc) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDate = dateFormat.format(new Date());
        mStr = String.format("%s %s %s %s\n", mStr, curDate, TAG, desc);
        tv_component_log.setText(mStr);
    }




}
