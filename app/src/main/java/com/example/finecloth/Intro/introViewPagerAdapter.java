package com.example.finecloth.Intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.finecloth.R;

import java.util.List;

public class introViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ScreenItem> listScreen;

    public introViewPagerAdapter(Context mContext, List<ScreenItem> listScreen) {
        this.mContext = mContext;
        this.listScreen = listScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen=layoutInflater.inflate(R.layout.layout_screen,null);

        ImageView imageView=layoutScreen.findViewById(R.id.imageView);
        TextView textViewTitle=layoutScreen.findViewById(R.id.textViewTitle);
        TextView textViewDesc=layoutScreen.findViewById(R.id.textViewDesc);

        textViewTitle.setText(listScreen.get(position).getTitle());
        textViewDesc.setText(listScreen.get(position).getDesc());
        imageView.setImageResource(listScreen.get(position).getScreenImg());

        container.addView(layoutScreen);
        return layoutScreen;


    }

    @Override
    public int getCount() {
        return listScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
