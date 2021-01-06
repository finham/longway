package com.finham.pagingdemo;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * User: Fin
 * Date: 2020/2/28
 * Time: 16:08
 */
public class MyPagedAdapter extends PagedListAdapter<Student, MyPagedAdapter.StudentViewModel> {

    //这里也有一个DiffUtil的callback，用来比较两组数之间的差异。将参数里的删掉，在super里重写
    protected MyPagedAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getStudentNumber() == newItem.getStudentNumber();
            }
        });
    }

    @NonNull
    @Override
    public StudentViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cell,parent,false);
        StudentViewModel studentViewModel=new StudentViewModel(view);
        return studentViewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewModel holder, int position) {
        //采用Paging后Student可能为空（按需加载嘛），所以需要判空。
        Student student=getItem(position);
        if(student==null){
            holder.textView.setText("loading");
        }else {
            holder.textView.setText(String.valueOf(student.getStudentNumber()));
        }
    }

    static class StudentViewModel extends RecyclerView.ViewHolder {
        private TextView textView;

        public StudentViewModel(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
