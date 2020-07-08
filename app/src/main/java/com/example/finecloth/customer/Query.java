package com.example.finecloth.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finecloth.R;

public class Query extends AppCompatActivity {

    TextView textViewQueryResult,textViewQueryResult1;
    Button btnSendQuery;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        textViewQueryResult=findViewById(R.id.textViewQueryResult);
        textViewQueryResult1=findViewById(R.id.textViewQueryResult1);
        textViewQueryResult.setVisibility(View.GONE);
        textViewQueryResult1.setVisibility(View.GONE);
        actionBar=getSupportActionBar();
        actionBar.hide();
        btnSendQuery=findViewById(R.id.buttonSendQuery);
        btnSendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewQueryResult.setVisibility(View.VISIBLE);
                textViewQueryResult1.setVisibility(View.VISIBLE);
            }
        });
    }



}
