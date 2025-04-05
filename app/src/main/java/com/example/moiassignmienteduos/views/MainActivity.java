package com.example.moiassignmienteduos.views;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.moiassignmienteduos.R;
import com.example.moiassignmienteduos.adapter.MovieRecyclerViewAdapter;
import com.example.moiassignmienteduos.databinding.ActivityMainBinding;
import com.example.moiassignmienteduos.fragments.FavoritesFragment;
import com.example.moiassignmienteduos.fragments.SearchFragment;
import com.example.moiassignmienteduos.viewModel.SearchViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.moiassignmienteduos.views.Login;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private SearchViewModel searchViewModelInstance;
    private MovieRecyclerViewAdapter adapterInstance;

    FirebaseAuth myAuth;

    //  Flag to avoid repeating the toast for each LiveData update
    private boolean searchInitiated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //instantiating FireBaseAuth obj
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user = myAuth.getCurrentUser();
        if (user == null) {
            // No user then Go back to login. This is because jesus christ t
            startActivity(new Intent(this, Login.class));
            finish();
            //stops this code from actually creating the view further
            return;
        }

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Checking to see if the user is maintained for debugging when the user tries to click back from the search page
        Log.d("userEmail", "user Email is " + user.getEmail());

        // for initial loading
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.fragLayout,new SearchFragment());

        ft.commit();
        //to show search fragment
        binding.Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.fragLayout,new SearchFragment());
                ft.commit();}
        });

        //to show favorites fragment
        binding.Favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();

                ft.replace(R.id.fragLayout,new FavoritesFragment());

                ft.commit();}
        });

        //implementing logout functionality
        binding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAuth.signOut();
                Intent intentObj = new Intent(getApplicationContext(), Login.class);
                startActivity(intentObj);
                //using finish here so that the onCreate happens with the Login (i.e everyone gets signed out and no one gets transported to the MainActivity)
                finish();
            }
        });
    }

}
