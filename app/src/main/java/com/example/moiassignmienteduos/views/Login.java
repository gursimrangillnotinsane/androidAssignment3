package com.example.moiassignmienteduos.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moiassignmienteduos.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myAuth = FirebaseAuth.getInstance();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = binding.emailAddressText.getText().toString();
                String password = binding.passwordText.getText().toString();

                if(userEmail.isBlank() == false && password.isBlank() == false){
                    signIn(userEmail, password);
                }
                else{
                    Toast.makeText(Login.this, "Incomplete Credentials", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.registerPageLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(getApplicationContext(), Register.class);
                startActivity(intentObj);
                finish();
            }
        });
    }

    public void signIn(String userEmail, String userPassword){
        myAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentObj);
                }
                else{
                    Toast.makeText(Login.this, "Login Failed, Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}