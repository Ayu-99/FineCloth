package com.example.finecloth.admin;

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
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finecloth.Item.item;
import com.example.finecloth.R;
import com.example.finecloth.admin.adapters.OrderAdapter;
import com.example.finecloth.customer.Cart;
import com.example.finecloth.customer.ViewItem;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
import com.example.finecloth.customer.model.order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewOrders extends AppCompatActivity implements RecyclerViewClickInterface1 {

    MenuInflater menuInflater;
    SharedPreferences sp2, sp3;
    ActionBar actionBar;
    SwipeRefreshLayout swipeRefreshLayout;
    String email;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    int pos;
    TextView textViewNoCart;
    ProgressBar progressBar;
    public ArrayList<order> orderList;
    OrderAdapter a;

    public ArrayList<order> getData() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("Orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){

                    progressBar.setVisibility(View.GONE);
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        order o=d.toObject(order.class);
                        o.setId(d.getId());
                        orderList.add(o);

                    }

                    a.notifyDataSetChanged();

                }
            }
        });
        return orderList;
    }


        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_orders);
            actionBar = getSupportActionBar();
            actionBar.hide();
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            sp2 = getSharedPreferences("keeploggedIn", MODE_PRIVATE);
            sp3 = getSharedPreferences("email", MODE_PRIVATE);
            db = FirebaseFirestore.getInstance();
            progressBar = findViewById(R.id.progressBar);
            textViewNoCart = findViewById(R.id.textViewNoCart);
            email = sp3.getString("email", "");
            orderList = new ArrayList<order>();
            recyclerView = findViewById(R.id.recycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewOrders.this));


            a = new OrderAdapter(getData(), this);
            new ItemTouchHelper((itemTouchHelperCallBack)).attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(a);


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    a.notifyDataSetChanged();

                    orderList.remove(pos);
                    a.notifyItemRemoved(pos);
                    recyclerView.setAdapter(a);

                }
            });


        }





    @Override
    public void onItemClick(int position) {

        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

        order i = orderList.get(position);

        pos = position;
        Intent intent = new Intent(ViewOrders.this, ViewItem.class);
        intent.putExtra("name", i.getI().getName());
        intent.putExtra("desc", i.getI().getDesc());
        intent.putExtra("price", i.getI().getPrice());
        intent.putExtra("points", i.getI().getPoints());
        intent.putExtra("category", i.getI().getCategory());
        intent.putExtra("imageUrl", i.getI().getImageUrl());


        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            db.collection("Orders Completed").add(orderList.get(viewHolder.getAdapterPosition())).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ViewOrders.this, "Some error occurred!!", Toast.LENGTH_SHORT).show();
                }
            });
            db.collection("Orders").document(orderList.get(viewHolder.getAdapterPosition()).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    a.notifyDataSetChanged();

                    orderList.remove(pos);
                    a.notifyItemRemoved(pos);
                    Toast.makeText(ViewOrders.this, "Order removed", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(a);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ViewOrders.this, "Not able to remove", Toast.LENGTH_SHORT).show();
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
