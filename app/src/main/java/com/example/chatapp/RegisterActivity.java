package com.example.chatapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference db;

    EditText nameBox, emailBox, passwordBox;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        nameBox = findViewById(R.id.nameBox);
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> {

            String name = nameBox.getText().toString();
            String email = emailBox.getText().toString();
            String pass = passwordBox.getText().toString();

            if(name.isEmpty() || email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){

                            String id = auth.getCurrentUser().getUid();

                            db.child("Users").child(id)
                                    .setValue(new UserModel(name, email, id));

                            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}