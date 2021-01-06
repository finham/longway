package com.finham.roomdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button_insert, button_update, button_clear, button_delete;
    TextView textView;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    WordAdapter wordAdapter1;
    //List<WordEntity> words = new ArrayList<>();
    Switch aSwitch;
    WordAdapter wordAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.RecyclerView_Word);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordAdapter1 = new WordAdapter(false, wordViewModel);
        wordAdapter2 = new WordAdapter(true, wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordAdapter1);

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    recyclerView.setAdapter(wordAdapter2);
                else
                    recyclerView.setAdapter(wordAdapter1);
            }
        });
        textView = findViewById(R.id.textViewEnglish);
        //wordViewModel= new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(WordViewModel.class);
        //wordViewModel=new ViewModelProvider.NewInstanceFactory().create(WordViewModel.class);
        wordViewModel.getList().observe(this, new Observer<List<WordEntity>>() {
            @Override
            public void onChanged(List<WordEntity> wordEntities) {
                int temp = wordAdapter1.getItemCount(); //一定要先用一个变量将count存起来,获取当前adapter里面数据的长度
                wordAdapter1.setWords(wordEntities);
                wordAdapter2.setWords(wordEntities);
                //if (wordAdapter1.getItemCount() != wordEntities.size()) { //这样写不行!一定要定义一个变量
                if (temp!=wordEntities.size()){ //注意是wordEntities先变化的,再引起wordAdapter去更新自己的数据
                    wordAdapter1.notifyDataSetChanged();
                    wordAdapter2.notifyDataSetChanged();
                }
                //words=wordEntities;
                //wordAdapter=new WordAdapter(words);
                //recyclerView.setAdapter(wordAdapter);
            }
        });

        /*
          以下均为按钮和设置点击
         */
        button_insert = findViewById(R.id.button_insert);
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] english = {"Hello", "Finnegan", "Welcome", "To", "Computer", "Science"};
                String[] chinese = {"你好", "芳汉", "欢迎", "到", "计算机", "科学"};
                for (int i = 0; i < english.length; i++) {
                    wordViewModel.insertWords(new WordEntity(english[i], chinese[i]));
                }
            }
        });

        button_update = findViewById(R.id.button_update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordEntity wordEntity = new WordEntity("Hi", "你好啊");
                wordEntity.setId(3);
                wordViewModel.updateWords(wordEntity);
            }
        });

        button_clear = findViewById(R.id.button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordViewModel.clearWords();
            }
        });

        button_delete = findViewById(R.id.button_delete);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordEntity wordEntity = new WordEntity("World", "世界"); //word和meaning不需正确也能删除，因为依靠Id来删的
                wordEntity.setId(5);
                wordViewModel.deleteWords(wordEntity);
            }
        });
    }
}
