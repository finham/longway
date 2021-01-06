package com.finham.roomdemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * User: Fin
 * Date: 2020/2/3
 * Time: 20:22
 */
@Entity
public class WordEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "english_word")//给一个名称便于记忆，不写的话以变量名称为ColumnInfo
    private String word;
    @ColumnInfo(name = "chinese_meaning")
    private String meaning;
//    @ColumnInfo(name = "foo_data")
//    private boolean foo;

    @ColumnInfo(name = "chinese_invisible")
    private boolean chineseInvisible;

    public WordEntity(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

//    public boolean isFoo() {
//        return foo;
//    }
//
//    public void setFoo(boolean foo) {
//        this.foo = foo;
//    }

    public boolean isChineseInvisible() {
        return chineseInvisible;
    }

    public void setChineseInvisible(boolean chineseInvisible) {
        this.chineseInvisible = chineseInvisible;
    }
}
