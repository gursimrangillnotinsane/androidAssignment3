package com.example.moiassignmienteduos.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.util.ApiClient;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDisplayRepository {
    private final String apiKey = "846fd8e4";
    private MutableLiveData<Movie> placeHolderMovieData;
    private MutableLiveData<String> placeHolderError;

    public MovieDisplayRepository(MutableLiveData<Movie> toBeUpdatedMovieData, MutableLiveData<String> toBeUpdatedError){
        this.placeHolderMovieData = toBeUpdatedMovieData;
        this.placeHolderError = toBeUpdatedError;
    }

    //creating a function for the second ViewModel Class that will be responsible for displaying le movie that you select
    //when the user clicks on a movie, the movie's plot, rating, language, year, and Poster should be displayed
    public void getMovieDetails(String imdbID) {
        String url = "https://www.omdbapi.com/?type=move&apikey=" + apiKey + "&i=" + imdbID;
        ApiClient.sendRequest(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                placeHolderError.postValue("sending Request to ?i failed " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try{
                        //String titleParam, String yearParam, String posterParam, String plotParam, String ratingParam, String languageParam){
                        JSONObject readableJson = new JSONObject(response.body().string());
                        String title = readableJson.optString("Title", "N/A");
                        String year = readableJson.optString("Year", "N/A");
                        String poster = readableJson.optString("Poster", "N/A");
                        String plot = readableJson.optString("Plot", "N/A");
                        String rating = readableJson.optString("imdbRating", "N/A");
                        String language = readableJson.optString("Language", "N/A");

                        Movie movieObj = new Movie(title, year, poster, plot, rating, language);

                        placeHolderMovieData.postValue(movieObj);
                    }
                    catch (Exception e){
                        placeHolderError.postValue("connection with ID unsuccessful " + e.getMessage());
                    }
                }
            }
        });
    }
}
