package com.example.westwoodhomes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity
{
    EditText name, surname, familyName;
    Button finalizeProfile, addFam;
    DatabaseReference mDatabase;
    Resident resident;
    ListView familyList;
    SimpleAdapter sa;
    List<String> family;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mDatabase = fCon.fDatabase.getReference();
        name = findViewById(R.id.edtName);
        surname = findViewById(R.id.edtSurname);
        familyName = findViewById(R.id.edtFamilyName);
        familyList = findViewById(R.id.lsvFamily);
        finalizeProfile = findViewById(R.id.btnFinal);
        addFam = findViewById(R.id.btnAddFam);
        family = new ArrayList<>();
        finalizeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizeProfile();
            }
        });
        addFam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFamily();
            }
        });
        createResident();
    }

    public void createResident(){
        Query residentQuery = mDatabase.child("user").child(MainActivity.userID);
        residentQuery.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String strName = snapshot.child("name").getValue(String.class);
                String strSurname = snapshot.child("surname").getValue(String.class);
                String strUsername = snapshot.child("username").getValue(String.class);
                String strPassword = snapshot.child("password").getValue(String.class);
                name.setText(strName);
                surname.setText(strSurname);
                int UnitNo = snapshot.child("unitNo").getValue(Integer.class);
                if (snapshot.child("family").hasChildren())
                {
                    List<HashMap<String,String>> fam = new ArrayList<>();
                    int count = 0;
                    for (DataSnapshot snap : snapshot.child("family").getChildren())
                    {
                        HashMap<String,String> hash = new HashMap<>();
                        hash.put("name",snap.getValue(String.class));
                        fam.add(hash);
                        family.add(snap.getValue(String.class));
                    }
                    sa = new SimpleAdapter(EditProfileActivity.this, fam, R.layout.family_display,
                            new String[]{"name"}, new int[]{R.id.fam_name});
                    familyList.setAdapter(sa);
                    setListViewHeightBasedOnChildren(familyList);
                }
                String[] arr = new String[family.size()];
                int count = 0;
                for (String item : family){
                    arr[count] = item;
                    count++;
                }
                resident = new Resident(strName, strSurname, strUsername, strPassword, UnitNo, arr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

    }

    public void addFamily(){
        String name = this.familyName.getText().toString();
        family.add(name);
        List<HashMap<String,String>> fam = new ArrayList<>();
        for (String item : family){
            HashMap<String,String> hash = new HashMap<>();
            hash.put("name",item);
            fam.add(hash);
        }
        sa = new SimpleAdapter(EditProfileActivity.this, fam, R.layout.family_display,
                new String[]{"name"}, new int[]{R.id.fam_name});
        familyList.setAdapter(sa);
        setListViewHeightBasedOnChildren(familyList);
        this.familyName.setText("");
    }

    public void finalizeProfile()
    {
        String name = this.name.getText().toString();
        String surname = this.surname.getText().toString();
        mDatabase.child("user").child(MainActivity.userID).child("name").setValue(name);
        mDatabase.child("user").child(MainActivity.userID).child("surname").setValue(surname);
        int count = 1;
        for (String item : family)
        {
            mDatabase.child("user").child(MainActivity.userID).child("family").child(Integer.toString(count)).setValue(item);
            count++;
        }
        finish();
        /*this.name.setText("");
        this.surname.setText("");*/
    }

    private void setListViewHeightBasedOnChildren(ListView listView)
    {
        Log.e("Listview Size ", "" + listView.getCount());
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {

            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
