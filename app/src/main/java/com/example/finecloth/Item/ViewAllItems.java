package com.example.finecloth.Item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finecloth.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewAllItems extends AppCompatActivity {



    FirebaseFirestore db;
    public ArrayList<item> itemList;
    adapter a;
    MenuInflater menuInflater;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    String[] categories={"cotton","silk"};
    item i;
    int pos;

    public void updateItem(int position){


        Intent intent=new Intent(ViewAllItems.this,UpdateItem.class);
        i=itemList.get(position);
        String name=i.getName();
        String desc=i.getDesc();
        String price=i.getPrice();
        String points=i.getPoints();
        String category=i.getCategory();
        String imageUrl=i.getImageUrl();
        List<String> abcd  = Arrays.asList(categories);
        pos = abcd.indexOf(category);

        intent.putExtra("name",name);
        intent.putExtra("desc",desc);
        intent.putExtra("price",price);
        intent.putExtra("points",points);
        intent.putExtra("pos",position);
        intent.putExtra("imageUrl",imageUrl);
        startActivityForResult(intent,101);






    }


    public void deleteProduct(final int pos){

        db.collection("Items").document(itemList.get(pos).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                a.notifyItemRemoved(pos);
                itemList.remove(pos);
                recyclerView.setAdapter(a);
                Toast.makeText(ViewAllItems.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteItem(final int position){
//        Log.i("pos",String.valueOf(position));

        AlertDialog.Builder builder=new AlertDialog.Builder(ViewAllItems.this);
        builder.setTitle("Are you sure about this?");
        builder.setMessage("Deletion is permanent..");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad=builder.create();
        ad.show();


    }



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
        setContentView(R.layout.activity_view_all_items);
        db=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        itemList=new ArrayList<item>();
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAllItems.this));
        a=new adapter(getData());
        recyclerView.setAdapter(a);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int itemId=item.getItemId();
        if (itemId==121){
            updateItem(item.getGroupId());
//            Toast.makeText(ViewAllItems.this, "Selected", Toast.LENGTH_SHORT).show();
//            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//            Log.i("info", String.valueOf(info.position));
            return true;
        }
        if(itemId==122){
            deleteItem(item.getGroupId());
//            Toast.makeText(ViewAllItems.this, "delete Selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onContextItemSelected(item);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && resultCode==RESULT_OK && data!=null){
            String name,desc,price,points,category,imageUrl;
            name=data.getStringExtra("name");
            desc=data.getStringExtra("desc");
            price=data.getStringExtra("price");
            points=data.getStringExtra("points");
            category=data.getStringExtra("category");
            imageUrl=data.getStringExtra("imageUrl");

            final item new_item=new item(
                    name,
                    desc,
                    price,
                    points,
                    category,
                    imageUrl
            );
            db.collection("Items").document(i.getId()).set(new_item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ViewAllItems.this, "Item updated", Toast.LENGTH_SHORT).show();
                    a.notifyItemChanged(pos);
                    itemList.set(pos,new_item);
                    recyclerView.setAdapter(a);
                }
            });




        }
    }
}
