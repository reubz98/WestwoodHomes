package com.example.westwoodhomes.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.westwoodhomes.R;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
