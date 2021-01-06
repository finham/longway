package com.finham.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

/**
 * User: Fin
 * Date: 2020/2/2
 * Time: 20:33
 */
public class ScoreViewModel extends AndroidViewModel {
    private static final String HIGHEST_SCORE = "highest_score";
    private static final String LEFT_NUMBER = "left_number";
    private static final String RIGHT_NUMBER = "right_number";
    private static final String OPERATOR = "operator";
    private static final String ANSWER = "answer";
    private static final String SAVE_SP_DATA_NAME = "save_sp_data_name";
    private static final String CURRENT_SCORE = "current_score";
    private SavedStateHandle handle;
    public boolean win_flag;

    public ScoreViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(HIGHEST_SCORE)) {//第一次加载进来的话是没有任何数据的，如果handle不包含KEY_HIGHEST_SCORE(最高得分)这个key
            SharedPreferences sp = getApplication().getSharedPreferences(SAVE_SP_DATA_NAME, Context.MODE_PRIVATE);//创建sp对象
            handle.set(HIGHEST_SCORE, sp.getInt(HIGHEST_SCORE, 0));//handle要设置一个KEY-VALUE（SP也是KEY-VALUE），不要搞混
            handle.set(LEFT_NUMBER, 0);
            handle.set(RIGHT_NUMBER, 0);
            handle.set(OPERATOR, "+");
            handle.set(ANSWER, 0);
            handle.set(CURRENT_SCORE, 0);
        }
        this.handle = handle;
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return handle.getLiveData(RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(OPERATOR);
    }

    public MutableLiveData<Integer> getHighestScore() { //额，根本不用声明一个对象，有这个方法就可以自动生成对象了吗？。。。
        return handle.getLiveData(HIGHEST_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getAnswer() {
        return handle.getLiveData(ANSWER);
    }

    void generator() { //生成题目的方法
        int LEVEL = 20;
        Random random = new Random();
        int x, y;
        x = random.nextInt(LEVEL) + 1;//nextInt的值是从0~LEVEL-1，不希望出现0，所以补1
        y = random.nextInt(LEVEL) + 1;
        if (x % 2 == 0) { //希望加法减法的几率各为50%，所以用x的奇偶性来判断
            getOperator().setValue("+");
            if (x > y) { //如果x大于y，把x作为答案
                getAnswer().setValue(x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x - y);
            } else {
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x > y) {
                getAnswer().setValue(x - y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            } else {
                getAnswer().setValue(y - x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    void save() {
        SharedPreferences sp = getApplication().getSharedPreferences(SAVE_SP_DATA_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(HIGHEST_SCORE, getHighestScore().getValue()); //不会为空
        editor.apply();
    }

    @SuppressWarnings("ConstantConditions")
    void answerCorrectly() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighestScore().getValue()) {
            getHighestScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generator();
    }
}
