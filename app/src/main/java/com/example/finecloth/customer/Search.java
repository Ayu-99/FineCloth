package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finecloth.Category.category;
import com.example.finecloth.R;
import com.example.finecloth.customer.adapters.searchAdapter;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity implements RecyclerViewClickInterface1 {

    MenuInflater menuInflater;
    SharedPreferences sp2,sp3;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    String email;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    Button btnAddToCart;
    ProgressBar progressBar;
    public ArrayList<category> categoryList;
    searchAdapter a;

    public ArrayList<category> getData(){

        db.collection("Category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){

                    progressBar.setVisibility(View.GONE);
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        category c=d.toObject(category.class);
                        c.setId(d.getId());
                        categoryList.add(c);

                    }

                    a.notifyDataSetChanged();

                }
            }
        });
        return categoryList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        actionBar=getSupportActionBar();
        actionBar.hide();
        btnAddToCart=findViewById(R.id.buttonAddToCart);
        sp2=getSharedPreferences("keeploggedIn",MODE_PRIVATE);
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        db=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        email=sp3.getString("email","");
        categoryList=new ArrayList<category>();
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(Search.this));
        a=new searchAdapter(getData(),this);
        recyclerView.setAdapter(a);


//        btnAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CollectionReference collectionReference=db.collection("users");
//
//
//            }
//        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.home){
                    Intent intent=new Intent(Search.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else if(itemId==R.id.search){
                    Toast.makeText(Search.this, "Clicked", Toast.LENGTH_SHORT).show();

                }
                else if(itemId==R.id.profile){

                    Intent intent=new Intent(Search.this,Profile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_options_menu,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.logout){
            SharedPreferences.Editor ed2=sp2.edit();
            ed2.clear();
            ed2.commit();
            Intent intent=new Intent(Search.this,Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(Search.this, "clicked", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(Search.this,SearchResult.class);
        intent.putExtra("category",categoryList.get(position).getName().toString());
        startActivity(intent);
    }


}
