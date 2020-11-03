package com.example.westwoodhomes.login;

import androidx.appcompat.app.AppCompatActivity;

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
        username = (EditText) findViewById(R.id.txtUserame);
        password = (EditText) findViewById(R.id.txtPass);
        login = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvReg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void Login(){

    }
}
