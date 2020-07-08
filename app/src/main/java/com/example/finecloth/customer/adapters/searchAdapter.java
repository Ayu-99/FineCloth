package com.example.finecloth.customer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecloth.Category.category;
import com.example.finecloth.R;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.Holder> {

    LayoutInflater layoutInflater;
    ArrayList<category> categoryList;
    RecyclerViewClickInterface1 recyclerViewClickInterface;

    public searchAdapter(ArrayList<category> categoryList,RecyclerViewClickInterface1 recyclerViewClickInterface1){
        this.categoryList=categoryList;
        this.recyclerViewClickInterface=  recyclerViewClickInterface1;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customcategory,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchAdapter.Holder holder, int position) {

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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
