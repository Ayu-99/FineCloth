package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finecloth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity{

    private FirebaseAuth mAuth;
    ActionBar actionBar;
    EditText editTextEmail,editTextPassword;
    Button btnSignUp;
    ProgressBar progressBar;
    TextView emailVerificationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        actionBar=getSupportActionBar();
        actionBar.hide();
        progressBar=findViewById(R.id.progressBar);
        btnSignUp=findViewById(R.id.btnSignUp);
        emailVerificationTextView=findViewById(R.id.emailVerificationTextView);
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signUp(View view) {

        String email=editTextEmail.getText().toString();
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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {


//                    Log.i("user email",mAuth.getCurrentUser().getEmail());
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SignUp.this, "You have been send a confirmation email", Toast.LENGTH_SHORT).show();
                            emailVerificationTextView.setText("Please verify your email!! If verified, then click login");

                        }
                    });
//                    Log.i("user email",mAuth.getCurrentUser()());

                    if (mAuth.getCurrentUser().isEmailVerified()) {

                        emailVerificationTextView.setText("");
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (mAuth.getCurrentUser().isEmailVerified()) {

                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Email not verified!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }



                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(SignUp.this, "This email is already registered!", Toast.LENGTH_SHORT).show();

                    }else{
                        //task.exception.getMessage()
                        Toast.makeText(SignUp.this, "Some error occurred!!", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });



    }

    public void login(View view) {



            Intent intent=new Intent(SignUp.this,Login.class);
            startActivity(intent);
            finish();

    }
}
