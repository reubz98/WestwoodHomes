package com.example.westwoodhomes.admin.ui.residents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.westwoodhomes.R;
import com.example.westwoodhomes.fCon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResidentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResidentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference mDatabase;
    SimpleAdapter sa;
    ListView residents;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResidentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResidentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResidentFragment newInstance(String param1, String param2) {
        ResidentFragment fragment = new ResidentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resident, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = fCon.fDatabase.getReference();
        residents = getView().findViewById(R.id.lsvResidents);
        displayResidents();
    }

    private void displayResidents(){
        Query residentsQuery = mDatabase.child("user").orderByChild("isAdmin").equalTo(false);
        residentsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HashMap<String,String>> res = new ArrayList<>();
                if (snapshot.exists()){
                    for (DataSnapshot item : snapshot.getChildren()){
                        HashMap<String,String> hash = new HashMap<>();
                        hash.put("username",item.child("username").getValue(String.class));
                        hash.put("name",item.child("name").getValue(String.class));
                        hash.put("surname",item.child("surname").getValue(String.class));
                        hash.put("unit",Double.toString(item.child("unitNo").getValue(double.class)));
                        String strFam = "";
                        if (item.child("family").exists()){
                            for (DataSnapshot fam : item.child("family").getChildren()){
                                strFam += fam.getValue() + "\n";
                            }
                        }
                        String strBill = "";
                        if (item.child("bills").exists()){
                            for (DataSnapshot bill : item.child("bills").getChildren()){
                                double amount = bill.child("amount").getValue(double.class);
                                String date = bill.child("dateIssued").getValue(String.class);
                                boolean paid = bill.child("paid").getValue(boolean.class);
                                String type = bill.child("type").getValue(String.class);
                                strBill += "Type : " + type + "\n" +
                                        "Amount : " + amount + "\n" +
                                        "Date : " + date + "\n" +
                                        "Paid : " + paid + "\n\n";
                            }
                        }
                        hash.put("family", strFam);
                        hash.put("bills", strBill);
                        res.add(hash);
                    }
                    sa = new SimpleAdapter(getContext(), res, R.layout.resident_display,
                            new String[]{"username","name","surname","unitNo","family","bills"},
                            new int[]{R.id.res_username,R.id.res_name, R.id.res_surname,
                                    R.id.res_unit, R.id.res_Family, R.id.res_Bills});
                    residents.setAdapter(sa);
                    setListViewHeightBasedOnChildren(residents);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        Log.e("Listview Size ", "" + listView.getCount());
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {

            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
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
