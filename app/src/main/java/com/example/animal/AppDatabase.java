package com.example.animal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * AppDatabase 是 Room 数据库的核心类，使用单例模式创建
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // 声明单例实例
    private static volatile AppDatabase INSTANCE;

    // 提供 UserDao 抽象方法（Room 自动实现）
    public abstract UserDao userDao();

    /**
     * 获取数据库单例实例
     * @param context 应用上下文
     * @return 数据库实例
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "animal_database") // 数据库名称
                            .fallbackToDestructiveMigration() // 版本不一致时重建数据库
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
