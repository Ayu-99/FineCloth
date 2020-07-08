package com.example.finecloth.Item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finecloth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UpdateItem extends AppCompatActivity {

    EditText editTextName,editTextDesc, editTextPrice,editTextPoints;
    Spinner spinnerCategories;
    ArrayAdapter arrayAdapter;
    private FirebaseStorage storage;
    ProgressDialog progressDialog;
    Uri filePath;
    int image_req=1;
    String imageUrl;
    String category;
    Button addImage,updateItem,uploadImage;
    private StorageReference storageReference;
    String[] categories={"cotton","silk"};

    public void uploadImage() {

        if(filePath!=null){

            progressDialog=new ProgressDialog(UpdateItem.this);
            progressDialog.setTitle("uploading...");
            progressDialog.show();
            String url="images/"+ UUID.randomUUID().toString();
            imageUrl="gs://finecloth-7099e.appspot.com/"+url;
            final StorageReference reference=storageReference.child(url);
            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateItem.this, "Image uploaded", Toast.LENGTH_SHORT).show();




                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+ "%");
                        }
                    });


        }else{
            Toast.makeText(UpdateItem.this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        editTextName=findViewById(R.id.editTextName);
        addImage=findViewById(R.id.addImage);
        updateItem=findViewById(R.id.updateItem);
        uploadImage=findViewById(R.id.uploadImage);
        editTextDesc=findViewById(R.id.editTextDescription);
        editTextPrice=findViewById(R.id.editTextPrice);
        editTextPoints=findViewById(R.id.editTextPoints);
        spinnerCategories=findViewById(R.id.spinnerCategories);

        final Intent intent=getIntent();

        editTextName.setText(intent.getStringExtra("name"));
        editTextDesc.setText(intent.getStringExtra("desc"));
        editTextPrice.setText(intent.getStringExtra("price"));
        editTextPoints.setText(intent.getStringExtra("points"));
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(arrayAdapter);
        category=categories[Integer.parseInt(intent.getStringExtra("pos"))];
        spinnerCategories.setSelection(Integer.parseInt(intent.getStringExtra("pos")));
        imageUrl=intent.getStringExtra("imageUrl");

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=categories[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Image"),image_req);

                }catch (Exception e){

                }
            }


        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(UpdateItem.this,ViewAllItems.class);
                intent1.putExtra("name",editTextName.getText().toString());
                intent1.putExtra("desc",editTextDesc.getText().toString());
                intent1.putExtra("price",editTextPrice.getText().toString());
                intent1.putExtra("points",editTextPoints.getText().toString());
                intent1.putExtra("category",category);
                intent1.putExtra("imageUrl",imageUrl);

                setResult(RESULT_OK,intent1);
                finish();



            }
        });



    }
}
