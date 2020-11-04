package com.example.westwoodhomes.ui.review;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.westwoodhomes.Complaint;
import com.example.westwoodhomes.R;
import com.example.westwoodhomes.Review;
import com.example.westwoodhomes.fCon;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment
{
    Button SubmitRev, SubmitCom;
    EditText edtName, edtUnitNo, edtRating, edtReview, edtType, edtDes;
    DatabaseReference mDatabase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2)
    {
        ReviewFragment fragment = new ReviewFragment();
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
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        SubmitRev = getView().findViewById(R.id.btnSubmitRev);
        SubmitCom = getView().findViewById(R.id.btnSubmitCom);
        edtName = getView().findViewById(R.id.edtName);
        edtUnitNo = getView().findViewById(R.id.edtUnitNo);
        edtRating = getView().findViewById(R.id.edtRating);
        edtReview = getView().findViewById(R.id.edtReview);
        edtType = getView().findViewById(R.id.edtType);
        edtDes = getView().findViewById(R.id.edtDes);

        mDatabase = fCon.fDatabase.getReference();

        SubmitRev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                submitReview();
            }

        });

        SubmitCom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                submitComplaint();
            }
        });

    }
    public void submitReview()
    {

        String name = edtName.getText().toString();
        int unit = Integer.parseInt(edtUnitNo.getText().toString());
        int rating = Integer.parseInt(edtRating.getText().toString());
        String Body = edtReview.getText().toString();

        Review review = new Review(Body, name, rating, unit);

        mDatabase.child("review").push().setValue(review);
        edtName.setText("");
        edtUnitNo.setText(0);
        edtRating.setText(0);
        edtReview.setText("");

    }

    public void submitComplaint()
    {
        String type = edtType.getText().toString();
        String descrip = edtDes.getText().toString();

        Complaint complaint = new Complaint(type, descrip);

        mDatabase.child("complaint").push().setValue(complaint);
        edtType.setText("");
        edtDes.setText("");
    }
}
