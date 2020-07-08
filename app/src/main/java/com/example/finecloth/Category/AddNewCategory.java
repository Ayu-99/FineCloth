package com.example.finecloth.Category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.finecloth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AddNewCategory extends AppCompatActivity {


    EditText editTextName,editTextDesc;
    private FirebaseFirestore db;
    final int image_req=1;
    Button addCategory;
    ArrayAdapter arrayAdapter;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);
        db=FirebaseFirestore.getInstance();
        editTextName=findViewById(R.id.editTextName);
        editTextDesc=findViewById(R.id.editTextDescription);

        addCategory=findViewById(R.id.addCategory);





        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference collectionReference=db.collection("Category");
                category c=new category(
                        editTextName.getText().toString(),
                        editTextDesc.getText().toString()
                );


                collectionReference.add(c).addOnSuccessListener(
                        new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddNewCategory.this, "Category added", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewCategory.this, "Category not added", Toast.LENGTH_SHORT).show();
                    }
                });


                editTextName.getText().clear();
                editTextDesc.getText().clear();

            }
        });


    }
}
