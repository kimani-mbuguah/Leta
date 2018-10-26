package com.africastalking.leta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectUserActivity extends AppCompatActivity {

    private Button mClientBtn;
    private Button mVendorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        mClientBtn = findViewById(R.id.clientBtn);
        mVendorBtn = findViewById(R.id.vendorBtn);

        mClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clientLoginIntent = new Intent(SelectUserActivity.this, ClientLoginActivity.class);
                startActivity(clientLoginIntent);
            }
        });

        mVendorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vendorLoginIntent = new Intent(SelectUserActivity.this, VendorLoginActivity.class);
                startActivity(vendorLoginIntent);
            }
        });
    }
}
