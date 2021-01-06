package com.finham.pagingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button_populate,button_clear;
    StudentDao studentDao;
    StudentDatabase studentDatabase;
    MyPagedAdapter adapter;
    LiveData<PagedList<Student>> allStudentsLivePaged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        adapter=new MyPagedAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        studentDatabase=StudentDatabase.getInstance(this);
        studentDao=studentDatabase.getStudentDao();
        //数据越小代表每次分页加载的数据量越少，就越容易看出效果
        allStudentsLivePaged = new LivePagedListBuilder<>(studentDao.getAllStudents(),30).build();
        allStudentsLivePaged.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(final PagedList<Student> students) {
                adapter.submitList(students);
                //以下加的代码是为了让你看更明白分页加载的一个效果，其实上面30改为2的话就很明显能看出分页加载的效果了。
                //但是知道一下这个方法也不错。
                //PagedList可以加载一个callback，weak表示不会造成内存泄漏。
                students.addWeakCallback(null, new PagedList.Callback() {//Snapshot previously captured from this List, or null.
                    @Override
                    public void onChanged(int position, int count) { //每次从数据源加载数据的时候就会回调onChanged
                        Log.d("Observer",students.toString());
                        //如果你30→2，onChanged打log都还觉得不够明显，那么在上面的log打断点debug调试程序。
                    }

                    @Override
                    public void onInserted(int position, int count) { }

                    @Override
                    public void onRemoved(int position, int count) { }
                });
            }
        });

        button_populate=findViewById(R.id.button_populate);
        button_populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设为1001，i=1;i<=1000，这样下面也会会出错的，因为插入的时候student[0]没有赋值，直接空指针getId为空。
                Student[] students=new Student[1000];
                for(int i=0;i<1000;i++){
                    Student student = new Student();
                    student.setStudentNumber(i);
                    students[i]=student;
                }
                InsertTask insertTask=new InsertTask(studentDao);
                insertTask.execute(students);
            }
        });

        button_clear=findViewById(R.id.button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearTask clearTask=new ClearTask(studentDao);
                clearTask.execute();
            }
        });
    }

    static class InsertTask extends AsyncTask<Student,Void,Void>{
        StudentDao studentDao;

        public InsertTask(StudentDao studentDao) { //将dao传过来，因为静态类是没办法访问外部的变量的。
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudent(students);
            return null;
        }
    }

    static class ClearTask extends AsyncTask<Void,Void,Void>{
        StudentDao studentDao;

        public ClearTask(StudentDao studentDao) { //将dao传过来，因为静态类是没办法访问外部的变量的。
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAllStudents();
            return null;
        }
    }
}
