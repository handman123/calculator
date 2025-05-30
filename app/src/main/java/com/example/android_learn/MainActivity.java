package com.example.android_learn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_learn.calculator.Calculator;
import com.example.android_learn.component.Component;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_main_calculator, btn_main_component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        initComponent();
    }

    private void initComponent() {
        // 赋值
        btn_main_calculator = findViewById(R.id.btn_main_calculator);
        btn_main_component = findViewById(R.id.btn_main_component);

        // 监听器
        btn_main_calculator.setOnClickListener(this);
        btn_main_component.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_main_calculator) {
            Intent intent = new Intent(MainActivity.this, Calculator.class);
            startActivity(intent);
        }

        else if (id == R.id.btn_main_component) {
            Intent intent = new Intent(MainActivity.this, Component.class);
            startActivity(intent);
        }
    }

}
