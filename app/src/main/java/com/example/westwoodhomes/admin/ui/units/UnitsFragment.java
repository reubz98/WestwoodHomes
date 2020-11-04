package com.example.westwoodhomes.admin.ui.units;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.westwoodhomes.R;
import com.example.westwoodhomes.Unit;
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
 * Use the {@link UnitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnitsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatabaseReference mDatabase;
    ListView units;
    EditText unitNo, bedrooms, bathrooms, parking;
    Button add;
    SimpleAdapter sa;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UnitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnitsFragment newInstance(String param1, String param2) {
        UnitsFragment fragment = new UnitsFragment();
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
        return inflater.inflate(R.layout.fragment_units, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        units = getView().findViewById(R.id.lsvUnits);
        unitNo = getView().findViewById(R.id.edit_unit_number);
        bedrooms = getView().findViewById(R.id.edit_unit_bedrooms);
        bathrooms = getView().findViewById(R.id.edit_unit_bathrooms);
        parking = getView().findViewById(R.id.edit_unit_parking);
        add = getView().findViewById(R.id.btnAddUnit);
        mDatabase = fCon.fDatabase.getReference();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUnit();
            }
        });
        displayUnits();
    }

    public void addUnit(){
        final int unitNo = Integer.parseInt(this.unitNo.getText().toString());
        final int bedrooms = Integer.parseInt(this.bedrooms.getText().toString());
        final int bathrooms = Integer.parseInt(this.bathrooms.getText().toString());
        final int parking = Integer.parseInt(this.parking.getText().toString());
        Unit unit = new Unit(unitNo, bedrooms, bathrooms, parking);
        mDatabase.child("unit").push().setValue(unit);
    }

    public void displayUnits(){

        Query unitsQuery = mDatabase.child("unit").orderByChild("unitNo");
        unitsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HashMap<String,String>> unit = new ArrayList<HashMap<String, String>>();
                if (snapshot.exists()){
                    for (DataSnapshot item : snapshot.getChildren()){
                        HashMap<String, String> hash = new HashMap<String, String>();
                        hash.put("unitNo", getResources().getString(R.string.unit_no) + item.child("unitNo").getValue(Integer.class));
                        hash.put("bedrooms", getResources().getString(R.string.unit_bedrooms) +  item.child("bedrooms").getValue(Integer.class));
                        hash.put("bathrooms", getResources().getString(R.string.unit_bathrooms) +  item.child("bathrooms").getValue(Integer.class));
                        hash.put("parking", getResources().getString(R.string.unit_parking) +  item.child("parkingSpots").getValue(Integer.class));
                        unit.add(hash);
                    }
                    sa = new SimpleAdapter(getContext(), unit, R.layout.unit_display,
                            new String[]{"unitNo","bedrooms","bathrooms", "parking"},
                            new int[]{R.id.dis_unit_no, R.id.dis_bedrooms, R.id.dis_bathrooms, R.id.dis_parking});
                    units.setAdapter(sa);
                    setListViewHeightBasedOnChildren(units);
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