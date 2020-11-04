package com.example.westwoodhomes.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westwoodhomes.MainActivity;
import com.example.westwoodhomes.fCon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.example.westwoodhomes.R;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
{
    DatabaseReference mDatabase;
    final String TAG = "LoginActivity";
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
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });
        mDatabase = fCon.fDatabase.getReference();


    }

    private void Login()
    {
        final String username = this.username.getText().toString();
        final String password = this.password.getText().toString();
        Query query = mDatabase.child("user").orderByChild("username").equalTo(username).limitToFirst(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot item : snapshot.getChildren()){
                        if (item.child("password").getValue(String.class).equals(password)){
                            Log.d(TAG, "Password Correct");
                            Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT);
                            MainActivity.userID = snapshot.getKey();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Log.d(TAG, "Password incorrect");
                            Toast.makeText(LoginActivity.this,"Password incorrect", Toast.LENGTH_SHORT);
                        }
                    }
                }
                else {
                    Log.d(TAG,"No user with username inputted");
                    Toast.makeText(LoginActivity.this,"No user with username inputted", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (username.getText().toString().length() == 0 || password.getText().toString().length() == 0){
                login.setEnabled(false);
            } else {
                login.setEnabled(true);
            }
        }
    };
}
