package com.example.moiassignmienteduos.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moiassignmienteduos.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

//Importing the two firebase modules required for authentication:
    //FirebaseAuth connects with the authentication created on Ass3 (backend)
import com.google.firebase.auth.FirebaseAuth;
    //FireBaseUser helps us get the current user once they have been made post registration.
import com.google.firebase.auth.FirebaseUser;

import kotlin.text.Regex;

public class Register extends AppCompatActivity {
    //adding viewBinding class obj
    ActivityRegisterBinding binding;

    //create an instance of Firebase Authentication.
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initializing the instance of firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        binding.loginPageLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(getApplicationContext(), Login.class);
                startActivity(intentObj);
                finish();
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = binding.emailAddressText.getText().toString().trim();
                String password = binding.passwordText.getText().toString().trim();
                String confirmPassword = binding.confirmPasswordText.getText().toString().trim();

                if(emailIsValid(userEmail)){
                    if(password.isBlank() || confirmPassword.isBlank()){
                        Toast.makeText(Register.this, "Password(s) is blank", Toast.LENGTH_SHORT).show();

                    }
                    else if(password.equals(confirmPassword)){
                        registerUser(userEmail, password);
                    }
                    else{
                        Toast.makeText(Register.this, "The two passwords didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Register.this, "Email is Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Making a function that takes the input params of email and password that will be obtained from the user entering them in the register page.
    private void registerUser(String enteredEmail, String enteredPassword){
        //using FireBaseAuth's "createUserWithEmailAndPassword(), attaching a listener to it which activates when FireBaseAuth's aformentioned function runs
        mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            //if it succeeds then the user is registered by creating an fireBaseUser obj.
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Register.this, "User Registration successful with id " + user.getUid(), Toast.LENGTH_SHORT).show();

                    Intent intentObj = new Intent(getApplicationContext(), Login.class);
                    startActivity(intentObj);
                    finish();
                }
                else{
                    Exception e = task.getException();
                    Toast.makeText(Register.this, "registration failed because " + e.getMessage().toLowerCase(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //making a function to validate email
    public boolean emailIsValid(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern patternObj = Pattern.compile(regex);
        Matcher matcherObj = patternObj.matcher(email);
        boolean matchFound =  matcherObj.matches();
        return matchFound;
    }
}