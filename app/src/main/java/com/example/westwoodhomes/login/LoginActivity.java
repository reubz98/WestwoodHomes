package com.example.westwoodhomes.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.westwoodhomes.fCon;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.example.westwoodhomes.R;

public class LoginActivity extends AppCompatActivity
{
    DatabaseReference mDatabase;
    Button login;
    EditText username, password;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPass);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.tvReg);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Login();
            }
        });
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent());
            }
        });
        mDatabase = fCon.fDatabase.getReference();

    }

    private void Login()
    {

        String username, password;
        username = this.username.getText().toString();
        password = this.password.getText().toString();

    }
}
