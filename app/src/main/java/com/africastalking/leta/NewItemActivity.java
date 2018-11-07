package com.africastalking.leta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

public class NewItemActivity extends AppCompatActivity {
    private ImageView newItemImage;
    private EditText mItemName;
    private EditText mItemDesc;
    private Button addNewItemBtn;
    private android.support.v7.widget.Toolbar addNewitemToolbar;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        //toolbar
        addNewitemToolbar = findViewById(R.id.add_item_toolbar);
        setSupportActionBar(addNewitemToolbar);
        getSupportActionBar().setTitle("Leta");

        //initialize text inputs
        newItemImage = findViewById(R.id.new_item_image);
        mProgressView = findViewById(R.id.add_item_progress_bar);
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
