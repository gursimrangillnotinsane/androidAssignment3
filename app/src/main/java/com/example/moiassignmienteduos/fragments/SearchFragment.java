package com.example.moiassignmienteduos.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moiassignmienteduos.adapter.MovieRecyclerViewAdapter;
import com.example.moiassignmienteduos.databinding.FragmentSearchBinding;
import com.example.moiassignmienteduos.viewModel.SearchViewModel;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private SearchViewModel searchViewModelInstance;
    private MovieRecyclerViewAdapter adapterInstance;
    private boolean searchInitiated = false; // Flag to avoid repeated toasts



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textInput.requestFocus();

        // ViewModel instance
        searchViewModelInstance = new ViewModelProvider(this).get(SearchViewModel.class);

        // Set up RecyclerView
        adapterInstance = new MovieRecyclerViewAdapter(requireContext(), new ArrayList<>(),"search");
        binding.recyclerViewMovieList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMovieList.setAdapter(adapterInstance);

        // Observe movie list
        searchViewModelInstance.getMovieListLiveData().observe(getViewLifecycleOwner(), movieList -> {
            if (movieList != null) {
                adapterInstance.updateMovieList(movieList);
                if (searchInitiated) {
                    if (!movieList.isEmpty()) {
                        Toast.makeText(requireContext(), "Movies found: " + movieList.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "No movies found", Toast.LENGTH_SHORT).show();
                    }
                    searchInitiated = false;
                }
            }
        });

        // Observe error messages
        searchViewModelInstance.getErrorLiveData().observe(getViewLifecycleOwner(), errorMsg -> {
            Toast.makeText(requireContext(), "Error: " + errorMsg, Toast.LENGTH_SHORT).show();
        });

        // Search button click
        binding.searchButton.setOnClickListener(v -> {
            String userSearch = binding.textInput.getText().toString().trim();
            if (!userSearch.isEmpty()) {
                searchInitiated = true; // Set flag for upcoming result
                searchViewModelInstance.searchingMovieFunction(userSearch);
            } else {
                Toast.makeText(requireContext(), "Please enter a movie name.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}