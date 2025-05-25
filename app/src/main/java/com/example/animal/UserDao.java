package com.example.animal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Query("SELECT * FROM user_table WHERE username = :username")
    User findByUsername(String username);

    @Query("SELECT * FROM user_table") // ✅ 加了这一行
    List<User> getAllUsers();
}
