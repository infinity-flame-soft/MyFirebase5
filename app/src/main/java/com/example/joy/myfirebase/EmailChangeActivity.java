package com.example.joy.myfirebase;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailChangeActivity extends AppCompatActivity {


    private EditText etPass;
    private Button button;
    private ProgressDialog dialog;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_change);

        //----initialize-----------------
        etPass=findViewById(R.id.edittext_pass);
        button=findViewById(R.id.btn_change);


        //----dialog--------------
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait..");
        dialog.setTitle("Changeing");
        dialog.setCancelable(false);


        //----auth----------------------
        auth=FirebaseAuth.getInstance();

        //---user-------------
        user=auth.getCurrentUser();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etPass.getText().toString().isEmpty()){
                    Toast.makeText(EmailChangeActivity.this,"Please enter email..",Toast.LENGTH_LONG).show();
                }
                else {
                    dialog.show();
                    user.updateEmail(etPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            dialog.cancel();

                            if (task.isSuccessful()){
                                Toast.makeText(EmailChangeActivity.this,"Email id updated successfully..",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(EmailChangeActivity.this,"email id can't change right now ! try later ",Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }




            }
        });

    }
}
