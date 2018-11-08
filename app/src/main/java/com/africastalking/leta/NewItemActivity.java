package com.africastalking.leta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class NewItemActivity extends AppCompatActivity {
    private ImageView newItemImage;
    private EditText mItemName;
    private EditText mItemDesc;
    private Button addNewItemBtn;
    private android.support.v7.widget.Toolbar addNewitemToolbar;
    private View mProgressView;
    private Uri itemImageUri = null;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private StorageReference mStorageReference;
    private Bitmap compressedImageFile;
    private static final int GALLERY_PICK = 1;


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
                final String desc = mItemDesc.getText().toString();
                final String item_name = mItemName.getText().toString();
                if(!TextUtils.isEmpty(desc) && !TextUtils.isEmpty(item_name) && itemImageUri != null){
                    showProgress(true);
                    final String randomName = UUID.randomUUID().toString();

                    //image upload
                    File newImageFile = new File(itemImageUri.getPath());
                    try {

                        compressedImageFile = new Compressor(NewItemActivity.this)
                                .setMaxHeight(720)
                                .setMaxWidth(720)
                                .setQuality(50)
                                .compressToBitmap(newImageFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();

                    UploadTask filePath = mStorageReference.child("item_images").child(randomName + ".jpg").putBytes(imageData);
                    filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            final String downloadUri = task.getResult().toString();
                            if (task.isSuccessful()){
                                File newThumbFile = new File(itemImageUri.getPath());
                                try {

                                    compressedImageFile = new Compressor(NewItemActivity.this)
                                            .setMaxHeight(100)
                                            .setMaxWidth(100)
                                            .setQuality(1)
                                            .compressToBitmap(newThumbFile);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] thumbData = baos.toByteArray();

                                UploadTask uploadTask = mStorageReference.child("item_images/thumbs")
                                        .child(randomName + ".jpg").putBytes(thumbData);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        String downloadthumbUri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                                        //String downloadthumbUri = taskSnapshot.getDownloadUrl().toString();

                                        Map<String, Object> imagesMap = new HashMap<>();
                                        imagesMap.put("image_url", downloadUri);
                                        imagesMap.put("image_thumb", downloadthumbUri);
                                        imagesMap.put("item_name", item_name);
                                        imagesMap.put("item_desc", desc);
                                        imagesMap.put("timestamp", FieldValue.serverTimestamp());



                                        firebaseFirestore.collection("Menu").add(imagesMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                                if(task.isSuccessful()){

                                                    Toast.makeText(NewItemActivity.this, "Item Added Successfully", Toast.LENGTH_LONG).show();
                                                   /* Intent mainIntent = new Intent(NewPostActivity.this, MainActivity.class);
                                                    startActivity(mainIntent);
                                                    finish();*/

                                                } else {
                                                    Toast.makeText(NewItemActivity.this, task.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }

                                                showProgress(false);

                                            }
                                        });



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(NewItemActivity.this, "An Unknown Error Occurred", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    });


                }else{
                    Toast.makeText(NewItemActivity.this, "Something's wrong",Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                itemImageUri = result.getUri();
                newItemImage.setImageURI(itemImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

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
        }
    }
}
