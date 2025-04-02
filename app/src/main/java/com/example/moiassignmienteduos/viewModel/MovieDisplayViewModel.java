package com.example.moiassignmienteduos.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.repository.MovieDisplayRepository;

public class MovieDisplayViewModel extends ViewModel {
    private MutableLiveData<Movie> movieObjLiveData;
    private MutableLiveData<String> errorLiveData;

    private MovieDisplayRepository repositoryObj;

    public MovieDisplayViewModel(){
        //instantiating the vars
        movieObjLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        repositoryObj = new MovieDisplayRepository(movieObjLiveData, errorLiveData);

    }

    //creating the function to get data from the imdb string of a movie
    public void fetchMovieDetails(String imdbID){
        repositoryObj.getMovieDetails(imdbID);
    }

    //creating getters for updated versions of both mutable objs
    public LiveData<Movie> getMovieLiveData(){
        return movieObjLiveData;
    }
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
