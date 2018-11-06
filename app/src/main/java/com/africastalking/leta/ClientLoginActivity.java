package com.africastalking.leta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientLoginActivity extends AppCompatActivity {

    private Button msignUpBtn;
    private Button mSignInBtn;
    private EditText clientEmail, clientPassword;
    private FirebaseAuth mAuth;
    private View mLoginFormView;
    private View mProgressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        //initialize the firebaseauth instance
        mAuth = FirebaseAuth.getInstance();

        //initialize textinputs
        mLoginFormView = findViewById(R.id.client_login_form);
        mProgressView = findViewById(R.id.client_login_progress_bar);
        clientEmail = findViewById(R.id.client_login_email);
        clientPassword = findViewById(R.id.client_login_password);

        msignUpBtn = findViewById(R.id.btnClientSignUP);
        mSignInBtn = findViewById(R.id.btnClientSignIn);

        //set click listener
        msignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(ClientLoginActivity.this, ClientSignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        //set click listener to sign in button
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        //reset errors
        clientEmail.setError(null);
        clientPassword.setError(null);

        //get user input
        String email = clientEmail.getText().toString();
        String password = clientPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //validate form
        if (TextUtils.isEmpty(password)) {
            clientPassword.setError(getString(R.string.error_field_required));
            focusView = clientPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)){
            clientEmail.setError(getString(R.string.error_field_required));
            focusView = clientEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else {
            showProgress(true);
            loginUser(email, password);
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            showProgress(false);
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent loginIntent = new Intent(ClientLoginActivity.this, ClientMainActivity.class);
                            loginIntent.putExtra("client_id", user);
                            startActivity(loginIntent);
                        }else{
                            showProgress(false);
                            Toast.makeText(ClientLoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
