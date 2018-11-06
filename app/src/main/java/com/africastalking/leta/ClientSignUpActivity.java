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

public class ClientSignUpActivity extends AppCompatActivity {
    private EditText clientName, clientEmail, clientPhone, clientPass, clientPassConfirm;
    private FirebaseAuth mAuth;
    private Button msignUpBtn;
    private View mLoginFormView;
    private View mProgressView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_up);

        //initialize firebaseAuth instance

        mAuth = FirebaseAuth.getInstance();
        //initialize fields

        clientName = findViewById(R.id.client_reg_name);
        clientEmail =findViewById(R.id.client_reg_email);
        clientPhone = findViewById(R.id.client_reg_phone);
        clientPass = findViewById(R.id.client_reg_pass);
        clientPassConfirm = findViewById(R.id.client_reg_passconfirm);
        msignUpBtn = findViewById(R.id.btnClientSignUP);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress_bar);

        //set click listener for sign up button and call method to attempt login

        msignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        // Reset errors.
        clientName.setError(null);
        clientEmail.setError(null);
        clientPhone.setError(null);
        clientPass.setError(null);
        clientPassConfirm.setError(null);

        String name = clientName.getText().toString();
        String phone =  clientPhone.getText().toString();
        String email = clientEmail.getText().toString();
        String password = clientPass.getText().toString();
        String passwordConfirm = clientPassConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            clientPass.setError(getString(R.string.error_invalid_password));
            focusView = clientPass;
            cancel = true;
        }


        if (TextUtils.isEmpty(passwordConfirm)) {
            clientPassConfirm.setError(getString(R.string.error_invalid_password));
            focusView = clientPassConfirm;
            cancel = true;
        }

        if (!isPasswordValid(passwordConfirm)){
            clientPass.setError(getString(R.string.error_invalid_password));
            focusView = clientPass;
            cancel = true;
        }

        if (!isPasswordValid(password)){
            clientPass.setError(getString(R.string.error_invalid_password));
            focusView = clientPass;
            cancel = true;
        }

        if (!password.equals(passwordConfirm)){
            clientPassConfirm.setError("Passwords must be the same");
            focusView = clientPassConfirm;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            clientEmail.setError(getString(R.string.error_field_required));
            focusView = clientEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            clientEmail.setError(getString(R.string.error_invalid_email));
            focusView = clientEmail;
            cancel = true;
        }

        //check for a valid name
        if (TextUtils.isEmpty(name)){
            clientName.setError(getString(R.string.error_field_required));
            focusView = clientName;
        }

        //check for a valid phone number
        if(TextUtils.isEmpty(phone)){
            clientPhone.setError(getString(R.string.error_field_required));
            focusView = clientPhone;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            createUser(name, email, phone, password);

        }
    }

    private void createUser(String name, String email, String phone, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //signed in successfully
                            Toast.makeText(ClientSignUpActivity.this,"Account created successfully",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent clientMainActivityIntent = new Intent(ClientSignUpActivity.this, ClientMainActivity.class);
                            clientMainActivityIntent.putExtra("client_id", user);
                            startActivity(clientMainActivityIntent);
                            showProgress(false);
                        }else{
                            Toast.makeText(ClientSignUpActivity.this, "Action unsuccessful. Check your internet connection and try again", Toast.LENGTH_LONG).show();
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

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
