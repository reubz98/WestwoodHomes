package com.example.westwoodhomes.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.westwoodhomes.MainActivity;
import com.example.westwoodhomes.R;
import com.example.westwoodhomes.fCon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment
{
    TextView tvNews, tvRent, tvUtil, tvNet;
    DatabaseReference mDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        tvNews = getView().findViewById(R.id.tvNews);
        tvRent = getView().findViewById(R.id.tvRent);
        tvUtil = getView().findViewById(R.id.tvUtil);
        tvNet = getView().findViewById(R.id.tvNet);

        tvNews.setMovementMethod(new ScrollingMovementMethod());

        mDatabase = fCon.fDatabase.getReference();

        Query newsQuery = mDatabase.child("news");

        newsQuery.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String display = "";
                for (DataSnapshot snap : snapshot.getChildren())
                {
                    String title = snap.child("title").getValue(String.class);
                    String content = snap.child("content").getValue(String.class);
                    display += title + "\n"+content+ "\n\n";
                }
                tvNews.setText(display);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
        Query billQ = mDatabase.child("user").child(MainActivity.userID).child("bills");

        billQ.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String dis ="";
                for (DataSnapshot sn : snapshot.getChildren())
                {

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
