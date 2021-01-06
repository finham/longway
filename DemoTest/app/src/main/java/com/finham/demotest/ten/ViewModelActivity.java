package com.finham.demotest.ten;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.finham.demotest.R;
import com.finham.demotest.eleven.LiveDataActivity;

public class ViewModelActivity extends AppCompatActivity {
    MyViewModel myViewModel;
    TextView textView;
    Button button_add;
    Button button_multiply;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        myViewModel= ViewModelProviders.of(this).get(MyViewModel.class);//注意是使用Providers。ViewModel绑定控制器，即当前的Activity
        textView=findViewById(R.id.number_changed);
        textView.setText(String.valueOf(myViewModel.number)); //这句话保证在屏幕旋转情况下保证数字不丢失
        button_add=findViewById(R.id.button_add);
        button_multiply=findViewById(R.id.button_multiply);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.number++; //因为定义为了public int。
                textView.setText(String.valueOf(myViewModel.number));
            }
        });
        button_multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.number=myViewModel.number*2;
                textView.setText(String.valueOf(myViewModel.number));
            }
        });
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewModelActivity.this, LiveDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
