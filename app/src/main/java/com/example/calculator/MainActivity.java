package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.util.SimpleCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> conInput;
    private String preInput;
    private boolean preIsNumber;
    private boolean preIsEqual;
    private boolean preIsOpts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        conInput = new ArrayList<>();
        preInput = "";
        preIsNumber = false;
        preIsEqual = false;
        preIsOpts = false;

        int[] allIds = new int[]{
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
                R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_point,
                R.id.btn_plus, R.id.btn_sub, R.id.btn_multiply, R.id.btn_devide, R.id.btn_sqrt,
                R.id.btn_ce, R.id.btn_c, R.id.btn_1_x, R.id.btn_equal
        };


        // 设置监听器
        for(int id : allIds){
            findViewById(id).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 1. 点击了数字
        if (isClickNumber(id)){
            clickNumber(v);
        }
        // 2. 点击了操作符
        else if (isClickOpts(id)) {
            clickOpts(v);
        }
        // 3. 点击了回退符
        else if (isClickBackKey(id)){
            clickBackKey(v);
        }
        // 4. 点击了求和
        else {
            clickEqual(v);
        }
    }

    private void clickEqual(View v) {

//        Toast.makeText(this, conInput.toString(), Toast.LENGTH_SHORT).show();
        if (preIsOpts)  // 上一个操作时操作符，退出
            return;

        // 计算
        SimpleCalculator simpleCalculator = new SimpleCalculator(conInput);
        double res = simpleCalculator.calculate();

        // 清空
        conInput.clear();


        // 更新
        conInput.add(String.valueOf(res));
        preInput = "";
        preIsEqual = true;
        preIsOpts = false;
        preIsNumber = false;

        // 刷新
        updateView();
    }

    private void clickBackKey(View v) {
        String inputKey = (String) ((Button) v).getText();

        // CE清除最近输入
        if (inputKey.equals("CE")){
            if (conInput.isEmpty()) // 当前数组为空
                return;
            if (conInput.size() == 1){
                conInput.clear();
                preIsEqual = false;
                preIsOpts = false;
                preIsNumber = false;
                preInput = "";
            }
            else {
                conInput.remove(conInput.size() - 1);   // 移除最后一个符号
                preIsEqual = false;
                preInput = conInput.get(conInput.size() - 1);
                if (preInput.equals("+") || preInput.equals("-") || preInput.equals("*") || preInput.equals("/") || preInput.equals("sqrt") || preInput.equals("1/x")){
                    preIsOpts = true;
                    preIsNumber = false;
                }
                else {
                    preIsOpts = false;
                    preIsNumber = true;
                }
            }

        }

        // C清除所有
        else if (inputKey.equals("C")){
            conInput.clear();
            preIsEqual = false;
            preIsOpts = false;
            preIsNumber = false;
            preInput = "";
        }

        updateView();
    }

    private void clickOpts(View v) {
        String inputKey;
        // 获取操作按键字符
        if (v.getId() == R.id.btn_sqrt)
            inputKey = "sqrt";
        else inputKey = (String) ((Button) v).getText();

        // 判断前一个输入是否为操作符
        if (preIsOpts){
            conInput.remove(conInput.size() - 1); // 移除上一个输入的字符
        }

        if (preIsEqual || conInput.isEmpty()){  // 前一个输入为相等或当前输入为空，退出
            return;
        }

        // 加减乘除
        switch (inputKey) {
            case "+":
            case "-":
            case "*":
            case "/":
                conInput.add(inputKey);
                break;

            // 开方
            case "sqrt":
                conInput.add("√");
                break;

            // 1/X
            case "1/x":
                double preNum = Double.parseDouble(conInput.get(conInput.size() - 1));
                conInput.set(conInput.size() - 1, String.valueOf(1/preNum));
                preIsNumber = true;
                preIsOpts = false;
                preIsEqual = false;
                preInput = String.valueOf(preNum);
                updateView();
                return;
        }

        // 更新
        preIsNumber = false;
        preIsOpts = true;
        preIsEqual = false;
        preInput = inputKey;

        updateView();
    }

    private void clickNumber(View v) {
        String inputNum = (String) ((Button) v).getText();
        // 上一次操作为等号，清空
        if (preIsEqual){
            conInput.clear();
        }
        // 上一次操作为输入0
        if (preInput.equals("0"))
            conInput.remove(conInput.size() - 1);
        // 上一次操作是操作符或数字
        if (preIsNumber){
            conInput.set(conInput.size() - 1, conInput.get(conInput.size() - 1) + inputNum);
        }
        else conInput.add(inputNum);

        // 更新状态
        preIsNumber = true;
        preIsOpts = false;
        preIsEqual = false;
        preInput = inputNum;

        // 更新视图
        updateView();
    }

    private void updateView(){
        TextView tv = findViewById(R.id.tv_result);
        StringBuilder sb = new StringBuilder();
        for (String str : conInput){
            sb.append(str);
        }
        String desc = sb.length() == 0 ? "0" : sb.toString();
        tv.setText(desc);
    }

    private boolean isClickBackKey(int id) {
        return id == R.id.btn_ce || id == R.id.btn_c;
    }

    private boolean isClickOpts(int id) {
        return id == R.id.btn_plus || id == R.id.btn_sub || id == R.id.btn_multiply || id == R.id.btn_devide || id == R.id.btn_1_x || id == R.id.btn_sqrt;
    }

    private boolean isClickNumber(int id) {
        return id == R.id.btn_0 || id == R.id.btn_1 || id == R.id.btn_2 ||
                id == R.id.btn_3 || id == R.id.btn_4 || id == R.id.btn_5 ||
                id == R.id.btn_6 || id == R.id.btn_7 || id == R.id.btn_8 ||
                id == R.id.btn_9 || id == R.id.btn_point;
    }
}
