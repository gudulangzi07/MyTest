package com.mytest.mvvm.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mytest.mvvm.db.converter.DateConverter;
import com.mytest.mvvm.db.dao.MVVMDao;
import com.mytest.mvvm.db.model.MVVMDB;

/**
 * 如果去掉exportSchema = false，则需要在build.gradle中加上room的输出目录
 * javaCompileOptions {
 *             annotationProcessorOptions {
 *                 arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
 *             }
 *         }
 * */
@Database(entities = {MVVMDB.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String databaseName = "MVVMDataBase";
    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(Context context){
        if (appDatabase == null){
            synchronized (AppDatabase.class){
                if (null == appDatabase){
                    appDatabase = Room.databaseBuilder(context, AppDatabase.class, databaseName)
                            //用来做数据库升级
                            //.addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }

        return appDatabase;
    }

    public abstract MVVMDao getMVVMDao();

    public void closeDb(){
        appDatabase.close();
        appDatabase = null;
    }

    //数据库变动添加Migration
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //执行在库中加字段的SQL
            database.execSQL("ALTER TABLE chat_message_db ADD COLUMN is_grown INTEGER NOT NULL DEFAULT 0");
        }
    };
}
