package com.example.moiassignmienteduos.views;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.String.valueOf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moiassignmienteduos.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myAuth = FirebaseAuth.getInstance();
        //signing everyone out just in case there is cached login info from fireBase.
        myAuth.signOut();
//        Toast.makeText(getApplicationContext(), "Startup", LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = binding.emailAddressText.getText().toString();
                String password = binding.passwordText.getText().toString();

                if(userEmail.isBlank() == false && password.isBlank() == false){
                    signIn(userEmail, password);
                }
                else{
                    Toast.makeText(Login.this, "Incomplete Credentials", LENGTH_SHORT).show();
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
    //when user tries to navigate to the login page from a post login page like MainActivity, they will start the onRestart phase of this view's lifecycle
    //since they can only access MainActivity after logging in, I am gonna send them back there because they should be logged in.
    @Override
    protected void onRestart() {
        Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intentObj);
        super.onRestart();
    }

    public void signIn(String userEmail, String userPassword){
        myAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Successful", LENGTH_SHORT).show();
                    Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentObj);

                }
                else{
                    Toast.makeText(Login.this, "Login Failed, Incorrect Credentials", LENGTH_SHORT).show();
                }
            }
        });
    }

}