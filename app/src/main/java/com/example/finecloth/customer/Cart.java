package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finecloth.Item.item;
import com.example.finecloth.R;
import com.example.finecloth.customer.adapters.adapterDashboard;
import com.example.finecloth.customer.adapters.cartAdapter;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
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

public class Cart extends AppCompatActivity implements RecyclerViewClickInterface1 {
    MenuInflater menuInflater;
    SharedPreferences sp2,sp3;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    SwipeRefreshLayout swipeRefreshLayout;
    String email;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    int pos;
    TextView textViewNoCart;
    ProgressBar progressBar;
    public ArrayList<item> itemList;
    cartAdapter a;

    public ArrayList<item> getData(){

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(uid).collection("Cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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

////
//        if(itemList.isEmpty()){
////            Log.i("items",itemList.get(0).getName());
//            progressBar.setVisibility(View.GONE);
//            textViewNoCart.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//
////            Toast.makeText(Cart.this, "There are no items in Cart!!", Toast.LENGTH_LONG).show();
//
//        }

        return itemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        actionBar=getSupportActionBar();
        actionBar.hide();
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        sp2=getSharedPreferences("keeploggedIn",MODE_PRIVATE);
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        db=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        textViewNoCart=findViewById(R.id.textViewNoCart);
        email=sp3.getString("email","");
        itemList=new ArrayList<item>();
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));


        a=new cartAdapter(getData(),this);
        new ItemTouchHelper((itemTouchHelperCallBack)).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(a);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                a.notifyDataSetChanged();

                itemList.remove(pos);
//                if(itemList.size()==0){
//                    textViewNoCart.setVisibility(View.VISIBLE);
//                    return;
//                }
                a.notifyItemRemoved(pos);
                recyclerView.setAdapter(a);

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.home){
                    Intent intent=new Intent(Cart.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else if(itemId==R.id.search){
//                    Toast.makeText(Cart.this, "Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Cart.this,Search.class);
                    startActivity(intent);

                }
                else if(itemId==R.id.profile){

                    Intent intent=new Intent(Cart.this,Profile.class);
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
            Intent intent=new Intent(Cart.this,Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

        item i=itemList.get(position);

        pos=position;
        Intent intent=new Intent(Cart.this,ViewItem.class);
        intent.putExtra("name",i.getName());
        intent.putExtra("desc",i.getDesc());
        intent.putExtra("price",i.getPrice());
        intent.putExtra("points",i.getPoints());
        intent.putExtra("category",i.getCategory());
        intent.putExtra("imageUrl",i.getImageUrl());


        startActivity(intent);

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("Cart").document(itemList.get(viewHolder.getAdapterPosition()).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    a.notifyDataSetChanged();

                    itemList.remove(pos);
                    a.notifyItemRemoved(pos);
                    Toast.makeText(Cart.this, "Item removed", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(a);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cart.this, "Not able to remove", Toast.LENGTH_SHORT).show();
                    a.notifyDataSetChanged();

//                    itemList.remove(pos);
//                    a.notifyItemRemoved(pos);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(Cart.this));

//                    a=new cartAdapter(getData(),Cart.this);
                    recyclerView.setAdapter(a);

                }
            });

        }
    };



}
