package com.africastalking.leta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClientLoginActivity extends AppCompatActivity {

    private Button msignUpBtn;
    private Button mSignInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        msignUpBtn = findViewById(R.id.btnSignUpClient);
        mSignInBtn = findViewById(R.id.btSignIn);

        //set click listener
        msignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(ClientLoginActivity.this, ClientMainActivity.class);
                startActivity(signUpIntent);
            }
        });

        //set click listener to sign in button
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(ClientLoginActivity.this, ClientMainActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}
