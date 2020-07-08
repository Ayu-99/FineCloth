package com.example.finecloth.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finecloth.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class ItemImage extends AppCompatActivity {

    ImageView imageView;
    Button btnChoose, btnUpload;
    int image_req=1;
    Uri filePath;
    String downloadUrl,name,desc,price,points;
    int pos;
    ProgressDialog progressDialog;
    StorageReference storageReference;


    public void uploadImage() {

        if(filePath!=null){

            progressDialog=new ProgressDialog(ItemImage.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference imagePath=storageReference.child(filePath.getLastPathSegment()+".jpg");

            final UploadTask uploadTask=imagePath.putFile(filePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message=e.toString();
                    Toast.makeText(ItemImage.this, message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ItemImage.this, "Image uploaded Successfully!!", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(!task.isSuccessful()){
                                throw task.getException();
                            }else{

                                downloadUrl=imagePath.getDownloadUrl().toString();
                                return imagePath.getDownloadUrl();
                            }

                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                downloadUrl=task.getResult().toString();
//                                Toast.makeText(ItemImage.this, downloadUrl, Toast.LENGTH_SHORT).show();

//                                Log.i("DownloadUrlHere",downloadUrl);


                                Intent intent1=new Intent(ItemImage.this,AddNewItem.class);
                                intent1.putExtra("downloadUrl",downloadUrl);
                                intent1.putExtra("name",name);
                                intent1.putExtra("desc",desc);
                                intent1.putExtra("price",price);
                                intent1.putExtra("points",points);
                                intent1.putExtra("pos",pos);

                                startActivity(intent1);
//                                setResult(RESULT_OK,intent1);

                                finish();
                            }
                        }
                    });
                }
            })

//            String url="images/"+ UUID.randomUUID().toString();
//            imageUrl="gs://finecloth-7099e.appspot.com/"+url;
//            final StorageReference reference=storageReference.child(url);
//            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    progressDialog.dismiss();
////                    Uri uri= storageReference.child(url).getDownloadUrl().getResult();
////                    imageUrl="https://firebasestorage.googleapis.com/v0/b/"+uri.toString();
//
//                    Toast.makeText(ItemImage.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//
//
//
//
//                }
//            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+ "%");
                        }
                    });


        }else{

            Toast.makeText(ItemImage.this, "No image selected", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_image);
        imageView=findViewById(R.id.imageView);
        btnUpload=findViewById(R.id.uploadImage);
        btnChoose=findViewById(R.id.chooseImage);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        desc=intent.getStringExtra("desc");
        price=intent.getStringExtra("price");
        points=intent.getStringExtra("points");
//        pos=intent.getIntExtra("pos");
        storageReference= FirebaseStorage.getInstance().getReference().child("Item images");

        btnChoose.setOnClickListener(new View.OnClickListener() {
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

    btnUpload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uploadImage();
        }
    });
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==image_req && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath=data.getData();
            imageView.setImageURI(filePath);
            try {
                Bitmap objectBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
//                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

