package com.finham.demotest.twenty_one;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Chronometer;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * User: Fin
 * Date: 2020/2/3
 * Time: 16:17
 */
public class MyChronometer extends Chronometer implements LifecycleObserver {
    private static long elapsedTime;

    //因为要用在xml里面，所以选用第二个构造方法
    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pauseChronometer(){
        elapsedTime = System.currentTimeMillis() - getBase();
        stop(); //写不写无所谓
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resumeChronometer(){
        setBase(SystemClock.elapsedRealtime()-elapsedTime);
        start(); //写不写无所谓
    }

    /*
     * 用该类创建的对象会自动响应Activity的生命周期
     */
}
