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
import android.widget.ProgressBar;

import com.example.finecloth.Item.item;
import com.example.finecloth.R;
import com.example.finecloth.customer.adapters.cartAdapter;
import com.example.finecloth.customer.adapters.cartAdapter;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllPurchases extends AppCompatActivity implements RecyclerViewClickInterface1 {

    MenuInflater menuInflater;
    SharedPreferences sp2,sp3;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    String email;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    int pos;
    ProgressBar progressBar;
    public ArrayList<item> itemList;
    cartAdapter a;

    public ArrayList<item> getData(){

        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid).collection("Purchases").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){

                    progressBar.setVisibility(View.GONE);
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        item i=d.toObject(item.class);
                        i.setId(d.getId());
                        itemList.add(i);

                    }

                    a.notifyDataSetChanged();

                }
            }
        });


        return itemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        actionBar=getSupportActionBar();
        actionBar.hide();
        sp2=getSharedPreferences("keeploggedIn",MODE_PRIVATE);
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        db=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);

        email=sp3.getString("email","");
        itemList=new ArrayList<item>();
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllPurchases.this));
        a=new cartAdapter(getData(),this);
        recyclerView.setAdapter(a);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.home){
                    Intent intent=new Intent(AllPurchases.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else if(itemId==R.id.search){
//                    Toast.makeText(AllPurchases.this, "Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AllPurchases.this,Search.class);
                    startActivity(intent);

                }
                else if(itemId==R.id.profile){

                    Intent intent=new Intent(AllPurchases.this,Profile.class);
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
            Intent intent=new Intent(AllPurchases.this,Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {



    }

}
