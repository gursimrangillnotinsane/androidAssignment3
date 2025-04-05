package com.example.moiassignmienteduos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moiassignmienteduos.model.Movie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoritesDisplayRepository {

    private MutableLiveData<Movie> placeHolderMovieData;
    private MutableLiveData<String> placeHolderError;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference colRef = db.collection("movie");

    private FirebaseAuth auth;

    public FavoritesDisplayRepository(MutableLiveData<Movie> toBeUpdatedMovieData, MutableLiveData<String> toBeUpdatedError){
        this.placeHolderMovieData = toBeUpdatedMovieData;
        this.placeHolderError = toBeUpdatedError;
    }

    public void getFavoriteDetails(String imdbID) {
        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
        CollectionReference colUserRef = colRef.document(firebaseUser.getUid()).collection("favorites");

        colUserRef.document(imdbID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Movie favoriteMovie = documentSnapshot.toObject(Movie.class);
                placeHolderMovieData.postValue(favoriteMovie);
            }
        });
    }

    public void deleteFavorite(String imdbID) {
        FirebaseUser firebaseUser = auth.getInstance().getCurrentUser();
        CollectionReference colUserRef = colRef.document(firebaseUser.getUid()).collection("favorites");

        colUserRef.document(imdbID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Blargh", "Look how they massacred my boy");
            }
        });
    }
}
