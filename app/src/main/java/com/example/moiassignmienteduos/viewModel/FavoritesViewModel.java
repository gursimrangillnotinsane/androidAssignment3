package com.example.moiassignmienteduos.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.repository.FavoritesListRepository;
import com.example.moiassignmienteduos.repository.MovieListRepository;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieListLiveData;
    private MutableLiveData<String> errorLiveData;

    private FavoritesListRepository favoritesListRepoObj;

    public FavoritesViewModel(){
        movieListLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        favoritesListRepoObj = new FavoritesListRepository(movieListLiveData, errorLiveData);
    }

    public LiveData<List<Movie>> getMovieListLiveData(){
        return movieListLiveData;
    }

    public LiveData<String> getErrorLiveData(){
        return errorLiveData;
    }

    public void getFavoriteMovieFun(){
        favoritesListRepoObj.getFavoriteMovie();
    }


}

