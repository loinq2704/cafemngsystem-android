package com.loinq.cafemngsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.loinq.cafemngsystem.db.viewModel.UserViewModel;
import com.loinq.cafemngsystem.dbo.OrderDto;
import com.loinq.cafemngsystem.dbo.UserDto;

public class LoginActivity extends AppCompatActivity {

    private static final String APP_NAME = "cafemngsystem";
    private EditText edtUsername;
    private EditText edtPwd;
    private Button btnLogin;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Gson gson;
    private UserViewModel mUserViewModel;

    private void bindingView(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPwd = findViewById(R.id.edtPwd);
        btnLogin = findViewById(R.id.btnRegister);

        mSharedPreferences = getSharedPreferences(APP_NAME, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        gson = new Gson();
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    };

    private void bindingAction(){
        btnLogin.setOnClickListener(this::onClickBtnLogin);
    }

    private void onClickBtnLogin(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPwd.getText().toString();

        mUserViewModel.getByUsername(username).observe(this, user -> {
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    String orderJson = mSharedPreferences.getString("orderDto", null);
                    OrderDto orderDto = gson.fromJson(orderJson, OrderDto.class);
                    if (orderDto == null) {
                        orderDto = new OrderDto();
                    }
                    UserDto userDto = new UserDto(user);
                    orderDto.setUser(userDto);
                    orderJson = gson.toJson(orderDto);
                    mEditor.putString("orderDto", orderJson);
                    mEditor.apply();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                UserDto userDto = new UserDto();
                userDto.setUsername(username);
                userDto.setPassword(password);
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("USER", userDto);
                startActivity(intent);
            }
        });
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
    }
}