package com.finham.wordrecord;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * User: Fin
 * Date: 2020/2/3
 * Time: 20:45
 */
@Database(entities = {WordEntity.class}, version = 4, exportSchema = false)
//如果有多个entity用逗号隔开；如果你要增加字段导致数据库结构发生改变那版本就要增加并配合Migration；
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase instance;

    static synchronized WordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "Word")//Word是databse的名称
                    //.fallbackToDestructiveMigration() //destructive 破坏式迁移
                    .addMigrations(MIGRATION_3_4)
                    .build();
        }
        return instance;
    }

    public abstract WordDao getWordDao(); //不用实现，由系统自动生成。
    //如果有多个Entity，则应该写多个Dao

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE wordentity ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    // 删除表某一列的骚操作
    static final Migration MIGRATION_2_3_NOT_USED = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL,word TEXT,meaning TEXT)");//创建新的表
            // SELECT后错误，因为wordentity中是id，english_word，chinese_meaning！注意是ColumnInfo中设置的
            database.execSQL("INSERT INTO word_temp(id,word,meaning) SELECT id,word,meaning FROM wordentity"); //提取旧表复制给新表
            database.execSQL("DROP TABLE wordentity");
            database.execSQL("ALTER TABLE word_temp RENAME to word");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE wordentity ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 0");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL,english_word TEXT,chinese_meaning TEXT,chinese_invisible INTEGER NOT NULL DEFAULT 0)");//创建新的表
            database.execSQL("INSERT INTO word_temp(id,english_word,chinese_meaning,chinese_invisible) " +
                    "SELECT id,english_word,chinese_meaning,chinese_invisible FROM WordEntity"); //提取旧表复制给新表
            database.execSQL("DROP TABLE WordEntity");
            database.execSQL("ALTER TABLE word_temp RENAME to wordentity");
        }
    };
}
