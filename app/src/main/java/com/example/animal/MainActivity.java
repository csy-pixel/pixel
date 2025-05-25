package com.example.animal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnLogout;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        tvWelcome = findViewById(R.id.tv_welcome);
        btnLogout = findViewById(R.id.btn_logout);

        // 获取用户名
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        if (username == null) {
            username = "用户";
        }

        // 显示欢迎信息
        tvWelcome.setText("欢迎回来，" + username);

        // 请求通知权限（Android 13+）
        NotificationUtil.requestNotificationPermission(this);

        // 发送欢迎通知
        NotificationUtil.sendNotification(this, "欢迎回来", username + "，很高兴见到你！");

        // 退出登录按钮点击事件
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // 权限请求结果处理（必须实现）
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NotificationUtil.getRequestCode()) {
            // 用户授权后，再发送通知（可选）
            NotificationUtil.sendNotification(this, "欢迎回来", username + "，通知权限已开启！");
        }
    }
}
