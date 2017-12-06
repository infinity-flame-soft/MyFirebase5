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

    private Button addUSER,updateUSER,deleteUser,retriveUSER;
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
        updateUSER=findViewById(R.id.btn_update_info);
        deleteUser=findViewById(R.id.btn_delete_info);
        retriveUSER=findViewById(R.id.btn_retrive_info);

        //-------firebase db init--------------
        myDB=FirebaseDatabase.getInstance();
        myRef=myDB.getReference("user");



        retriveUSER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.child("-L-eiBzscSAI_TbDVR_F").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        UserModel model=dataSnapshot.getValue(UserModel.class);

                        Log.d("USERS INFO","NAME: "+model.name+"  EMAIL: "+model.email+"   CONTACT: "+model.contact_no);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(DBActivity.this,"CANCELLED!!",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        updateUSER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myRef.child("-L-eiBzscSAI_TbDVR_F").child("email").setValue(etEmail.getText().toString());

            }
        });
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myRef.removeValue();

            }
        });
        addUSER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel model=new UserModel(etName.getText().toString(),etEmail.getText().toString(),etContact.getText().toString());

                String userID=myRef.push().getKey();


                myRef.child(userID).setValue(model).addOnFailureListener(new OnFailureListener() {
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
