package com.africastalking.leta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    LinearLayout l1,l2;
    Button getStartedBtn;
    Animation uptodown,downtoup;

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent sendToMainIntent = new Intent(WelcomeActivity.this, ClientMainActivity.class);
            startActivity(sendToMainIntent);
        }

        getStartedBtn = findViewById(R.id.buttonGetStarted);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        //set click listener on get started button
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectUserIntent = new Intent(WelcomeActivity.this, SelectUserActivity.class);
                startActivity(selectUserIntent);
            }
        });
    }
}
