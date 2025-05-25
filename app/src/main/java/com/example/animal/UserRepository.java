package com.example.animal;

import android.content.Context;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
    }

    public void insert(User user) {
        executor.execute(() -> userDao.insert(user));
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
