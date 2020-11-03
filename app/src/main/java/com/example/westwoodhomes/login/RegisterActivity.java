package com.example.westwoodhomes.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.westwoodhomes.R;
import com.example.westwoodhomes.Resident;
import com.example.westwoodhomes.User;
import com.example.westwoodhomes.fCon;
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
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Register(){
        final String user = username.getText().toString();
        final String pass = this.pass.getText().toString();
        final String confirm = confirmPass.getText().toString();
        final String name = this.name.getText().toString();
        final String surname = this.surname.getText().toString();
        final int unit = Integer.parseInt(this.unit.getText().toString());
        Query query = mDatabase.child("user").orderByChild("username").equalTo(user);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.d(TAG, "Username taken.");
                }
                else{
                    Log.d(TAG, "Username exists.");
                    if (pass.equals(confirmPass)) {
                        Resident res = new Resident(name, surname,user,pass,unit);
                        mDatabase.child("user").push().setValue(res);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });

    }
}
