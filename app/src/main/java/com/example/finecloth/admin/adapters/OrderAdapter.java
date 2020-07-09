package com.example.finecloth.admin.adapters;

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
import com.example.finecloth.customer.interfaces.RecyclerViewClickInterface1;
import com.example.finecloth.customer.model.order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder>{

    LayoutInflater layoutInflater;
    ArrayList<order> orderList;
    RecyclerViewClickInterface1 recyclerViewClickInterface;

    public OrderAdapter(ArrayList<order> orderList,RecyclerViewClickInterface1 recyclerViewClickInterface){
        this.orderList=orderList;
        this.recyclerViewClickInterface=recyclerViewClickInterface;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customorderitem,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        order o=orderList.get(position);

//        holder.imageView.setText(s.getImageView());
//        holder.itemimageView.setImageResource(Integer.parseInt(i.getImageUrl()));
        holder.textViewitemName.setText("ItemName: "+o.getI().getName());
        holder.textViewitemCategory.setText("ItemCategory: "+o.getI().getCategory());
        holder.textViewItemLength.setText("Length: "+o.getLength());
//        Log.i("imageUrl",i.getImageUrl());
        holder.textViewitemUserEmail.setText("UserEmail: "+o.getEmail());
        holder.textViewitemUserPhone.setText("UserPhone: "+o.getPhone());
        holder.textViewitemUserAddress.setText("UserAddress: "+o.getAddress());
        holder.textViewUserTotalBill.setText("UserBill: "+o.getTotalAmount());
    }





    @Override
    public int getItemCount() {

        return orderList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewitemUserEmail;
        TextView textViewitemUserAddress;
        TextView textViewitemUserPhone;
        TextView textViewUserTotalBill;
        TextView textViewitemName;
        TextView textViewitemCategory;
        TextView textViewItemLength;
        
        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewitemName=itemView.findViewById(R.id.ItemName);
            textViewitemCategory=itemView.findViewById(R.id.ItemCategory);
            textViewItemLength=itemView.findViewById(R.id.ItemLength);
            textViewitemUserEmail=itemView.findViewById(R.id.UserEmail);
            textViewitemUserPhone=itemView.findViewById(R.id.UserPhone);
            textViewitemUserAddress=itemView.findViewById(R.id.UserAddress);
            textViewUserTotalBill=itemView.findViewById(R.id.UserTotalBill);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });


        }




    }

}
