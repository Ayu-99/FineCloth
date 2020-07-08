package com.example.finecloth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    ActionBar actionBar;

    public void MoveToItemDashBoard(View view){
//        Toast.makeText(MainActivity.this, "Item", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,ItemDashboard.class);
        startActivity(intent);

    }

    public void MoveToCategoryDashBoard(View view){
//        Toast.makeText(MainActivity.this, "category", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,CategoryDashboard.class);
        startActivity(intent);
    }
    public void MoveToCustomerDashBoard(View view){
//        Toast.makeText(MainActivity.this, "customer", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this,CustomerDashboard.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        actionBar.hide();

    }
}
