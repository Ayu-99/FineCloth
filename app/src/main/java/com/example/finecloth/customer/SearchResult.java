package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finecloth.Item.item;
import com.example.finecloth.R;
import com.example.finecloth.customer.adapters.adapterDashboard;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity implements RecyclerViewClickInterface {

    FirebaseFirestore db;
    ActionBar actionBar;
    String category;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<item> itemList;
    adapterDashboard a;

    public ArrayList<item> getData(){


        db.collection("Items").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){

                    progressBar.setVisibility(View.GONE);
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        item i=d.toObject(item.class);
                        i.setId(d.getId());
                        Log.i("item",i.getName());
                        Log.i("category",category);

                        if(i.getCategory().equals(category)){
                            itemList.add(i);
                        }


                    }

                    a.notifyDataSetChanged();

                }
            }
        });
//        if(itemList.isEmpty()){
//            Toast.makeText(SearchResult.this, "Sorry, there are no items available in this category!!", Toast.LENGTH_SHORT).show();
//        }
        return itemList;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        actionBar=getSupportActionBar();
        actionBar.hide();
        Intent intent=getIntent();
        category=intent.getStringExtra("category");
        db=FirebaseFirestore.getInstance();
        itemList=new ArrayList<>();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        recyclerView=findViewById(R.id.recycle);
        progressBar=findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResult.this));
        a=new adapterDashboard(getData(),this);
        recyclerView.setAdapter(a);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.home){
                    Intent intent=new Intent(SearchResult.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else if(itemId==R.id.search){
//                    Toast.makeText(DashboardActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SearchResult.this,Search.class);
                    startActivity(intent);

                }
                else if(itemId==R.id.profile){

                    Intent intent=new Intent(SearchResult.this,Profile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });


    }


    public void onItemClick(int position) {

        item i=itemList.get(position);

        Intent intent=new Intent(SearchResult.this,ViewItem.class);
        intent.putExtra("name",i.getName());
        intent.putExtra("desc",i.getDesc());
        intent.putExtra("price",i.getPrice());
        intent.putExtra("points",i.getPoints());
        intent.putExtra("category",i.getCategory());
        intent.putExtra("imageUrl",i.getImageUrl());


        startActivity(intent);
//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onClickButton(int position) {

        item i=new item(

                itemList.get(position).getName(),
                itemList.get(position).getDesc(),
                itemList.get(position).getPrice(),
                itemList.get(position).getPoints(),
                itemList.get(position).getCategory(),
                itemList.get(position).getImageUrl()
        );

        progressBar.setVisibility(View.VISIBLE);

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference collectionReference=db.collection("users").document(uid).collection("Cart");



        collectionReference.add(i).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SearchResult.this, "Item added to Cart", Toast.LENGTH_LONG).show();
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SearchResult.this, "Item not added to Cart", Toast.LENGTH_LONG).show();
            }
        });



    }
}
