package com.example.finecloth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finecloth.Item.AddNewItem;
import com.example.finecloth.Item.UpdateItem;
import com.example.finecloth.Item.ViewAllItems;

public class ItemDashboard extends AppCompatActivity {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_dashboard);
        actionBar=getSupportActionBar();
        actionBar.hide();
    }

    public void viewItems(View view) {
        Intent intent=new Intent(ItemDashboard.this, ViewAllItems.class);
        startActivity(intent);

    }

    public void deleteItem(View view) {

    }

    public void updateItem(View view) {

        Intent intent=new Intent(ItemDashboard.this, UpdateItem.class);
        startActivity(intent);


    }

    public void addNewItem(View view) {

        Intent intent=new Intent(ItemDashboard.this, AddNewItem.class);
        startActivity(intent);

    }


}
