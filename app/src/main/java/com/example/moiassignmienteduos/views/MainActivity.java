package com.example.moiassignmienteduos.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moiassignmienteduos.adapter.MovieRecyclerViewAdapter;
import com.example.moiassignmienteduos.databinding.ActivityMainBinding;
import com.example.moiassignmienteduos.viewModel.SearchViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private SearchViewModel searchViewModelInstance;
    private MovieRecyclerViewAdapter adapterInstance;

    //  Flag to avoid repeating the toast for each LiveData update
    private boolean searchInitiated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textInput.requestFocus();

        // ViewModel instance
        searchViewModelInstance = new ViewModelProvider(this).get(SearchViewModel.class);

        // Set up RecyclerView
        adapterInstance = new MovieRecyclerViewAdapter(this, new ArrayList<>());
        binding.recyclerViewMovieList.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMovieList.setAdapter(adapterInstance);

        // Observe movie list
        searchViewModelInstance.getMovieListLiveData().observe(this, movieList -> {
            if (movieList != null) {
                adapterInstance.updateMovieList(movieList);
                if (searchInitiated) {
                    if (!movieList.isEmpty()) {
                        Toast.makeText(this, "Movies found: " + movieList.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show();
                    }
                    searchInitiated = false;
                }
            }
        });

        // Observe error messages
        searchViewModelInstance.getErrorLiveData().observe(this, errorMsg -> {
            Toast.makeText(this, "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
        });

        // Search button click
        binding.searchButton.setOnClickListener(v -> {
            String userSearch = binding.textInput.getText().toString().trim();
            if (!userSearch.isEmpty()) {
                searchInitiated = true; //  flag true for upcoming result
                searchViewModelInstance.searchingMovieFunction(userSearch);
            } else {
                Toast.makeText(this, "Please enter a movie name.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
