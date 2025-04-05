package com.example.moiassignmienteduos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moiassignmienteduos.model.Movie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoritesListRepository {

    private MutableLiveData<List<Movie>> placeHolderMovieListData;
    private MutableLiveData<String> placeHolderError;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference colRef = db.collection("movie");

    private FirebaseAuth auth;

    public FavoritesListRepository(MutableLiveData<List<Movie>> toBeUpdatedLiveData, MutableLiveData<String> toBeUpdatedErrorData) {
        this.placeHolderMovieListData = toBeUpdatedLiveData;
        this.placeHolderError = toBeUpdatedErrorData;
    }


    public void getFavoriteMovie() {
        List<Movie> favoriteMovies = new ArrayList<>();

        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
        CollectionReference colUserRef = colRef.document(firebaseUser.getUid()).collection("favorites");

        //This fetches movies from the DB and posts it to the movie list data for recyclerview
        colUserRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Movie movieToAdd;
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    movieToAdd = snapshot.toObject(Movie.class);
                    favoriteMovies.add(movieToAdd);
                }
                placeHolderMovieListData.postValue(favoriteMovies);
            }
        });

    }

}
