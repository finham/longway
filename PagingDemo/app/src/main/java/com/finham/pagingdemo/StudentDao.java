package com.finham.pagingdemo;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/2/28
 * Time: 15:23
 */
@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student... students);
    @Delete
    void deleteStudent(Student... students);
    @Query("DELETE FROM STUDENT_TABLE")
    void deleteAllStudents();

    @Query("SELECT * FROM STUDENT_TABLE ORDER BY id")
    //LiveData<List<Student>> getAllStudents();//之前本来是这样写的，但是这一次引入了Paging，就不这样获取了
    DataSource.Factory<Integer,Student> getAllStudents();
}
