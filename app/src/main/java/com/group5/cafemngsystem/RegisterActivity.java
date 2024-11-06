package com.group5.cafemngsystem;

import android.content.Intent;
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

import com.group5.cafemngsystem.customer.CustomerActivity;
import com.group5.cafemngsystem.db.entity.enum1.Role;
import com.group5.cafemngsystem.db.entity.User;
import com.group5.cafemngsystem.db.viewModel.UserViewModel;
import com.group5.cafemngsystem.dbo.UserDto;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPwd;
    private EditText edtFullname;
    private Button btnRegister;

    private UserViewModel mUserViewModel;

    private void bindingView(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPwd = findViewById(R.id.edtPwd);
        edtFullname = findViewById(R.id.edtFName);
        btnRegister = findViewById(R.id.btnRegister);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    };

    private void bindingAction(){
        btnRegister.setOnClickListener(this::onClickBtnRegister);
    }

    private void onClickBtnRegister(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPwd.getText().toString();
        String fullname = edtFullname.getText().toString();
        if(password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 character.", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(username, password, fullname, Role.USER);
        mUserViewModel.insert(user);
        Intent intent = new Intent(RegisterActivity.this, CustomerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        receiveIntent();
    }

    private void receiveIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            UserDto user = (UserDto) intent.getSerializableExtra("USER");
            if (user != null) {
                edtUsername.setText(user.getUsername());
                edtPwd.setText(user.getPassword());
            }
        }
    }
}