package com.example.rohansingh.purpleautumn;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthstateListner;
    BottomNavigationView NavigationBar;
    FrameLayout mainFrame;
    FeedFragment feedFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrame= (FrameLayout)findViewById(R.id.mainFrameLayout);
        feedFragment = new FeedFragment();
        profileFragment= new ProfileFragment();
        searchFragment= new SearchFragment();

        mAuth = FirebaseAuth.getInstance();
        mAuthstateListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null)
                {
                    Intent intent;
                    intent = new Intent(MainActivity.this,LogInAndSignUpActivity.class);
                    startActivity(intent);
                }
            }
        };

        getFragmentManager().beginTransaction().add(R.id.mainFrameLayout,profileFragment).commit();
        NavigationBar=(BottomNavigationView)findViewById(R.id.navigationBar);
        NavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:
                        changeFragment(feedFragment);
                        return true;
                    case R.id.profileicon:
                        changeFragment(profileFragment);
                        return true;
                    case R.id.searchicon:
                        changeFragment(searchFragment);
                        return true;
                    default:
                        return false;
                }

            }
        });
    }
    void changeFragment(Fragment fragment){

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout,fragment);
        transaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthstateListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthstateListner!=null)
            mAuth.removeAuthStateListener(mAuthstateListner);
    }
}

