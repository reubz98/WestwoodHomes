package com.example.westwoodhomes.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        username.addTextChangedListener(watcher);
        name = findViewById(R.id.regName);
        name.addTextChangedListener(watcher);
        surname = findViewById(R.id.regSurname);
        surname.addTextChangedListener(watcher);
        pass = findViewById(R.id.regPassword);
        pass.addTextChangedListener(watcher);
        confirmPass = findViewById(R.id.regConfirmPass);
        confirmPass.addTextChangedListener(watcher);
        unit = findViewById(R.id.regUnit);
        unit.addTextChangedListener(watcher);
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
                    if (pass.equals(confirm)) {
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

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (username.getText().toString().length() == 0 || pass.getText().toString().length() == 0 ||
                confirmPass.getText().toString().length() == 0 || name.getText().toString().length() == 0 ||
                surname.getText().toString().length() == 0 || unit.getText().toString().length() == 0){
                register.setEnabled(true);
            } else {
                register.setEnabled(true);
            }
        }
    };
}
