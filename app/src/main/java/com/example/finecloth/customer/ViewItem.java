package com.example.finecloth.customer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.finecloth.R;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;

public class ViewItem extends AppCompatActivity {

    String name,desc,price,points,category,imageUrl,id;
    TextView textViewName,textViewDesc,textViewPoints,textViewPrice,textViewCategory;
    ImageView imageView;

    PhotoViewAttacher photoViewAttacher;

//SubsamplingScaleImageView imageView;

    ActionBar actionBar;
    Button btnBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        actionBar=getSupportActionBar();
        actionBar.hide();
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        desc=intent.getStringExtra("desc");
        price=intent.getStringExtra("price");
        points=intent.getStringExtra("points");
        id=intent.getStringExtra("id");
        category=intent.getStringExtra("category");
        imageUrl=intent.getStringExtra("imageUrl");
        textViewName=findViewById(R.id.textViewName);
        textViewDesc=findViewById(R.id.textViewDesc);
        textViewPrice=findViewById(R.id.textViewPrice);
        textViewPoints=findViewById(R.id.textViewPoints);
        textViewCategory=findViewById(R.id.textViewCategory);
        imageView=findViewById(R.id.imageViewItemImage);
        btnBuy=findViewById(R.id.buttonBuy);

        photoViewAttacher=new PhotoViewAttacher(imageView);
        photoViewAttacher.update();
        textViewName.setText(textViewName.getText()+": "+name);
        textViewDesc.setText(textViewDesc.getText()+": "+desc);
        textViewPrice.setText(textViewPrice.getText()+": "+price);
        textViewPoints.setText(textViewPoints.getText()+": "+points);
        textViewCategory.setText(textViewCategory.getText()+": "+category);

        Picasso.get().load(imageUrl).into(imageView);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ViewItem.this, "buy clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ViewItem.this,Buy.class);
                intent.putExtra("id",id);
                intent.putExtra("price",price);
                intent.putExtra("name",name);
                intent.putExtra("desc",desc);
                intent.putExtra("points",points);
                intent.putExtra("category",category);
                intent.putExtra("imageUrl",imageUrl);
                startActivity(intent);
            }
        });

    }
}
