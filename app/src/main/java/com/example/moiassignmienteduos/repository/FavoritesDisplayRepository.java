package com.example.moiassignmienteduos.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.moiassignmienteduos.model.Movie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoritesDisplayRepository {

    private MutableLiveData<Movie> placeHolderMovieData;
    private MutableLiveData<String> placeHolderError;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference colRef = db.collection("movie").document(" RhEeL591wxlvH7g0Dz5s ").collection("favorites");

    public FavoritesDisplayRepository(MutableLiveData<Movie> toBeUpdatedMovieData, MutableLiveData<String> toBeUpdatedError){
        this.placeHolderMovieData = toBeUpdatedMovieData;
        this.placeHolderError = toBeUpdatedError;
    }

    public void getFavoriteDetails(String imdbID) {

        colRef.document(imdbID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Movie favoriteMovie = documentSnapshot.toObject(Movie.class);
                placeHolderMovieData.postValue(favoriteMovie);
            }
        });
    }
}
