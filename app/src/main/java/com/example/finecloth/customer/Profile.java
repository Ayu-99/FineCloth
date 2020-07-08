package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finecloth.Item.ViewAllItems;
import com.example.finecloth.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    SharedPreferences sp3,sp2;
    Button btnCart,btnPurchases,btnQuery,btnLogout;
    TextView textViewEmail;
    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        actionBar=getSupportActionBar();
        actionBar.hide();
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        textViewEmail=findViewById(R.id.textViewEmail);
        sp2=getSharedPreferences("keeploggedIn",MODE_PRIVATE);
        textViewEmail.setText("Your email: "+ sp3.getString("email",""));
        btnCart=findViewById(R.id.buttonCart);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        btnPurchases=findViewById(R.id.buttonPurchases);
        btnQuery=findViewById(R.id.buttonQuery);
        btnLogout=findViewById(R.id.buttonLogout);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Profile.this,Cart.class);
                startActivity(intent);

            }
        });


        btnPurchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,AllPurchases.class);
                startActivity(intent);
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(Profile.this, "Query", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Profile.this,Query.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
                builder.setTitle("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor ed2=sp2.edit();
                        ed2.clear();
                        ed2.commit();
                        Intent intent=new Intent(Profile.this,Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

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
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.home){

                    Intent intent=new Intent(Profile.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else if(itemId==R.id.search){
                    Intent intent=new Intent(Profile.this,Search.class);
                    startActivity(intent);
                }
                else if(itemId==R.id.profile){

                    Intent intent=new Intent(Profile.this,Profile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                return true;
            }
        });

    }
}
