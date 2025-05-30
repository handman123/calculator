package com.example.android_learn.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_learn.R;
import com.example.android_learn.util.DateUtil;

public class PageActivityB extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_page_send, tv_page_rec;

    private Button btn_page_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.component_page_activity);

        initComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();

        if (extras != null && !extras.isEmpty()){
            String ace = extras.getString("req_msg");
            String time = extras.getString("req_time");

            tv_page_rec.setText(String.format("接收时间：%s\n接收内容：%s", time, ace));
        }
    }

    private void initComponent() {
        // 初始化
        tv_page_send = findViewById(R.id.tv_page_send);
        tv_page_rec = findViewById(R.id.tv_page_rec);

        btn_page_send = findViewById(R.id.btn_page_send);

        tv_page_send.setText("待发送消息：今天天气很好");
        btn_page_send.setText("发送");

        // 设置监听器
        btn_page_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("rec_msg", "你好，今天天气怎么样");
        bundle.putString("rec_time", DateUtil.getCurTime());

        Intent intent = new Intent();
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);

        finish();
    }
}
