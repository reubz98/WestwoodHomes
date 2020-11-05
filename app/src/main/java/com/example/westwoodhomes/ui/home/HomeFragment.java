package com.example.westwoodhomes.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.westwoodhomes.MainActivity;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment
{
    TextView tvNews;
    DatabaseReference mDatabase;
    ListView bills;
    SimpleAdapter sa;

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
        bills = getView().findViewById(R.id.lsvBills);

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
        displayBill();
    }

    public void displayBill(){
        Query billQuery = mDatabase.child("user").child(MainActivity.userID).child("bills");
        billQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HashMap<String,String>> bill = new ArrayList<>();
                if (snapshot.exists()){
                    for (DataSnapshot item : snapshot.getChildren()){
                        HashMap<String,String> hash = new HashMap<>();
                        hash.put("amount","Amount : " + item.child("amount").getValue(double.class).toString());
                        hash.put("date","Date : " + item.child("dateIssued").getValue(String.class));
                        hash.put("paid","Paid : " + item.child("paid").getValue(boolean.class).toString());
                        hash.put("type","Type : " + item.child("type").getValue(String.class));
                        bill.add(hash);
                    }
                    sa = new SimpleAdapter(getContext(), bill, R.layout.bill_display,
                            new String[]{"amount","date","paid","type"},
                            new int[]{R.id.dis_bill_amount,R.id.dis_bill_date, R.id.dis_bill_paid,
                            R.id.dis_bill_type});
                    bills.setAdapter(sa);
                    setListViewHeightBasedOnChildren(bills);
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
