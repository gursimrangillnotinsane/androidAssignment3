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

import com.example.moiassignmienteduos.R;
import com.example.moiassignmienteduos.adapter.MovieRecyclerViewAdapter;
import com.example.moiassignmienteduos.databinding.FragmentFavoritesBinding;
import com.example.moiassignmienteduos.databinding.FragmentSearchBinding;
import com.example.moiassignmienteduos.viewModel.FavoritesViewModel;
import com.example.moiassignmienteduos.viewModel.SearchViewModel;

import java.util.ArrayList;


public class FavoritesFragment extends Fragment {
    private FragmentFavoritesBinding binding;
    private FavoritesViewModel favoritesViewModel;
    private MovieRecyclerViewAdapter adapterInstance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Set up RecyclerView
        adapterInstance = new MovieRecyclerViewAdapter(requireContext(), new ArrayList<>(),"favorites");
        binding.recyclerViewMovieList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMovieList.setAdapter(adapterInstance);

        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        // Observe movie list updates
        favoritesViewModel.getMovieListLiveData().observe(getViewLifecycleOwner(), movieList -> {
            if (movieList != null) {
                adapterInstance.updateMovieList(movieList);

            }
        });

        favoritesViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMsg ->
                Toast.makeText(requireContext(), "Error: " + errorMsg, Toast.LENGTH_SHORT).show()
        );
        //Now moved to onResume
        //favoritesViewModel.getFavoriteMovieFun();
    }

    //We need to override onResume so that if we delete a favorite, the change shows
    @Override
    public void onResume() {
        super.onResume();
        favoritesViewModel.getFavoriteMovieFun();
    }
}