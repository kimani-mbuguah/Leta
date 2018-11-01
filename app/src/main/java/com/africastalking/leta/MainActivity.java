package com.africastalking.leta;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Dialog mDialog;
    private RecyclerView homeRV;
    ArrayList<ModelHomeContent> foodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        foodsList = new ArrayList<>();
        foodsList.add(new ModelHomeContent(R.drawable.bg1,"Managu Mingi","Kibandaski", "KSH: 200"));
        foodsList.add(new ModelHomeContent(R.drawable.bg,"Beans Chapo","Kibandaski", "KSH: 250"));
        foodsList.add(new ModelHomeContent(R.drawable.bg1,"Kichwa Tamu","KFC", "KSH: 500"));
        foodsList.add(new ModelHomeContent(R.drawable.bg,"Supu ya Miguu","Java House", "KSH: 100"));
        foodsList.add(new ModelHomeContent(R.drawable.bg1,"Ugali Matumbo","Kibanda 2", "KSH: 300"));
        foodsList.add(new ModelHomeContent(R.drawable.bg,"Rice Beans","Java House", "KSH: 50"));

        homeRV = findViewById(R.id.homeRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager((this));
        RecyclerView.LayoutManager homeLinearLayoutManager = layoutManager;
        homeRV.setLayoutManager(homeLinearLayoutManager);

        homeContentAdapter homeContentAdapter = new homeContentAdapter(this, foodsList);
        homeRV.setAdapter(homeContentAdapter);

        mDialog = new Dialog(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myCartIntent = new Intent(MainActivity.this,MyCartActivity.class);
                startActivity(myCartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void ViewItem(View v){
        TextView txtclose;
        Button btnAddToCart;
        mDialog.setContentView(R.layout.item_popup);
        txtclose =(TextView) mDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        final ElegantNumberButton addQuantityButton = mDialog.findViewById(R.id.addQuantityBtn);

        addQuantityButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = addQuantityButton.getNumber();
            }
        });
        btnAddToCart  = (Button) mDialog.findViewById(R.id.btnAddToCart);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
