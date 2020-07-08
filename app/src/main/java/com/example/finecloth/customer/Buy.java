package com.example.finecloth.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finecloth.Item.item;
import com.example.finecloth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.example.finecloth.customer.model.order;
import org.json.JSONObject;

public class Buy extends AppCompatActivity implements PaymentResultListener {

    ActionBar actionBar;
    private static final String TAG=Buy.class.getSimpleName();
    FirebaseFirestore db;
    String price,name,points,desc,category,imageUrl;
    int pricepermeter;
    SharedPreferences sp4,sp3;
    int length,total;
    item i;
    EditText editTextLength, editTextAddress, editTextPhone;
    TextView textViewTotal;
    Button btnViewTotal;
    Button btnMakePayment;


    public void makePayments(){


//        checkout.setKeyID("rzp_test_uvmilFv72Adj37");
        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */
//        checkout.setImage(R.drawable.rzp_logo);

        /**
         * Reference to current activity
         */
        final Activity activity =this;

        Checkout checkout=new Checkout();
        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "FineCloth");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Buy finest material");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", String.valueOf(total*100));

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        Checkout.preload(getApplicationContext());
        actionBar=getSupportActionBar();
        actionBar.hide();
        db=FirebaseFirestore.getInstance();
        final Intent intent=getIntent();
        sp4=getSharedPreferences("addressphone",MODE_PRIVATE);
        sp3=getSharedPreferences("email",MODE_PRIVATE);
        price=intent.getStringExtra("price");
        desc=intent.getStringExtra("desc");
        name=intent.getStringExtra("name");
        points=intent.getStringExtra("points");
        category=intent.getStringExtra("category");
        imageUrl=intent.getStringExtra("imageUrl");
        editTextLength=findViewById(R.id.editTextLength);
        editTextAddress=findViewById(R.id.editTextAddress);
        editTextPhone=findViewById(R.id.editTextPhone);
        btnMakePayment=findViewById(R.id.buttonMakePayment);
        btnViewTotal=findViewById(R.id.buttonViewTotal);
        textViewTotal=findViewById(R.id.textViewTotalCost);
        if(sp4.getString("address","")!=""){
            editTextAddress.setText(sp4.getString("address",""));
        }
        if(sp4.getString("phone","")!=""){
            editTextPhone.setText(sp4.getString("phone",""));
        }



        btnViewTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextLength.getText().toString().isEmpty()){
                    editTextLength.setError("This field cannot be blank !!");
                    editTextLength.requestFocus();
                    return;
                }
                pricepermeter=Integer.parseInt(price);
                length=Integer.parseInt(editTextLength.getText().toString());
                total=length*pricepermeter;
                textViewTotal.setText("Your total payment is:  ₹ "+ total);

            }
        });

        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor ed4=sp4.edit();
                ed4.putString("address",editTextAddress.getText().toString());
                ed4.putString("phone",editTextPhone.getText().toString());
                ed4.commit();

                i=new item(

                        name,
                        desc,
                        price,
                        points,
                        category,
                        imageUrl

                );

                if(editTextAddress.getText().toString().isEmpty()){
                    editTextAddress.setError("This field cannot be blank !!");
                    editTextAddress.requestFocus();
                    return;
                }


                if(editTextPhone.getText().toString().isEmpty()){
                    editTextPhone.setError("This field cannot be blank !!");
                    editTextPhone.requestFocus();
                    return;
                }

                if(editTextLength.getText().toString().isEmpty()){
                    editTextLength.setError("This field cannot be blank !!");
                    editTextLength.requestFocus();
                    return;
                }

                makePayments();



            }
        });



    }
    @Override
    public void onPaymentSuccess(String s) {

//        String length;
//        String address;
//        String phone;
//        item i;
//        String totalAmount;
//        String email;

        order o=new order(

                String.valueOf(length),
                editTextAddress.getText().toString(),
                editTextPhone.getText().toString(),
                i,
                String.valueOf(total),
                sp3.getString("email","")

        );

        db.collection("Orders").add(o).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Buy.this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });


        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Purchases").add(i).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(Buy.this, "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Buy.this, "Some error occured!! Try Again", Toast.LENGTH_SHORT).show();
            }
        });
//        Toast.makeText(Buy.this, "Payment successful", Toast.LENGTH_SHORT).show();






    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.i("error",s);
        Toast.makeText(Buy.this, "Payment  Not successful", Toast.LENGTH_SHORT).show();

    }
}
