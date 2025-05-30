package com.example.android_learn.util;

import java.util.List;
import java.util.Stack;

public class SimpleCalculator {
    private final Stack<Double> numsStack;
    private final Stack<String> optsStack;
    private final List<String> inputList;

    public SimpleCalculator(List<String> inputList) {
        numsStack = new Stack<>();
        optsStack = new Stack<>();
        this.inputList = inputList;
    }

    public Double calculate(){
        if (inputList.isEmpty())    // 操作栈为空，直接返回
            return 0.0;
        if (inputList.size() < 3){
            if (inputList.size() == 1 && isNumber(inputList.get(0))){
                return Double.parseDouble(inputList.get(0));
            }
            return 0.0;
        }
        // 遍历list，数字入栈，操作符判断
        for(String str : inputList){
            if (isNumber(str)){ // 数字入栈
                numsStack.push(Double.parseDouble(str));
            }
            else {  // 操作符判断
                // 操作栈空、或优先级大于等于栈顶元素，操作数入栈
                if (optsStack.isEmpty() || precedence(str) >= precedence(optsStack.peek()))
                    optsStack.push(str);
                else {
                    // 要入栈元素小于栈顶元素，计算
                    while (!optsStack.isEmpty() && precedence(str) < precedence(optsStack.peek())){
                        Double num2 = numsStack.pop();
                        Double num1 = numsStack.pop();
                        String opts = optsStack.pop();
                        double res = calculateAAndB(num1, num2, opts);
                        numsStack.push(res);
                    }
                    optsStack.push(str);    // 新操作符入栈
                }
            }
        }

        // 处理剩余符号
        while (!optsStack.isEmpty()){
            Double num2 = numsStack.pop();
            Double num1 = numsStack.pop();
            String opts = optsStack.pop();
            double res = calculateAAndB(num1, num2, opts);
            numsStack.push(res);
        }

        return numsStack.pop();
    }

    private double calculateAAndB(Double num1, Double num2, String opts) {
        double res = 0.0;
        switch (opts){
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num1 - num2;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = num1 / num2;
                break;
            case "√":
                res = Math.pow(num2, 1 / num1);
                break;
        }
        return res;
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private int precedence(String ops){
        switch (ops) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "sqrt":
                return 3;
        }
        return 0;
    }

}
