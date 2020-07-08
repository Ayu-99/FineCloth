package com.example.finecloth.Category;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecloth.R;

import java.util.ArrayList;


public class adapter1 extends RecyclerView.Adapter<adapter1.Holder> {

    LayoutInflater layoutInflater;
    ArrayList<category> categoryList;

    public adapter1(ArrayList<category> categoryList){
        this.categoryList=categoryList;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customcategory,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        category c=categoryList.get(position);

//        holder.imageView.setText(s.getImageView());
//        holder.itemimageView.setImageResource(i.getImageUrl());
        holder.textViewcategoryName.setText("Name: "+c.getName());
        holder.textViewcategoryDesc.setText("Desc: "+c.getDesc());
    }

    @Override
    public int getItemCount() {

        return categoryList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textViewcategoryName;
        TextView textViewcategoryDesc;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewcategoryName=itemView.findViewById(R.id.categoryName);
            textViewcategoryDesc=itemView.findViewById(R.id.categoryDesc);


        }
    }

}
