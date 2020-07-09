package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finecloth.R;
import com.example.finecloth.customer.model.suggestions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Query extends AppCompatActivity {

    TextView textViewQueryResult,textViewQueryResult1;
    EditText editTextSuggestion;
    FirebaseFirestore db;
    Button btnSendQuery;
    ProgressBar progressBar;
    ActionBar actionBar;
    String suggestion,email;
    SharedPreferences sp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        textViewQueryResult=findViewById(R.id.textViewQueryResult);
        textViewQueryResult1=findViewById(R.id.textViewQueryResult1);
        textViewQueryResult.setVisibility(View.GONE);
        textViewQueryResult1.setVisibility(View.GONE);
        db=FirebaseFirestore.getInstance();
        actionBar=getSupportActionBar();
        actionBar.hide();
        progressBar=findViewById(R.id.progressBar);
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        editTextSuggestion=findViewById(R.id.editTextQuery);
        email=sp3.getString("email","");


        btnSendQuery=findViewById(R.id.buttonSendQuery);
        btnSendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestion=editTextSuggestion.getText().toString();
                suggestions s=new suggestions(
                        suggestion,
                        email,
                        FirebaseAuth.getInstance().getCurrentUser().getUid()

                );

                progressBar.setVisibility(View.VISIBLE);
                db.collection("Suggestions").add(s).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressBar.setVisibility(View.GONE);
                        textViewQueryResult.setVisibility(View.VISIBLE);
                        textViewQueryResult1.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        textViewQueryResult.setText("Some error occured..Try Again!!");
                    }
                });


                editTextSuggestion.getText().clear();

            }
        });
    }



}
