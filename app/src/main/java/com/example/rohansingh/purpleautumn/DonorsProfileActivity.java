package com.example.rohansingh.purpleautumn;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonorsProfileActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    TextView tname;
    TextView tcontact;
    TextView taddress;
    TextView tbloodGroup;
    TextView temail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors_profile);

        String BloodGroup = getIntent().getStringExtra("BloodType");
        String userID=getIntent().getStringExtra("UserId");

        tname=(TextView)findViewById(R.id.DonorProfileName);
        tcontact =(TextView)findViewById(R.id.DonorProfileContact);
        taddress= (TextView)findViewById(R.id.DonorProfileAddress);
        tbloodGroup =(TextView)findViewById(R.id.DonorProfileBloodGroup);
        temail=(TextView)findViewById(R.id.DonorProfileEmail);

        tbloodGroup.setText(BloodGroup);


        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users")
                .child(BloodGroup).child(userID);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String contact=dataSnapshot.child("contact").getValue().toString();
                String name=dataSnapshot.child("username").getValue().toString();
                String address=dataSnapshot.child("address").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String age=dataSnapshot.child("age").getValue().toString();
                String gender=dataSnapshot.child("gender").getValue().toString();

                tname.setText(name);
                tcontact.setText(contact);
                taddress.setText(address);
                temail.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
}
