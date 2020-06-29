package com.example.rohansingh.purpleautumn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.zip.Inflater;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class ListOfDonorActivity extends AppCompatActivity {

    TextView BloodHeading;
    RecyclerView ListOfUsers;
    FirebaseRecyclerAdapter FirebaseAdapter;
    String donorBloodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_donor);
        BloodHeading=(TextView)findViewById(R.id.listOfDonorBloodTypeTextView);
        String BloodType=getIntent().getStringExtra("BloodType");
        donorBloodType=BloodType;
        BloodHeading.setText(BloodType);
        Toast.makeText(this, "form the list activity"+BloodType, Toast.LENGTH_SHORT).show();

        ListOfUsers=(RecyclerView)findViewById(R.id.DonorListRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListOfUsers.setLayoutManager(manager);
        Query mQuery = FirebaseDatabase.getInstance().getReference().child("Users").child(BloodType);
        mQuery.keepSynced(true);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<DonorList>().setQuery(mQuery,DonorList.class).build();

         FirebaseAdapter = new FirebaseRecyclerAdapter<DonorList,DonorViewHolder>(options) {
            @NonNull
            @Override
            public DonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.donor_list_item,parent,false);
                return new DonorViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DonorViewHolder holder, int position, @NonNull DonorList model) {

                holder.setUsername(model.getUsername());
                holder.setContact(model.getContact());
                final String user = getRef(position).getKey();
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ListOfDonorActivity.this, user, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),DonorsProfileActivity.class);
                        intent.putExtra("BloodType",donorBloodType);
                        intent.putExtra("UserId",user);
                        startActivity(intent);

                    }
                });
            }
        };



        ListOfUsers.setAdapter(FirebaseAdapter);
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public DonorViewHolder(View itemView) {
            super(itemView);
            mView =itemView;
        }
        public void setUsername(String name){
            TextView DonorName=(TextView)mView.findViewById(R.id.donorNameTextView);
            DonorName.setText(name);

        }
        public void setContact(String contact){
            TextView userContact =(TextView)mView.findViewById(R.id.donorContactTextView);
            userContact.setText(contact);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAdapter.startListening();
    }
}
