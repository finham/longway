package com.finham.roomdemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/2/3
 * Time: 20:30
 */
@Dao //Database access object
public interface WordDao {
    @Insert
    void insertWord(WordEntity... words);//返回值也可以写作long，如果只插入一条数据可以带回插入值的ID。

    @Update
    void updateWords(WordEntity... words);//返回值也可以写作int，返回更新了几条数据

    @Delete
    void deleteWords(WordEntity... wordEntities);

    @Query("DELETE FROM WORDENTITY")
    void deleteAllWords();

    @Query("SELECT * FROM WORDENTITY ORDER BY ID DESC")
        //DESC：降序，最新的记录放在最前面
        //List<WordEntity> getAllWords();
    LiveData<List<WordEntity>> getAllWords(); //这个为什么不用写AsyncTask呢？因为LiveData系统是自动放在父线程上执行
}
