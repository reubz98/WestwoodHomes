package com.example.westwoodhomes.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.LiveFolders;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.westwoodhomes.EditProfileActivity;
import com.example.westwoodhomes.MainActivity;
import com.example.westwoodhomes.R;
import com.example.westwoodhomes.Resident;
import com.example.westwoodhomes.fCon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment
{
    Button profile;
    DatabaseReference mDatabase;
    TextView profile_name, profile_unit, profile_Bills, profile_family, unit_no, unit_bedrooms, unit_bathrooms, unit_parking;
    private String unitNo;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2)
    {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        profile = getView().findViewById(R.id.edProfile);
        profile_name = getView().findViewById(R.id.profile_name);
        profile_unit = getView().findViewById(R.id.profile_unit);
        profile_Bills = getView().findViewById(R.id.profile_txtBills);
        profile_family = getView().findViewById(R.id.profile_txtFamily);
        unit_no = getView().findViewById(R.id.unit_no);
        unit_bedrooms = getView().findViewById(R.id.unit_bedrooms);
        unit_bathrooms = getView().findViewById(R.id.unit_bathrooms);
        unit_parking = getView().findViewById(R.id.unit_parking);


        mDatabase = fCon.fDatabase.getReference();

        Query profileQuery = mDatabase;
        mDatabase.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String name = snapshot.child("user").child(MainActivity.userID).child("name").getValue(String.class);
                String surname = snapshot.child("user").child(MainActivity.userID).child("surname").getValue(String.class);
                unitNo = Integer.toString(snapshot.child("user").child(MainActivity.userID).child("unitNo").getValue(int.class));
                String fullName = name + " " + surname;
                profile_name.setText(getResources().getString(R.string.profile_name) + " " + fullName);
                profile_unit.setText(getResources().getString(R.string.profile_unit) + " " + unitNo);
                String family = "";
                if (snapshot.child("user").child(MainActivity.userID).child("family").exists()){
                    for (DataSnapshot snap : snapshot.child("user").child(MainActivity.userID).child("family").getChildren()){
                        family += snap.getValue(String.class) + "\n";
                    }
                }
                String bills = "";
                if (snapshot.child("user").child(MainActivity.userID).child("bills").exists()){
                    for (DataSnapshot snap : snapshot.child("user").child(MainActivity.userID).child("bills").getChildren()){
                        bills += "Type : " + snap.child("type").getValue(String.class) +
                                "\nAmount : " + snap.child("amount").getValue(double.class) +
                                "\nDate : " + snap.child("dateIssued").getValue(String.class) +
                                "\nPaid : " + snap.child("paid").getValue(boolean.class) + "\n\n";
                    }
                }
                profile_family.setText(family);
                profile_Bills.setText(bills);

                String bedrooms = Integer.toString(snapshot.child("unit").child(unitNo).child("bedrooms").getValue(int.class));
                String bathrooms = Integer.toString(snapshot.child("unit").child(unitNo).child("bathrooms").getValue(int.class));
                String parkingSpots = Integer.toString(snapshot.child("unit").child(unitNo).child("parkingSpots").getValue(int.class));
                unit_no.setText(getResources().getString(R.string.profile_unit) + " " + unitNo);
                unit_bedrooms.setText(getResources().getString(R.string.unit_bedrooms)+ " " + bedrooms);
                unit_bathrooms.setText(getResources().getString(R.string.unit_bathrooms)+ " " + bathrooms);
                unit_parking.setText(getResources().getString(R.string.unit_parking)+ " " + parkingSpots);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            { }
        });


        profile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));

            }
        });

    }

}
