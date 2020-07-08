package com.example.finecloth.Item;


import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecloth.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class adapter extends RecyclerView.Adapter<adapter.Holder> {

    LayoutInflater layoutInflater;
    ArrayList<item> itemList;

    public adapter(ArrayList<item> itemList){
        this.itemList=itemList;

    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customitem,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        item i=itemList.get(position);

//        holder.imageView.setText(s.getImageView());
//        holder.itemimageView.setImageResource(Integer.parseInt(i.getImageUrl()));
        holder.textViewitemName.setText("Name: "+i.getName());
        holder.textViewitemDesc.setText("Desc: "+i.getDesc());
//        Log.i("imageUrl",i.getImageUrl());
        Picasso.get().load(i.getImageUrl()).into(holder.itemimageView);
        holder.textViewitemPrice.setText("Price: "+i.getPrice());
        holder.textViewitemPoints.setText("Points: "+i.getPoints());
        holder.textViewitemCategory.setText("Category: "+i.getCategory());
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView itemimageView;
        CardView cardView;
        TextView textViewitemName;
        TextView textViewitemDesc;
        TextView textViewitemPrice;
        TextView textViewitemPoints;
        TextView textViewitemCategory;
        public Holder(@NonNull View itemView) {
            super(itemView);
            itemimageView=itemView.findViewById(R.id.itemimageView);
            textViewitemName=itemView.findViewById(R.id.itemName);
            textViewitemDesc=itemView.findViewById(R.id.itemDesc);
            textViewitemPrice=itemView.findViewById(R.id.itemPrice);
            cardView=itemView.findViewById(R.id.cardView);
            textViewitemPoints=itemView.findViewById(R.id.itemPoints);
            textViewitemCategory=itemView.findViewById(R.id.itemCategory);
            cardView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle("Select Option");
            menu.add(this.getAdapterPosition(),121,0,"Update");
            menu.add(this.getAdapterPosition(),122,1,"Delete");
        }
    }


}
