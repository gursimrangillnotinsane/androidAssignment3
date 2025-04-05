package com.example.moiassignmienteduos.views;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moiassignmienteduos.databinding.ActivityLoginBinding;
import com.example.moiassignmienteduos.viewModel.LoginViewModel;

public class LoginView extends AppCompatActivity {

    ActivityLoginBinding binding;

    //creating a viewModel Obk
    LoginViewModel loginViewModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //instantiating the viewModelObj
        loginViewModelObj = new ViewModelProvider(this).get(LoginViewModel.class);
        //signing everyone out just in case there is cached login info from fireBase.
        loginViewModelObj.signOut();

        // Observe login result
        loginViewModelObj.getLoginSuccess().observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Login Successful", LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        });
        loginViewModelObj.getLoginError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(this, errorMsg, LENGTH_SHORT).show();
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = binding.emailAddressText.getText().toString();
                String password = binding.passwordText.getText().toString();

                if(userEmail.isBlank() == false && password.isBlank() == false){
                    loginViewModelObj.signIn(userEmail, password);
                }
                else{
                    Toast.makeText(LoginView.this, "Incomplete Credentials", LENGTH_SHORT).show();
                }
            }
        });

        binding.registerPageLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(getApplicationContext(), RegisterView.class);
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
        loginViewModelObj.userCheck();
        super.onRestart();
    }

}