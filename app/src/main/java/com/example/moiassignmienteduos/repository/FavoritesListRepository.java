package com.example.moiassignmienteduos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.moiassignmienteduos.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoritesListRepository {

    private MutableLiveData<List<Movie>> placeHolderMovieListData;
    private MutableLiveData<String> placeHolderError;

    public FavoritesListRepository(MutableLiveData<List<Movie>> toBeUpdatedLiveData, MutableLiveData<String> toBeUpdatedErrorData) {
        this.placeHolderMovieListData = toBeUpdatedLiveData;
        this.placeHolderError = toBeUpdatedErrorData;
    }


    public void getFavoriteMovie() {
        // GET USER FAVORITES FROM DATABASE HERE

        //Dummy DATA for testing
        List<Movie> favoriteMovies = new ArrayList<>();
        favoriteMovies.add(new Movie("m1", "Inception", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.instagram.com%2Fmajestic7cw%2Fp%2FC5TwHNAv8uX%2F&psig=AOvVaw0QdHNVKqdI7XLBXmfulkHS&ust=1743794409018000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJiv_MTKvIwDFQAAAAAdAAAAABAE", "123123"));
        favoriteMovies.add(new Movie("m2", "Interstellar", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.instagram.com%2Fmajestic7cw%2Fp%2FC5TwHNAv8uX%2F&psig=AOvVaw0QdHNVKqdI7XLBXmfulkHS&ust=1743794409018000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJiv_MTKvIwDFQAAAAAdAAAAABAE", "123123"));
        favoriteMovies.add(new Movie("m3", "The Dark Knight", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.instagram.com%2Fmajestic7cw%2Fp%2FC5TwHNAv8uX%2F&psig=AOvVaw0QdHNVKqdI7XLBXmfulkHS&ust=1743794409018000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJiv_MTKvIwDFQAAAAAdAAAAABAE", "123123"));

        placeHolderMovieListData.postValue(favoriteMovies);
    }

}
