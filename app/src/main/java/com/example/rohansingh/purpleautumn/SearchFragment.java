package com.example.rohansingh.purpleautumn;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends android.app.Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    FeatureCoverFlow mCoverFlow;
    CoverflowAdapter coverflowAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // code for cover flow

        mCoverFlow = (FeatureCoverFlow)view.findViewById(R.id.coverflow);
        coverflowAdapter = new CoverflowAdapter(getContext());
        mCoverFlow.setAdapter(coverflowAdapter);
        final String[] BloodTypes={"O+","O-","A+","A-","B+","B-","AB+","AB-"};
        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), BloodTypes[i], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),ListOfDonorActivity.class);
                intent.putExtra("BloodType",BloodTypes[i]);
                startActivity(intent);
            }
        });
                return view;
    }

}
