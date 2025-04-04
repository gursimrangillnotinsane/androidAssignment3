//package com.example.moiassignmienteduos.viewModel;
//
//import android.content.Context;
//import android.content.Intent;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//
//import com.example.moiassignmienteduos.views.Login;
//import com.example.moiassignmienteduos.views.Register;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//
////Importing the two firebase modules required for authentication:
////FirebaseAuth connects with the authentication created on Ass3 (backend)
//import com.google.firebase.auth.FirebaseAuth;
////FireBaseUser helps us get the current user once they have been made post registration.
//import com.google.firebase.auth.FirebaseUser;
//
//
//
//import com.example.moiassignmienteduos.views.Register;
//
//public class RegisterClassViewModel extends ViewModel {
//
//
//    //create an instance of Firebase Authentication.
//    FirebaseAuth mAuth;
//
//    //Making a function that takes the input params of email and password that will be obtained from the user entering them in the register page.
//    private void registerUser(String enteredEmail, String enteredPassword, Context context){
//        //initializing the instance of firebase Authentication
//        mAuth = FirebaseAuth.getInstance();
//
//        //using FireBaseAuth's "createUserWithEmailAndPassword(), attaching a listener to it which activates when FireBaseAuth's aformentioned function runs
//        mAuth.createUserWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener( task ->  {
//            //if it succeeds then the user is registered by creating an fireBaseUser obj.
//                if(task.isSuccessful()){
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    Toast.makeText(context, "User Registration successful with id " + user.getUid(), Toast.LENGTH_SHORT).show();
//
//                    //using finish to go back to the login page by terminating the stacked register page view.
//                    Intent intentObj = new Intent(context, Login.class);
//                    context.startActivity(intentObj);
//                }
//
//                else{
//                    Toast.makeText(context, "The user did not register succesfully", Toast.LENGTH_SHORT).show();
//                }
//        });
//    }
//}
