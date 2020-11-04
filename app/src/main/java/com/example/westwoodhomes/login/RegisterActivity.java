package com.example.westwoodhomes.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.westwoodhomes.R;
import com.example.westwoodhomes.Resident;
import com.example.westwoodhomes.fCon;
import com.example.westwoodhomes.md5;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity
{
    final String TAG = "RegisterActivity";
    DatabaseReference mDatabase;
    EditText username, name, surname, pass, confirmPass, unit;
    Button register;
    TextView Login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase = fCon.fDatabase.getReference();
        username = (EditText) findViewById(R.id.regUser);
        name = findViewById(R.id.regName);
        surname = findViewById(R.id.regSurname);
        pass = findViewById(R.id.regPassword);
        confirmPass = findViewById(R.id.regConfirmPass);
        unit = findViewById(R.id.regUnit);
        register = findViewById(R.id.btnReg);
        Login = findViewById(R.id.tvLogin);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Register();
            }
        });

        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }

        });



    }

    public void Register()
    {
        final String user = username.getText().toString();
        final String pass = this.pass.getText().toString();
        final String confirm = confirmPass.getText().toString();
        final String name = this.name.getText().toString();
        final String surname = this.surname.getText().toString();
        final int unit = Integer.parseInt(this.unit.getText().toString());
        Query query = mDatabase.child("user").orderByChild("username").equalTo(user);
        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    Log.d(TAG, "Username taken.");
                    Toast.makeText(RegisterActivity.this, "Username Taken", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "Username is free.");
                    if (pass.equals(confirm))
                    {
                        Resident res = new Resident(name, surname,user, md5.encryptPass(pass),unit);
                        mDatabase.child("user").push().setValue(res);

                        Toast.makeText(RegisterActivity.this, "User Created.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.d(TAG, error.getMessage());
            }
        });

    }
}
