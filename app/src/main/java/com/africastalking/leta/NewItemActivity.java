package com.africastalking.leta;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class NewItemActivity extends AppCompatActivity {
    private ImageView newItemImage;
    private EditText mItemName;
    private EditText mItemDesc;
    private Button addNewItemBtn;
    private android.support.v7.widget.Toolbar addNewitemToolbar;
    private View mProgressView;
    private Uri postImageUri = null;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private StorageReference mStorageReference;
    private Bitmap compressedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        //toolbar
        addNewitemToolbar = findViewById(R.id.add_item_toolbar);
        setSupportActionBar(addNewitemToolbar);
        getSupportActionBar().setTitle("Leta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase
        mStorageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();


        //initialize text inputs
        newItemImage = findViewById(R.id.new_item_image);
        mProgressView = findViewById(R.id.add_item_progress_bar);
        mItemName = findViewById(R.id.new_item_name);
        mItemDesc = findViewById(R.id.new_item_desc);
        addNewItemBtn = findViewById(R.id.btnAddNewItem);

        //upload image
        newItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(NewItemActivity.this);
            }
        });

        addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewItemActivity.this, "item added successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
