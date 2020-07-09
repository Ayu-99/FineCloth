package com.example.finecloth.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finecloth.Category.category;
import com.example.finecloth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AddNewItem extends AppCompatActivity {



    public ArrayList<String> categoryList=new ArrayList<>();
    EditText editTextName,editTextDesc, editTextPrice,editTextPoints;
    Spinner spinnerCategories;
    String imageUrl;
    String downloadUrl;
    TextView getCategory;
    private FirebaseFirestore db;
    Button addImage,addItem,uploadImage;
    String[] categories={"cotton","silk"};
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressDialog;
    String name,desc,price,points,category;
    String categorySelected;
    Uri filePath;
    int pos;
    ArrayList<String> x=new ArrayList<>();
    ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public ArrayList<String> getData(){

        db=FirebaseFirestore.getInstance();
        db.collection("Category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){

                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){

                        category c=d.toObject(category.class);
                        Log.i("categoryList", String.valueOf(categoryList.size()));
                        arrayAdapter=new ArrayAdapter(AddNewItem.this,android.R.layout.simple_spinner_item,categoryList);
//                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCategories.setAdapter(arrayAdapter);
                        categoryList.add(c.getName());


                    }


                }
            }
        });
        return categoryList;
    }


//    public void validateInputs(){
//        if(filePath==null){
//            Toast.makeText(AddNewItem.this, "Item image is mandatory!!", Toast.LENGTH_SHORT).show();
//        }
//        else if()
//    }

//    public void uploadImage() {
//
//        if(filePath!=null){
//
//            progressDialog=new ProgressDialog(AddNewItem.this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
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
//                    Toast.makeText(AddNewItem.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//
//
//
//
//                }
//            })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
//
//                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                            progressDialog.setMessage("Uploaded "+(int)progress+ "%");
//                        }
//                    });
//
//
//        }else{
//
//            Toast.makeText(AddNewItem.this, "No image selected", Toast.LENGTH_SHORT).show();
//        }
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        db=FirebaseFirestore.getInstance();
        Intent intent=getIntent();

//        pos=Integer.parseInt(intent.getStringExtra("pos"));

        getCategory=findViewById(R.id.getCategory);
        downloadUrl=intent.getStringExtra("downloadUrl");
        editTextName=findViewById(R.id.editTextName);
        editTextDesc=findViewById(R.id.editTextDescription);
        editTextPrice=findViewById(R.id.editTextPrice);
        addImage=findViewById(R.id.addImage);
        addItem=findViewById(R.id.addItem);
        uploadImage=findViewById(R.id.uploadImage);
        editTextPoints=findViewById(R.id.editTextPoints);
        spinnerCategories=findViewById(R.id.spinnerCategories);
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        categoryList=getData();
//        Log.i("xxxxxxxx",String.valueOf(x.size()));
        editTextName.setText(intent.getStringExtra("name"));
        editTextDesc.setText(intent.getStringExtra("desc"));
        editTextPrice.setText(intent.getStringExtra("price"));
        editTextPoints.setText(intent.getStringExtra("points"));
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,x);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(arrayAdapter);
//        Toast.makeText(this, getCategory.getText().toString(), Toast.LENGTH_SHORT).show();
//        if(getCategory.getText().toString()!=null && getCategory.getText().toString()!=""){
//            spinnerCategories.setSelection(Integer.parseInt(getCategory.getText().toString()));
//
//        }

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected=categoryList.get(position);

                pos=position;
//                Toast.makeText(AddNewItem.this, categorySelected, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=editTextName.getText().toString();
                desc=editTextDesc.getText().toString();
                price=editTextPrice.getText().toString();
                points=editTextPoints.getText().toString();
                category=categorySelected;
                Intent intent=new Intent(AddNewItem.this,ItemImage.class);
                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                intent.putExtra("price",price);
                intent.putExtra("points",points);
                intent.putExtra("pos",pos);

                startActivity(intent);
                finish();
//                startActivityForResult(intent,2);

//                try {
//
//                    Intent intent=new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent,"Select Image"),image_req);
//
//                }catch (Exception e){
//
//                }
            }


        });
//
//        uploadImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage();
//            }
//        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                validateInputs();

//                Calendar calendar=Calendar.getInstance();
//                SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
//                String savecurrentDate=currentDate.format(calendar.getTime());
//
//                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
//                String savecurrentTime=currentTime.format(calendar.getTime());

                CollectionReference collectionReference=db.collection("Items");
                if(downloadUrl==null){
                    Toast.makeText(AddNewItem.this, "Image Url shoudl be there", Toast.LENGTH_SHORT).show();
                    return;
                }
                item i=new item(

                        editTextName.getText().toString(),
                        editTextDesc.getText().toString(),
                        editTextPrice.getText().toString(),
                        editTextPoints.getText().toString(),
                        categorySelected,
                        downloadUrl
                );

                collectionReference.add(i).addOnSuccessListener(
                        new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddNewItem.this, "Item added", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewItem.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                });


                editTextName.getText().clear();
                editTextPrice.getText().clear();
                editTextPoints.getText().clear();
                editTextDesc.getText().clear();

            }
        });






    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//
////            System.out.println("Yahan pe aya hai");
//            Log.i("hh","aaya");
//            Toast.makeText(AddNewItem.this, "Here", Toast.LENGTH_LONG).show();
////
//            editTextName.setText(data.getStringExtra("name"));
//            editTextDesc.setText(data.getStringExtra("desc"));
//            editTextPrice.setText(data.getStringExtra("price"));
//            editTextPoints.setText(data.getStringExtra("points"));
//            spinnerCategories.setSelection(Integer.parseInt(data.getStringExtra("pos")));
//            editTextName.setText(name);
////            editTextDesc.setText(desc);
////            editTextPrice.setText(price);
////            editTextPoints.setText(points);
////            spinnerCategories.setSelection(pos);
//            Log.i("DownlaodUrl",downloadUrl);
//            downloadUrl=data.getStringExtra("downloadUrl");




    //            filePath=data.getData();
//            try {
//                Bitmap objectBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
////                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }

//    }
}

//package com.example.finecloth.Item;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.SpinnerAdapter;
//import android.widget.Toast;
//
//import com.example.finecloth.Category.category;
//import com.example.finecloth.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.OnProgressListener;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.UUID;
//
//public class AddNewItem extends AppCompatActivity {
//
//
//    EditText editTextName,editTextDesc, editTextPrice,editTextPoints;
//    Spinner spinnerCategories;
//    String imageUrl;
//    String downloadUrl;
//    private FirebaseFirestore db;
//    Button addImage,addItem,uploadImage;
//    String[] categories={"cotton","silk"};
//    ArrayAdapter arrayAdapter;
//    ProgressDialog progressDialog;
//    String name,desc,price,points,category;
//    String categorySelected;
//    ArrayList<String> fcategory=new ArrayList<>();
//    Uri filePath;
//    int currentPos;
//    int pos;
//    ArrayList<String> categoryList=new ArrayList<>();
//
//    ImageView imageView;
//    SharedPreferences sp7;
//    private FirebaseStorage storage;
//    private StorageReference storageReference;
//
//
//    public ArrayList<String> getData(){
//
//        db=FirebaseFirestore.getInstance();
//        db.collection("Category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                if(!queryDocumentSnapshots.isEmpty()){
//
//                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
//                    for(DocumentSnapshot d:list){
//                        category c=d.toObject(category.class);
////                        int x=sp7.getInt("current",0);
////                        categories[x]=c.getName();
////                        SharedPreferences.Editor ed7=sp7.edit();
////                        ed7.putInt("current",x+1);
////                        ed7.commit();
//
////                        Log.i("name",c.getName());
//                        categoryList.add(c.getName());
//                        fcategory.add(c.getName());
//                        Log.i("Category list is",categoryList.toString());
//
//
//                    }
//
//
//                }
//            }
//        });
////        Log.i("Category list is",categoryList.toString());
//        return categoryList;
//    }
//
//
////    public void validateInputs(){
////        if(filePath==null){
////            Toast.makeText(AddNewItem.this, "Item image is mandatory!!", Toast.LENGTH_SHORT).show();
////        }
////        else if()
////    }
//
////    public void uploadImage() {
////
////        if(filePath!=null){
////
////            progressDialog=new ProgressDialog(AddNewItem.this);
////            progressDialog.setTitle("Uploading...");
////            progressDialog.show();
////            String url="images/"+ UUID.randomUUID().toString();
////            imageUrl="gs://finecloth-7099e.appspot.com/"+url;
////            final StorageReference reference=storageReference.child(url);
////            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                @Override
////                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    progressDialog.dismiss();
//////                    Uri uri= storageReference.child(url).getDownloadUrl().getResult();
//////                    imageUrl="https://firebasestorage.googleapis.com/v0/b/"+uri.toString();
////
////                    Toast.makeText(AddNewItem.this, "Image uploaded", Toast.LENGTH_SHORT).show();
////
////
////
////
////                }
////            })
////                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
////                        @Override
////                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
////
////
////                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
////                            progressDialog.setMessage("Uploaded "+(int)progress+ "%");
////                        }
////                    });
////
////
////        }else{
////
////            Toast.makeText(AddNewItem.this, "No image selected", Toast.LENGTH_SHORT).show();
////        }
////
////    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_new_item);
////        String[] categories = new String[100];
////        categories= getData().toArray(categories);
//        db=FirebaseFirestore.getInstance();
//        Intent intent=getIntent();
////        categoryList=new ArrayList<>();
////        categoryList=;
////        categoryList=new ArrayList<>();
//
//        categoryList=getData();
//        Log.i("Finally ",String.valueOf(categoryList.size()));
//        downloadUrl=intent.getStringExtra("downloadUrl");
//        sp7=getSharedPreferences("currentpos",MODE_PRIVATE);
//        SharedPreferences.Editor ed7=sp7.edit();
//        ed7.putInt("current",0);
//        ed7.commit();
//        editTextName=findViewById(R.id.editTextName);
//        editTextDesc=findViewById(R.id.editTextDescription);
//        editTextPrice=findViewById(R.id.editTextPrice);
//        addImage=findViewById(R.id.addImage);
//        categoryList=new ArrayList<>();
////        categoryList=getData();
//        addItem=findViewById(R.id.addItem);
//        uploadImage=findViewById(R.id.uploadImage);
//        editTextPoints=findViewById(R.id.editTextPoints);
//        spinnerCategories=findViewById(R.id.spinnerCategories);
//        storage=FirebaseStorage.getInstance();
//        storageReference=storage.getReference();
////        getData();
//        String[] categoriesArray = new String[categoryList.size()];
//        categoriesArray = categoryList.toArray(categoriesArray);
//
//        editTextName.setText(intent.getStringExtra("name"));
//        editTextDesc.setText(intent.getStringExtra("desc"));
//        editTextPrice.setText(intent.getStringExtra("price"));
//        editTextPoints.setText(intent.getStringExtra("points"));
////        spinnerCategories.setSelection(intent.getIntExtra("pos"));
//        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategories.setAdapter(arrayAdapter);
//        Log.i("aarrray",categoryList.toString());
////        final String[] finategories = categories;
////        final String[] finalCategories = categories;
//        final String[] finalCategoriesArray = categoriesArray;
//        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AddNewItem.this, categorySelected, Toast.LENGTH_SHORT).show();
//
//
//                categorySelected= categories[position];
//                pos=position;
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        addImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name=editTextName.getText().toString();
//                desc=editTextDesc.getText().toString();
//                price=editTextPrice.getText().toString();
//                points=editTextPoints.getText().toString();
//                category=categorySelected;
//                Intent intent=new Intent(AddNewItem.this,ItemImage.class);
//                intent.putExtra("name",name);
//                intent.putExtra("desc",desc);
//                intent.putExtra("price",price);
//                intent.putExtra("points",points);
//                intent.putExtra("pos",pos);
//
//                startActivity(intent);
//                finish();
////                startActivityForResult(intent,2);
//
////                try {
////
////                    Intent intent=new Intent();
////                    intent.setType("image/*");
////                    intent.setAction(Intent.ACTION_GET_CONTENT);
////                    startActivityForResult(Intent.createChooser(intent,"Select Image"),image_req);
////
////                }catch (Exception e){
////
////                }
//            }
//
//
//        });
////
////        uploadImage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                uploadImage();
////            }
////        });
//
//        addItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                validateInputs();
//
////                Calendar calendar=Calendar.getInstance();
////                SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
////                String savecurrentDate=currentDate.format(calendar.getTime());
////
////                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
////                String savecurrentTime=currentTime.format(calendar.getTime());
//
//                final CollectionReference collectionReference=db.collection("Items");
//                if(downloadUrl==null){
//                    Toast.makeText(AddNewItem.this, "Image Url shoudl be there", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                final item i=new item(
//
//                        editTextName.getText().toString(),
//                        editTextDesc.getText().toString(),
//                        editTextPrice.getText().toString(),
//                        editTextPoints.getText().toString(),
//                        categorySelected,
//                        downloadUrl
//                );
//
//                collectionReference.add(i).addOnSuccessListener(
//                        new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                i.setId(collectionReference.document().getId());
//                                Toast.makeText(AddNewItem.this, "Product added", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AddNewItem.this, "Product deleted", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//                editTextName.getText().clear();
//                editTextPrice.getText().clear();
//                editTextPoints.getText().clear();
//                editTextDesc.getText().clear();
//
//            }
//        });
//
//
//
//
//
//
//    }
//
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if(requestCode==2 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
////
//////            System.out.println("Yahan pe aya hai");
////            Log.i("hh","aaya");
////            Toast.makeText(AddNewItem.this, "Here", Toast.LENGTH_LONG).show();
//////
////            editTextName.setText(data.getStringExtra("name"));
////            editTextDesc.setText(data.getStringExtra("desc"));
////            editTextPrice.setText(data.getStringExtra("price"));
////            editTextPoints.setText(data.getStringExtra("points"));
////            spinnerCategories.setSelection(Integer.parseInt(data.getStringExtra("pos")));
////            editTextName.setText(name);
//////            editTextDesc.setText(desc);
//////            editTextPrice.setText(price);
//////            editTextPoints.setText(points);
//////            spinnerCategories.setSelection(pos);
////            Log.i("DownlaodUrl",downloadUrl);
////            downloadUrl=data.getStringExtra("downloadUrl");
//
//
//
//
//            //            filePath=data.getData();
////            try {
////                Bitmap objectBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
//////                Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
////
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
////        }
//
////    }
//}
