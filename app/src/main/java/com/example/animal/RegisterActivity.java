package com.example.animal;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etEmail;
    private Button btnRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化视图
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etEmail = findViewById(R.id.et_email);
        btnRegister = findViewById(R.id.btn_register);

        // 初始化数据库
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user_database").build();

        // 注册按钮点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 检查用户名是否已存在
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User user = db.userDao().findByUsername(username);

                        if (user != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }

                        // 创建新用户
                        User newUser = new User(username, password, email);
                        db.userDao().insert(newUser);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                // 返回登录页面
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    // 邮箱格式验证
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}