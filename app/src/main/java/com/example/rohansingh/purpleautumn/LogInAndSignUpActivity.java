package com.example.rohansingh.purpleautumn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInAndSignUpActivity extends AppCompatActivity {

    public ViewPager Slide;
    LinearLayout dotLayout;
    TextView[] mdots;
    Button gotoLoginPageButton;
    Button loginButton;
    Button gotoSignupPageButton;
    Button signUpButton;
    EditText EmailEditText;
    EditText passwordEditText;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog loginDialog;
    ProgressDialog signupDialog;
    String BloodGroup;
    String username;
    EditText usernameSignUP;
    String address;
    EditText addressSignUP;
    String phoneNumber;
    EditText phoneNumberEditText;
    String age;
    EditText ageEditText;
    String gender;
    EditText genderEditText;
    String EmailSignUP;
    EditText EmailSignUpEditText;
    String PasswordSignUP;
    EditText PasswordSignUPEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_and_sign_up);
        // code for setting up the view pager
        Slide=(ViewPager)findViewById(R.id.LogInViewPager);
        dotLayout=(LinearLayout)findViewById(R.id.DotsForViewPagerLinearLayout);
        sliderAdapter adapter = new sliderAdapter(this);
        Slide.setAdapter(adapter); // setting the adapter
        AddDotsIndicator(0);// for the sliding dots
        Slide.addOnPageChangeListener(viewListener);

        mAuth= FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("user");

        // code for the signup and login
        loginDialog=new ProgressDialog(LogInAndSignUpActivity.this);
        loginDialog.setTitle("Signing In");
        loginDialog.setMessage("please wait while we sign you in");
        loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loginDialog.setCanceledOnTouchOutside(false);
        EmailEditText=(EditText)findViewById(R.id.usernameEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        loginButton =(Button)findViewById(R.id.signINbutton);
        gotoSignupPageButton=(Button)findViewById(R.id.signUpButton);
        gotoLoginPageButton=(Button)findViewById(R.id.logInButton);

        //code for progress dialog during signing up
        signupDialog = new ProgressDialog(LogInAndSignUpActivity.this);
        signupDialog.setTitle("Signing up");
        signupDialog.setMessage("creating your account ...");
        signupDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        signupDialog.setCanceledOnTouchOutside(false);

        // listener for goto login button
        gotoLoginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout source = (LinearLayout)findViewById(R.id.loginHomeLayout);
                LinearLayout destination=(LinearLayout)findViewById(R.id.loginLayout);
                source.setVisibility(View.INVISIBLE);
                destination.setVisibility(View.VISIBLE);

            }
        });
        // listener for goto sign up button
        gotoSignupPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout source =(LinearLayout)findViewById(R.id.loginHomeLayout);
                LinearLayout destination =(LinearLayout)findViewById(R.id.signupLayout);
                source.setVisibility(View.INVISIBLE);
                destination.setVisibility(View.VISIBLE);
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = EmailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogInAndSignUpActivity.this, "error:EMPTY PASSWORD OR EMAIL", Toast.LENGTH_SHORT).show();
                } else {
                    loginDialog.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LogInAndSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                loginDialog.dismiss();
                            } else {
                                Toast.makeText(LogInAndSignUpActivity.this, "login falied:INVALID EMAIL OR PASSWORD", Toast.LENGTH_SHORT).show();
                                loginDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });


        Spinner bloodGroupSpinner = (Spinner)findViewById(R.id.bloodTypeSpinnerSignUP);
        final String[] bloodType ={"O+","O-","A+","A-","B+","B-","AB+","AB-"};
        ArrayAdapter<CharSequence>SpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.bloodType,android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        bloodGroupSpinner.setAdapter(SpinnerAdapter);
        bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                BloodGroup=bloodType[i];
                //Toast.makeText(LogInAndSignUpActivity.this,"you've selected "+BloodGroup, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        signUpButton =(Button)findViewById(R.id.signUPBUTTON);
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EmailSignUP = EmailSignUpEditText.getText().toString();
//                PasswordSignUP = PasswordSignUPEditText.getText().toString();
//                usernameSignUP=usernameSignUP.getText()
//
//            }
//        });
        // code for sign up
        usernameSignUP =(EditText)findViewById(R.id.signUPname);
        addressSignUP=(EditText)findViewById(R.id.signUPaddressEditText);
        phoneNumberEditText=(EditText)findViewById(R.id.signUPcontactEditText);
        //EmailEditText=(EditText)findViewById(R.id.signUPEmailEditText);
        ageEditText=(EditText)findViewById(R.id.signUPageEditText);
        genderEditText=(EditText)findViewById(R.id.signUPgenderEditText);
        EmailSignUpEditText=(EditText)findViewById(R.id.signUPEmailEditText);
        PasswordSignUPEditText=(EditText)findViewById(R.id.signUPpasswordEditText);


        signUpButton =(Button)findViewById(R.id.signUPBUTTON);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmailSignUP = EmailSignUpEditText.getText().toString();
                PasswordSignUP = PasswordSignUPEditText.getText().toString();
                username = usernameSignUP.getText().toString();
                address = addressSignUP.getText().toString();
                phoneNumber = phoneNumberEditText.getText().toString();
                age = ageEditText.getText().toString();
                gender = genderEditText.getText().toString();

                if (EmailSignUP.isEmpty() || PasswordSignUP.isEmpty()) {
                    Toast.makeText(LogInAndSignUpActivity.this, "INVALID EMAIL AND PASSWORD", Toast.LENGTH_SHORT).show();
                }
                else if (username.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || age.isEmpty() || gender.isEmpty()){

                    Toast.makeText(LogInAndSignUpActivity.this, "INCOMPLETE DATA, ALL FIELDS ARE NECESSARY", Toast.LENGTH_SHORT).show();
                }
                else {
                    signupDialog.show();
                    mAuth.createUserWithEmailAndPassword(EmailSignUP, PasswordSignUP).addOnCompleteListener(LogInAndSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                // creating reference for database
                                String currentUser = mAuth.getCurrentUser().getUid();
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(BloodGroup).child(currentUser);
                                mDatabase.child("username").setValue(username);
                                mDatabase.child("age").setValue(age);
                                mDatabase.child("contact").setValue(phoneNumber);
                                mDatabase.child("gender").setValue(gender);
                                mDatabase.child("address").setValue(address);
                                mDatabase.child("email").setValue(EmailSignUP);
                                signupDialog.dismiss();

                            }
                        }
                    });


                }
            }
        });

    }
    public void AddDotsIndicator(int position){

        mdots=new TextView[3];
        dotLayout.removeAllViews();
        for(int i=0;i<mdots.length;i++) {

            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(45);
            mdots[i].setTextColor(getResources().getColor(R.color.fadedWhite));

            dotLayout.addView(mdots[i]);
        }
            if(mdots.length>0){
                mdots[position].setTextColor(getResources().getColor(R.color.pureWhite));
            }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            AddDotsIndicator(position);
            //Toast.makeText(LogInAndSignUpActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
