package com.example.joy.myfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button signUP,signIN,passRecovery,passChange,emailChange,btnDB;
    private ProgressDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----initialize-----------------------------
        email = findViewById(R.id.edittext_email);
        pass = findViewById(R.id.edittext_pass);
        signUP = findViewById(R.id.btn_login);
        signIN = findViewById(R.id.btn_login1);
        passRecovery=findViewById(R.id.btn_recovery);
        passChange=findViewById(R.id.btn_change);
        emailChange=findViewById(R.id.btn_email_change);
        btnDB=findViewById(R.id.btn_db);



        //----auth-----------------------------------
        auth = FirebaseAuth.getInstance();


        //----dialog------------------------
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("please wait..");
        dialog.setTitle("Sign Up");
        dialog.setCancelable(false);


        //-----onClick-----------------------------------
        btnDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,DBActivity.class));
            }
        });
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pass.getText().toString().length() < 6) {
                    Toast.makeText(MainActivity.this, "Password's minimum length is 6", Toast.LENGTH_LONG).show();
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill up your email...", Toast.LENGTH_LONG).show();
                } else {

                    dialog.show();
                    auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            dialog.cancel();

                            if (!task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Authentication failed !..try later", Toast.LENGTH_LONG).show();
                            } else if (task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Congratzzz ! Account created !", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()){

                    Toast.makeText(MainActivity.this, "Please fill up your email / password", Toast.LENGTH_LONG).show();

                }

                else {

                    dialog.show();
                    auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            dialog.cancel();
                            if (task.isSuccessful()){

                                Toast.makeText(MainActivity.this, "Successfully Login !!  ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });

        passRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,RecoveryActivity.class));
                
            }
        });
        passChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,PassChangeActivity.class));
            }
        });
        emailChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,EmailChangeActivity.class));
            }
        });

    }
}
