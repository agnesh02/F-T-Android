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

    EditText etEmail, etPassword, etConfirmPassword, etDob, etContact;
    Button btnRegister;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        etDob = findViewById(R.id.et_dob);
        etContact = findViewById(R.id.et_contact);

        btnRegister = findViewById(R.id.register_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = etEmail.getText().toString();
                String sPass = etPassword.getText().toString();
                String sConfirmPass = etConfirmPassword.getText().toString();

                validateFieldsAndRegister(sEmail, sPass, sConfirmPass);
            }
        });
    }

    public void validateFieldsAndRegister(String email, String pass, String confirmPass) {
        if (!pass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
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