package com.example.android_learn.component;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_learn.R;
import com.example.android_learn.util.DateUtil;

public class PageActivityA extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_page_send, tv_page_rec;

    private Button btn_page_send;

    private ActivityResultLauncher launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.component_page_activity);

        initComponent();
    }

    private void initComponent() {
        // 初始化
        tv_page_send = findViewById(R.id.tv_page_send);
        tv_page_rec = findViewById(R.id.tv_page_rec);

        btn_page_send = findViewById(R.id.btn_page_send);

        tv_page_send.setText("待发送消息：你好，今天天气怎么样");
        btn_page_send.setText("发送");

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null){
                Bundle extras = result.getData().getExtras();
                String rec_msg = extras.getString("rec_msg");
                String rec_time = extras.getString("rec_time");

                tv_page_rec.setText(String.format("接收时间：%s\n接受内容：%s", rec_time, rec_msg));
            }
        });

        // 设置监听器
        btn_page_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("req_msg", "你好，今天天气怎么样");
        bundle.putString("req_time", DateUtil.getCurTime());

        Intent intent = new Intent(PageActivityA.this, PageActivityB.class);
        intent.putExtras(bundle);

        launcher.launch(intent);
    }
}
