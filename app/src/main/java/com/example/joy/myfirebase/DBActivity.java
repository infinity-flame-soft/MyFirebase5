package com.example.joy.myfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DBActivity extends AppCompatActivity {

    private Button addUSER;
    private EditText etName,etEmail,etContact;
    private FirebaseDatabase myDB;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        //----initialize--------------------------------
        addUSER=findViewById(R.id.add_info);
        etContact=findViewById(R.id.edittext_contact);
        etEmail=findViewById(R.id.edittext_email);
        etName=findViewById(R.id.edittext_name);

        //-------firebase db init--------------
        myDB=FirebaseDatabase.getInstance();
        myRef=myDB.getReference("user");


        addUSER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel model=new UserModel(etName.getText().toString(),etEmail.getText().toString(),etContact.getText().toString());


                myRef.setValue(model).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(DBActivity.this,"Error ",Toast.LENGTH_LONG).show();

                        Log.d("DATABASE_ERROR", String.valueOf(e));

                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DBActivity.this," DATA ADDED !",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }
}
