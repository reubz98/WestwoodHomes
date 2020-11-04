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
    TextView profile_name, profile_unit, profile_Bills;
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
        profile_Bills = getView().findViewById(R.id.profile_Bills);


        mDatabase = fCon.fDatabase.getReference();
        Query profileQuery = mDatabase.child("user").child(MainActivity.userID);
        profileQuery.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                List <String> list = new ArrayList<>();
                for (DataSnapshot sn : snapshot.getChildren())
                {
                   String name = sn.child("name").getValue(String.class);
                   String surname = sn.child("surname").getValue(String.class);
                   String unitNo = Integer.toString(sn.child("unitNo").getValue(int.class));


                   String fullName = name + " " + surname;
                   profile_name.setText(getResources().getString(R.string.profile_name) + " " + fullName);
                   profile_unit.setText(getResources().getString(R.string.profile_unit) + " " + unitNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
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
