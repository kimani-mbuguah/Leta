package com.africastalking.leta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ClientMainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private MyMealsFragment myMealsFragment;
    private MyOrdersFragment myOrdersFragment;
    private android.support.v7.widget.Toolbar clientMainToolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        //initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        //check if a user is logged in
        if (mAuth.getCurrentUser() == null) {
            sendToStart();
        }

        clientMainToolbar = findViewById(R.id.client_main_toolbar);
        setSupportActionBar(clientMainToolbar);
        getSupportActionBar().setTitle("Leta");

        homeFragment = new HomeFragment();
        myMealsFragment = new MyMealsFragment();
        myOrdersFragment = new MyOrdersFragment();

        initializeFragment();
        
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        replaceFragment(homeFragment, currentFragment);
                        return true;

                    case R.id.navigation_meals:
                        replaceFragment(myMealsFragment, currentFragment);
                        return true;

                    case R.id.navigation_orders:
                        replaceFragment(myOrdersFragment, currentFragment);
                        return true;

                    default:
                        return false;
                }
            }

        });
    }

    private void sendToStart() {
        Intent welcomeIntent = new Intent(ClientMainActivity.this, WelcomeActivity.class);
        startActivity(welcomeIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:
                logOut();
                return true;

            default:
                return false;


        }

    }

    private void logOut() {
        mAuth.signOut();
        sendToStart();
    }


    private void initializeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.main_container, homeFragment);
        fragmentTransaction.add(R.id.main_container, myMealsFragment);
        fragmentTransaction.add(R.id.main_container, myOrdersFragment);

        fragmentTransaction.hide(myMealsFragment);
        fragmentTransaction.hide(myOrdersFragment);

        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment, Fragment currentFragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(fragment == homeFragment){

            fragmentTransaction.hide(myMealsFragment);
            fragmentTransaction.hide(myOrdersFragment);

        }

        if(fragment == myMealsFragment){

            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(myOrdersFragment);

        }

        if(fragment == myOrdersFragment){

            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(myMealsFragment);

        }
        fragmentTransaction.show(fragment);

        //fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();

    }



}
