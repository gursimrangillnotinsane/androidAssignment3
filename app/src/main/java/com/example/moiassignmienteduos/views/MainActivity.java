package com.example.moiassignmienteduos.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
                finish();
            }
        });
    }
}
