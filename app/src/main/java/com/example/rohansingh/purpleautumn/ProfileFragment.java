package com.example.rohansingh.purpleautumn;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.app.Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    TextView username;
    TextView bloodType;
    TextView email;
    TextView contact;
    TextView address;
    TextView logoutbutton;
    TextView editProfileButton;
    String user;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        if(FirebaseAuth.getInstance()!=null)
        mAuth = FirebaseAuth.getInstance();
        username = (TextView)view.findViewById(R.id.profileUsername);
        bloodType=(TextView)view.findViewById(R.id.profileBloodType);
        email=(TextView)view.findViewById(R.id.profileEmail);
        contact =(TextView)view.findViewById(R.id.profileContact);
        address=(TextView)view.findViewById(R.id.profileAddress);
        logoutbutton=(TextView)view.findViewById(R.id.logoutTextView);
        editProfileButton=(TextView)view.findViewById(R.id.editProfileTextView);
        try {
            user = mAuth.getCurrentUser().getUid();
        }
        catch (Exception e){ e.printStackTrace();}
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot testshot : dataSnapshot.getChildren()){

//                    String h = testshot.getKey().toString();
//                    String k = testshot.toString();
                    if(testshot.hasChild(user)){

                        username.setText(testshot.child(user).child("username").getValue().toString());
                        bloodType.setText(testshot.getKey().toString());
                        email.setText(testshot.child(user).child("email").getValue().toString());
                        contact.setText(testshot.child(user).child("contact").getValue().toString());
                        address.setText(testshot.child(user).child("address").getValue().toString());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
