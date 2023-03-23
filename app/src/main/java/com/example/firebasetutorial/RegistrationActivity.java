package com.example.firebasetutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword, etDob, etContact;
    Button btnRegister;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etDob = findViewById(R.id.et_dob);
        etContact = findViewById(R.id.et_contact);

        btnRegister = findViewById(R.id.register_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = etUsername.getText().toString().trim();
                String sEmail = etEmail.getText().toString().trim();
                String sPass = etPassword.getText().toString().trim();
                String sConfirmPass = etConfirmPassword.getText().toString().trim();
                String sDob = etDob.getText().toString().trim();
                String sContact = etContact.getText().toString().trim();

                validateFieldsAndRegister(sUsername, sEmail, sPass, sConfirmPass, sDob, sContact);
            }
        });
    }

    public void validateFieldsAndRegister(String username, String email, String pass, String confirmPass, String dob, String contact) {
        if (!pass.equals(confirmPass)) {
            etConfirmPassword.setError("Passwords does not match");
            return;
        }
        if (username.equals("")) {
            etUsername.setError("This field is required");
            return;
        }
        if (dob.equals("")) {
            etDob.setError("This field is required");
            return;
        }
        if (contact.equals("")) {
            etContact.setError("This field is required");
            return;
        }
        registerUser(email, pass);
    }

    public void registerUser(String email, String pass) {

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User has been registered successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener((new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
}