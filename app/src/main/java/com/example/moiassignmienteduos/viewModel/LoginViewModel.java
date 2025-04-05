package com.example.moiassignmienteduos.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//okay I can do this, how hard can ViewModel be after I've already created the login functionality..
//Not too bad.
public class LoginViewModel extends ViewModel {
    //Creating and declaring  appropriate Vars.
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private MutableLiveData<String> loginError = new MutableLiveData<>();;


    //making the appropriate Getters.
    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }
    public MutableLiveData<String> getLoginError() {
        return loginError;
    }

    public FirebaseUser getCurrentUser() {
        return myAuth.getCurrentUser();
    }

    //creating a signIn and SignOut function
    public void signIn(String email, String password) {
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                loginSuccess.setValue(true);
            }
            else
            {
                loginError.setValue("Login Failed: Invalid credentials.");
            }

        });
    }
    public void signOut(){
        myAuth.signOut();
    }

    public void userCheck(){
        FirebaseUser user = myAuth.getCurrentUser();
        Log.d("Email", "Current user email is " + user.getEmail());
    }

}
