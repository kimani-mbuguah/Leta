package com.africastalking.leta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ClientMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private HomeFragment homeFragment;
    private MyMealsFragment myMealsFragment;
    private MyOrdersFragment myOrdersFragment;
    private android.support.v7.widget.Toolbar clientMainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        clientMainToolbar = findViewById(R.id.client_main_toolbar);
        setSupportActionBar(clientMainToolbar);

        getSupportActionBar().setTitle("Tule");

        mTextMessage = (TextView) findViewById(R.id.message);

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
