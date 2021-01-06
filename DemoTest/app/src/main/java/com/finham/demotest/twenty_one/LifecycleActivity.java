package com.finham.demotest.twenty_one;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.finham.demotest.R;

public class LifecycleActivity extends AppCompatActivity {
    //Chronometer chronometer;
    //private long elapsedTime; //存放时间的变量都用long，每一次Chronometer退出前记了多少时间要把它记下来

    MyChronometer chronometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        chronometer = findViewById(R.id.meter);
        //chronometer.setBase(SystemClock.elapsedRealtime()); //设定计时的起点
        //System.currentTimeMillis()：UNIX时间 1970.01.01 0点
        //SystemClock.elapsedRealtime()：手机从上一次开机到现在所经过的时间的毫秒数，用这个做时间段的统计是最可靠的

        //需要新加一句话：给chronometer设置owner即当前的Activity
        getLifecycle().addObserver(chronometer);
    }

//    @Override
//    protected void onPause() {
//        super.onPause(); //Overriding method should call super.onPause(). Some methods require that you also call the super implementation as part of your method.
//        elapsedTime = System.currentTimeMillis() - chronometer.getBase();
//        //chronometer.stop();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
//        //chronometer.start();
//    }
}
