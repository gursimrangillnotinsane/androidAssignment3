package com.example.moiassignmienteduos.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.repository.MovieListRepository;

import java.util.List;

//adding the keyword extends ViewModel to make it ViewModel
public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movieListLiveData;
    private MutableLiveData<String> errorLiveData;

    private MovieListRepository movieListrepositoryObj;

    public SearchViewModel(){
        movieListLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        movieListrepositoryObj = new MovieListRepository(movieListLiveData, errorLiveData);
    }

    public LiveData<List<Movie>> getMovieListLiveData(){
        return movieListLiveData;
    }

    public LiveData<String> getErrorLiveData(){
        return errorLiveData;
    }

    public void searchingMovieFunction(String movieName){
        movieListrepositoryObj.searchingMovie(movieName);
    }


}
