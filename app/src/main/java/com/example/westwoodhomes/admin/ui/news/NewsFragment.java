package com.example.westwoodhomes.admin.ui.news;

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
import com.example.westwoodhomes.fCon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText title, content;
    Button add;
    DatabaseReference mDatabase;
    ListView newsList;
    SimpleAdapter sa;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2)
    {
        NewsFragment fragment = new NewsFragment();
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = fCon.fDatabase.getReference();
        title = getView().findViewById(R.id.news_title);
        content = getView().findViewById(R.id.news_content);
        newsList = getView().findViewById(R.id.lsvNews);
        add = getView().findViewById(R.id.news_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNews();
            }
        });
        displayNews();
    }

    public void addNews()
    {
        String title = this.title.getText().toString();
        String content = this.content.getText().toString();
        News news = new News(title, content);
        mDatabase.child("news").push().setValue(news);
    }

    public void displayNews()
    {
        Query newsQuery = mDatabase.child("news");
        newsQuery.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                List<HashMap<String,String>> news = new ArrayList<>();
                if (snapshot.exists())
                {
                    for (DataSnapshot item : snapshot.getChildren()){
                        HashMap<String,String> hash = new HashMap<>();
                        hash.put("title", item.child("title").getValue(String.class));
                        hash.put("content", item.child("content").getValue(String.class));
                        news.add(hash);
                    }
                    sa = new SimpleAdapter(getContext(), news, R.layout.news_display,
                            new String[]{"title", "content"},
                            new int[]{R.id.dis_news_title, R.id.dis_news_content});
                    newsList.setAdapter(sa);
                    setListViewHeightBasedOnChildren(newsList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
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

class News{
    private String title;
    private String content;

    public News(){

    }

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
