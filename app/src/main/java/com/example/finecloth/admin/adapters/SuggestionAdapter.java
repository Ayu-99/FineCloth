package com.example.finecloth.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecloth.Category.adapter1;
import com.example.finecloth.Category.category;
import com.example.finecloth.R;
import com.example.finecloth.customer.model.suggestions;

import java.util.ArrayList;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.Holder>{
    LayoutInflater layoutInflater;
    ArrayList<suggestions> categoryList;

    public SuggestionAdapter(ArrayList<suggestions> categoryList){
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

        suggestions c=categoryList.get(position);

//        holder.imageView.setText(s.getImageView());
//        holder.itemimageView.setImageResource(i.getImageUrl());
        holder.textViewSugg.setText("Sugg: "+c.getSugg());
        holder.textViewEmail.setText("Customer Email: "+c.getEmail());
    }

    @Override
    public int getItemCount() {

        return categoryList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textViewSugg;
        TextView textViewEmail;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewSugg=itemView.findViewById(R.id.categoryName);
            textViewEmail=itemView.findViewById(R.id.categoryDesc);


        }
    }
}
