package com.finham.pagingdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * User: Fin
 * Date: 2020/2/28
 * Time: 15:29
 */
@Database(entities = {Student.class},version = 1,exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    private static volatile StudentDatabase instance;
    static synchronized StudentDatabase getInstance(Context context){
        if(instance==null) {
            instance= Room.databaseBuilder(context,StudentDatabase.class,"student_database").build();
        }
        return instance;
    }
    abstract StudentDao getStudentDao();
}
