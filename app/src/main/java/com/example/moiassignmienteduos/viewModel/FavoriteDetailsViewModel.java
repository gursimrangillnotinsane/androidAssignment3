package com.example.moiassignmienteduos.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.repository.FavoritesDisplayRepository;

public class FavoriteDetailsViewModel extends ViewModel {

    private MutableLiveData<Movie> movieObjLiveData;
    private MutableLiveData<String> errorLiveData;

    private FavoritesDisplayRepository repositoryObj;

    public FavoriteDetailsViewModel() {
        //instantiating the vars
        movieObjLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        repositoryObj = new FavoritesDisplayRepository(movieObjLiveData, errorLiveData);

    }

    public void fetchMovieDetails(String imdbID){
        repositoryObj.getFavoriteDetails(imdbID);
    }

    public LiveData<Movie> getMovieLiveData(){
        return movieObjLiveData;
    }
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
