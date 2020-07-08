package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finecloth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    ActionBar actionBar;
    EditText editTextEmail,editTextPassword;
    FirebaseAuth mAuth;
    CheckBox checkBoxRememberMe,checkBoxKeepMeLoggedIn;
    SharedPreferences sp1,sp2,sp3;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        actionBar=getSupportActionBar();
        actionBar.hide();
        checkBoxRememberMe=findViewById(R.id.checkBoxRememberMe);
        checkBoxKeepMeLoggedIn=findViewById(R.id.checkBoxKeepLoggedIn);
        sp1=getSharedPreferences("rememberMe",MODE_PRIVATE);
        sp2=getSharedPreferences("keeploggedIn",MODE_PRIVATE);
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        progressBar=findViewById(R.id.progressBar);
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();


        if(sp2.getInt("keepLoggedIn",0)==1){
            Intent intent=new Intent(Login.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        if(sp1.getInt("rememberMe",0)==1){
            editTextEmail.setText(sp1.getString("Email",null));
            editTextPassword.setText(sp1.getString("Password",null));
        }
    }

    public void signUp(View view) {
        Intent intent=new Intent(Login.this,SignUp.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        finish();
    }

    public void login(View view) {

        final String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum length should be 6");
            editTextPassword.requestFocus();
            return;

        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email !!");
            editTextEmail.requestFocus();
            return;
        }


        if(checkBoxRememberMe.isChecked()){

            SharedPreferences.Editor ed1=sp1.edit();
            ed1.putString("Email",editTextEmail.getText().toString());
            ed1.putString("Password",editTextPassword.getText().toString());
            ed1.putInt("rememberMe",1);
            ed1.commit();
        }

        if(checkBoxKeepMeLoggedIn.isChecked()){
            SharedPreferences.Editor ed2=sp2.edit();
            ed2.putInt("keepLoggedIn",1);
            ed2.commit();
        }

        progressBar.setVisibility(View.VISIBLE);



        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    SharedPreferences.Editor ed3=sp3.edit();
                    ed3.putString("email",email);
                    ed3.commit();
                    Intent intent=new Intent(Login.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
//                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
