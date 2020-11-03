package com.example.westwoodhomes.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.westwoodhomes.R;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText username, password;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPass);
        login = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvReg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent());
            }
        });


    }

    private void Login(){
        String username, password;
        username = this.username.getText().toString();
        password = this.password.getText().toString();

    }
}
