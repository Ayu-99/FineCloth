package com.example.finecloth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finecloth.admin.ViewAllSuggestions;
import com.example.finecloth.admin.ViewOrders;

public class CustomerDashboard extends AppCompatActivity {


    ActionBar actionBar;


    public void viewAllSuggestions(View view){

        Intent intent=new Intent(CustomerDashboard.this, ViewAllSuggestions.class);
        startActivity(intent);
//        Toast.makeText(CustomerDashboard.this, "Yes", Toast.LENGTH_SHORT).show();

    }


    public void viewAllOrders(View view){
        Intent intent=new Intent(CustomerDashboard.this, ViewOrders.class);
        startActivity(intent);
//        Toast.makeText(CustomerDashboard.this, "Yes", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        actionBar=getSupportActionBar();
        actionBar.hide();




    }
}
