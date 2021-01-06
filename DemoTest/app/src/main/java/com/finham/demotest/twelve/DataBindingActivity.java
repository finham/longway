package com.finham.demotest.twelve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.finham.demotest.R;
import com.finham.demotest.thirteen.ScoreActivity;
import com.finham.demotest.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {
    //TextView textView;
    //Button button;
    ViewModelWIthDataBinding viewModelWIthDataBinding;
    ActivityDataBindingBinding activityDataBindingBinding;//当你xml文件convert成DataBinding形式后，AS自动帮你创建该类。
    // 命名为反着来最后加上Binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_data_binding); 转化为DataBinding后该句不需要了
        activityDataBindingBinding= DataBindingUtil.setContentView(this, R.layout.activity_data_binding);//改成这句
        //所有xml文件里的TextView/Button/ImageButton都会成为该类对象的成员，可直接访问，例如activityDataBindingBinding.textView3


        //可删除
        //button=findViewById(R.id.button2);//如果你之前用过AS 2.x版本，还要加上类型强转更麻烦，现在省略了这一部分
        //textView=findViewById(R.id.textView2);

        viewModelWIthDataBinding= ViewModelProviders.of(this).get(ViewModelWIthDataBinding.class);

//        viewModelWIthDataBinding.getNumber().observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                //textView.setText(String.valueOf(integer));
//                activityDataBindingBinding.textView2.setText(String.valueOf(integer));
//            }
//        });

//        activityDataBindingBinding.button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewModelWIthDataBinding.add();
//            }
//        });

        //在xml文件里使用@{}这种方式后这两个都可以删去了，取而代之的只需要两行代码：
        activityDataBindingBinding.setNumberCount(viewModelWIthDataBinding);
        activityDataBindingBinding.setLifecycleOwner(this); //这句也是必不可少
        activityDataBindingBinding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataBindingActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });

        //写完了这个活动，几乎和之前的LiveDataActivity一样，来看看缺点：
        //1.每一次需要创建变量并连接绑定findViewById。写起来麻烦，且有错误的概率。
    }
}
