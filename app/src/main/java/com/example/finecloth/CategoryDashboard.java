package com.example.finecloth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.finecloth.Category.AddNewCategory;
import com.example.finecloth.Category.ViewAllCategories;
import com.example.finecloth.Item.AddNewItem;

public class CategoryDashboard extends AppCompatActivity {

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_dashboard);
        actionBar=getSupportActionBar();
        actionBar.hide();
    }


    public void addNewCategory(View view) {


        Intent intent=new Intent(CategoryDashboard.this, AddNewCategory.class);
        startActivity(intent);
    }

    public void updateCategory(View view) {


        }

    public void deleteCategory(View view) {
    }

    public void viewCategories(View view) {

//        Toast.makeText(this, "aaya", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(CategoryDashboard.this, ViewAllCategories.class);
        startActivity(intent);
    }
}
