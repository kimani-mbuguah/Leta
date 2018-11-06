package com.africastalking.leta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewItemActivity extends AppCompatActivity {
    private EditText mItemName;
    private EditText mItemDesc;
    private Button addNewItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        //initialize text inputs
        mItemName = findViewById(R.id.new_item_name);
        mItemDesc = findViewById(R.id.new_item_desc);
        addNewItemBtn = findViewById(R.id.btnAddNewItem);

        addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewItemActivity.this, "item added successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
