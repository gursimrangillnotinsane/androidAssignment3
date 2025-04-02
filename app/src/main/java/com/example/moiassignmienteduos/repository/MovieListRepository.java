package com.example.moiassignmienteduos.repository;
//this class is going to get RAW DATA from the api itself (omdb) and then create Movie
//obj from it using the movie class, this obj will then be SENT TO THE viewModel class
//which will be responsible to update the UI (views)

//understanding that was very confusing but makes sense since modularity is the backbone of MVVM

//importing the required stuff,
// starting with the classes that Repo will need to communicate with
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.util.ApiClient;

//importing the required JSON stuff
import org.json.JSONArray;
import org.json.JSONObject;

//importing the required List and ArrayList stuff (to send list of movie objs to viewModel Class)
import java.util.ArrayList;
import java.util.List;

//importing the required stuff from okhttp3,
// Call (to represent a given connection request)
import okhttp3.Call;
// Callback (to represent the success or failure of the given request, onsuccess and onfailure)
import okhttp3.Callback;
// Response to represent the response from the API
import okhttp3.Response;

//importing the required stuff to catch connection errors for the Callback's onfailure)
import java.io.IOException;

//importing Mutable Data stuff
public class MovieListRepository {
    private static final String apiKey = "846fd8e4";

    //creating the mutable live data variables that will be sent to the viewModel
    private MutableLiveData<List<Movie>> placeHolderMovieListData;
    private MutableLiveData<String> placeHolderError;

    public MovieListRepository(MutableLiveData<List<Movie>> toBeUpdatedLiveData, MutableLiveData<String> toBeUpdatedErrorData) {
        this.placeHolderMovieListData = toBeUpdatedLiveData;
        this.placeHolderError = toBeUpdatedErrorData;
    }

    public void searchingMovie(String nameOfMovie) {
        String url = "https://www.omdbapi.com/?type=movie&apikey=" + apiKey + "&s=" + nameOfMovie;

        ApiClient.sendRequest(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call callObj, @NonNull IOException e) {
                placeHolderError.postValue("sending Request to ?s failed " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response responseObj) throws IOException {
                List<Movie> movieList = new ArrayList<>();

                if (responseObj.isSuccessful()) {
                    try {
                        JSONObject readableJson = new JSONObject(responseObj.body().string());

                        if (readableJson.has("Search")) {
                            JSONArray results = readableJson.getJSONArray("Search");

                            //creating a final counter var that tracks how many ratings are fetched
                            final int totalMovies = results.length();
                            final int[] ratingsFetchedCounter = {0};

                            for (int i = 0; i < totalMovies; i++) {
                                JSONObject movieJson = results.getJSONObject(i);

                                String title = movieJson.optString("Title", "N/A");
                                String year = movieJson.optString("Year", "N/A");
                                String poster = movieJson.optString("Poster", "N/A");
                                String imdbID = movieJson.optString("imdbID", "N/A");

                                //creating a movie obj and adding it to movieList
                                Movie movieObj = new Movie(title, year, poster, imdbID);
                                movieList.add(movieObj);

                                //sending this obj to a function to get its rating
                                fetchRatingForMovie(movieObj, movieList, ratingsFetchedCounter, totalMovies);
                            }
                        } else if (readableJson.has("Error")) {
                            placeHolderMovieListData.postValue(new ArrayList<>());
                        }
                    } catch (Exception e) {
                        placeHolderError.postValue("connection with search unsuccessful " + e.getMessage());
                    }
                }
            }
        });
    }

    //function to send another api request using movie id to get rating and update that movie obj
    private void fetchRatingForMovie(Movie movie, List<Movie> movieList, int[] counter, int total) {
        String ratingUrl = "https://www.omdbapi.com/?type=movie&apikey=" + apiKey + "&i=" + movie.getImdbID();

        ApiClient.sendRequest(ratingUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //even if rating fails, increment the counter so we don't wait forever
                synchronized (counter) {
                    counter[0]++;
                    if (counter[0] == total) {
                        placeHolderMovieListData.postValue(movieList);
                    }
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject ratingJson = new JSONObject(response.body().string());
                        String rating = ratingJson.optString("imdbRating", "N/A");
                        movie.setRating(rating);
                    } catch (Exception e) {
                        placeHolderError.postValue("could not parse rating: " + e.getMessage());
                    }
                }

                //increment the counter when rating for this movie is done (successful or not)
                synchronized (counter) {
                    counter[0]++;
                    //when all movies have fetched rating (or failed), post the final movie list to the live data
                    if (counter[0] == total) {
                        placeHolderMovieListData.postValue(movieList);
                    }
                }
            }
        });
    }
}