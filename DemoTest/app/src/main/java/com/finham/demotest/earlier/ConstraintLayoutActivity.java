package com.finham.demotest.earlier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finham.demotest.R;
import com.finham.demotest.ten.ViewModelActivity;

public class ConstraintLayoutActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        button=findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ConstraintLayoutActivity.this, ViewModelActivity.class);
                startActivity(intent);
            }
        });
    }
}
