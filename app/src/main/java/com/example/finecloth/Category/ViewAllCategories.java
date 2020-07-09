package com.example.finecloth.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.finecloth.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllCategories extends AppCompatActivity {



    FirebaseFirestore db;
    public ArrayList<category> categoryList;
    adapter1 a;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    public ArrayList<category> getData(){

        db=FirebaseFirestore.getInstance();
        db.collection("Category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){

                    progressBar.setVisibility(View.GONE);
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        category c=d.toObject(category.class);
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
        setContentView(R.layout.activity_view_all_categories);
        progressBar=findViewById(R.id.progressBar);
        categoryList=new ArrayList<category>();
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAllCategories.this));
        a=new adapter1(getData());
        recyclerView.setAdapter(a);


    }
}
