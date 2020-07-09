package com.example.finecloth.Intro;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.finecloth.R;
import com.example.finecloth.customer.Login;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {


    ViewPager screenPager;
    introViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    List<ScreenItem> mList;
    Button btnNext;
    int position=0;
    SharedPreferences sp5;
    ActionBar actionBar;
    Animation btnAnimation;
    Button btnGetStarted;


    public void loadLastScreen() {

        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnimation);


    }

    public void savePrefsData(){
        SharedPreferences.Editor ed5=sp5.edit();
        ed5.putBoolean("isIntroOpened",true);
        ed5.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sp5=getSharedPreferences("intro",MODE_PRIVATE);

        if(sp5.getBoolean("isIntroOpened",false)){

            Intent intent=new Intent(IntroActivity.this,Login.class);
            startActivity(intent);
            finish();

        }
        setContentView(R.layout.activity_intro);

        actionBar=getSupportActionBar();
        actionBar.hide();
        mList=new ArrayList<>();
        mList.add(new ScreenItem("Finest Material","Get the best material, in bulk, in low cost, just at your door step. Get variety of clothing material, with the best discounts and best deals ,just for you!!",R.drawable.material));
        mList.add(new ScreenItem("Fast Delivery","Your order is just a few days away from you, Fastest, safest and trust worthy  delivery, ensuring proper care of your material!!",R.drawable.delivery));
        mList.add(new ScreenItem("Easy Payment","Fully secured payment with variety of payment options plus amazing discounts and offers just for you,",R.drawable.payment));
        sp5=getSharedPreferences("intro",MODE_PRIVATE);
        btnAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tabIndicator=findViewById(R.id.tabLayout);
        screenPager=findViewById(R.id.screen_viewpager);
        introViewPagerAdapter=new introViewPagerAdapter(this,mList);

        screenPager.setAdapter(introViewPagerAdapter);

        //setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);
        btnGetStarted=findViewById(R.id.buttonGetStarted);
        btnNext=findViewById(R.id.buttonNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=screenPager.getCurrentItem();
                if(position<mList.size()){

                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position==mList.size()-1){
                    loadLastScreen();
                }
            }

        });



        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==mList.size()-1){

                    loadLastScreen();

                }
                if(tab.getPosition()<mList.size()-1){

                    btnGetStarted.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrefsData();
                Intent intent=new Intent(IntroActivity.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
