package com.finham.demotest.eleven;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.finham.demotest.R;
import com.finham.demotest.twelve.DataBindingActivity;

public class LiveDataActivity extends AppCompatActivity {
    ViewModelWithLiveData viewModelWithLiveData;
    TextView textView;
    ImageButton like;
    ImageButton dislike;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        textView=findViewById(R.id.textView); //还是需要引用，在DataBinding引入后就不需要创建引用了！
        like=findViewById(R.id.like);
        dislike=findViewById(R.id.dislike);

        //ViewModelProviders is deprecated, look for instructions from the official site.
        viewModelWithLiveData=ViewModelProviders.of(this).get(ViewModelWithLiveData.class);//ViewModel绑定控制器，即当前的Activity
        viewModelWithLiveData.getLikedNumber().observe(this, new Observer<Integer>() { //添加观察者，第一个参数必须是具有管理Lifecycle的对象
            @Override                                                                         //activity就是这样的一个对象
            public void onChanged(Integer integer) { //当数据发生改变时，就会回调该方法
                textView.setText(String.valueOf(integer)); //自动感知，所以就不需要三个地方都写textView.setText(),只需要写一次。
            }                                              //这就是LiveData的作用！
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelWithLiveData.addLikedNumber(1);
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelWithLiveData.addLikedNumber(-1);
            }
        });

        button=findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LiveDataActivity.this, DataBindingActivity.class);
                startActivity(intent);
            }
        });
    }
}
