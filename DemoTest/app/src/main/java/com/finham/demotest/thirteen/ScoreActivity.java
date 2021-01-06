package com.finham.demotest.thirteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.finham.demotest.R;
import com.finham.demotest.fourteen.RestoreActivity;
import com.finham.demotest.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {
    ScoreViewModel scoreViewModel;
    ActivityScoreBinding binding; //系统帮咱们创建的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_score);
        //scoreViewModel= ViewModelProviders.of(this).get(ScoreViewModel.class);    //目前废弃过时了
        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);//看StackOverflow解决方案应该是这个
        binding.setData(scoreViewModel);
        binding.setLifecycleOwner(this);
        binding.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, RestoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
