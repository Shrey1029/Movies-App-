package com.example.movieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieapp.R;

public class loginActivity2 extends AppCompatActivity {
    private EditText userEdt, passEdt;
    private Button loginbtn;

    // Constants for valid credentials
    private static final String VALID_USERNAME = "test";
    private static final String VALID_PASSWORD = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initView();
    }

    private void initView() {
        userEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.editTextPassword);
        loginbtn = findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = userEdt.getText().toString().trim();
        String password = passEdt.getText().toString().trim();

        if (validateInputs(username, password)) {
            if (isValidCredentials(username, password)) {
                // Correct credentials, start dashboard activity
                startActivity(new Intent(loginActivity2.this, dashboard.class));
                finish(); // finish the login activity
            } else {
                // Incorrect credentials
                showToast("Incorrect username or password");
            }
        }
    }

    private boolean validateInputs(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            showToast("Please enter your username");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            showToast("Please enter your password");
            return false;
        }
        return true;
    }

    private boolean isValidCredentials(String username, String password) {
        return username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD);
    }

    private void showToast(String message) {
        Toast.makeText(loginActivity2.this, message, Toast.LENGTH_SHORT).show();
    }
}
