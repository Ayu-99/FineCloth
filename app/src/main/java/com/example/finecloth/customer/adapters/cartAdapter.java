package com.example.finecloth.customer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finecloth.Item.item;
import com.example.finecloth.R;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface;
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class cartAdapter extends RecyclerView.Adapter<cartAdapter.Holder>{

    LayoutInflater layoutInflater;
    ArrayList<item> itemList;
    RecyclerViewClickInterface1 recyclerViewClickInterface;

    public cartAdapter(ArrayList<item> itemList,RecyclerViewClickInterface1 recyclerViewClickInterface){
        this.itemList=itemList;
        this.recyclerViewClickInterface=recyclerViewClickInterface;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customcartitem,parent,false);

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

    public class Holder extends RecyclerView.ViewHolder {
        ImageView itemimageView;
        CardView cardView;
        TextView textViewitemName;
        TextView textViewitemDesc;
        TextView textViewitemPrice;
        TextView textViewitemPoints;
        Button btnAddToCart;
        TextView textViewitemCategory;
        public Holder(@NonNull View itemView) {
            super(itemView);
            itemimageView=itemView.findViewById(R.id.itemimageView);
            textViewitemName=itemView.findViewById(R.id.itemName);
            textViewitemDesc=itemView.findViewById(R.id.itemDesc);
            textViewitemPrice=itemView.findViewById(R.id.itemPrice);
            btnAddToCart=itemView.findViewById(R.id.buttonAddToCart);
            cardView=itemView.findViewById(R.id.cardView);
            textViewitemPoints=itemView.findViewById(R.id.itemPoints);
            textViewitemCategory=itemView.findViewById(R.id.itemCategory);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });


        }




    }


}
