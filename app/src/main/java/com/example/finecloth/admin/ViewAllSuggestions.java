package com.example.finecloth.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finecloth.Category.ViewAllCategories;
import com.example.finecloth.Category.adapter1;
import com.example.finecloth.Category.category;
import com.example.finecloth.R;
import com.example.finecloth.admin.adapters.SuggestionAdapter;
import com.example.finecloth.customer.Cart;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
import com.example.finecloth.customer.model.suggestions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllSuggestions extends AppCompatActivity  {

    FirebaseFirestore db;
    public ArrayList<suggestions> suggestionsList;
    SuggestionAdapter a;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    public ArrayList<suggestions> getData(){

        db=FirebaseFirestore.getInstance();
        db.collection("Suggestions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(!queryDocumentSnapshots.isEmpty()){

                    progressBar.setVisibility(View.GONE);
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){

                        suggestions c=d.toObject(suggestions.class);
                        c.setId(d.getId());
                        suggestionsList.add(c);

                    }

                    a.notifyDataSetChanged();

                }
            }
        });
        return suggestionsList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_suggestions);
        progressBar=findViewById(R.id.progressBar);
        suggestionsList=new ArrayList<suggestions>();
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAllSuggestions.this));
        a=new SuggestionAdapter(getData());
        new ItemTouchHelper((itemTouchHelperCallBack)).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(a);

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int pos=viewHolder.getAdapterPosition();

            db.collection("Suggestions").document(suggestionsList.get(viewHolder.getAdapterPosition()).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    a.notifyDataSetChanged();

                   suggestionsList.remove(pos);
                    a.notifyItemRemoved(pos);
                    Toast.makeText(ViewAllSuggestions.this, "Suggestion removed", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(a);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ViewAllSuggestions.this, "Not able to remove", Toast.LENGTH_SHORT).show();
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
