package com.example.moiassignmienteduos.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.moiassignmienteduos.databinding.ActivityRegisterBinding;
import com.example.moiassignmienteduos.viewModel.RegisterViewModel;

public class RegisterView extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private RegisterViewModel viewModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModelObj = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Observe registration result
        viewModelObj.getRegistrationStatus().observe(this, result -> {
            if (result.startsWith("success")) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginView.class));
                finish();
            } else {
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }
        });

        binding.loginPageLinkButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginView.class));
            finish();
        });

        //creating a way for a user to go to the login page from the register page without registering.
        binding.registerButton.setOnClickListener(v -> {
            String email = binding.emailAddressText.getText().toString().trim();
            String password = binding.passwordText.getText().toString().trim();
            String confirmPassword = binding.confirmPasswordText.getText().toString().trim();

            if (!viewModelObj.emailIsValid(email)) {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Password(s) cannot be blank", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModelObj.registerUser(email, password);
        });
    }
}
