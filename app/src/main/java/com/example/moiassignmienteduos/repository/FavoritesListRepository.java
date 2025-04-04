package com.example.moiassignmienteduos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.moiassignmienteduos.model.Movie;
import com.google.android.gms.tasks.OnSuccessListener;
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

    //Why the hell does setting the UID allow for empty spaces at the start and end of it? Terrible input control.
    private CollectionReference colRef = db.collection("movie").document(" RhEeL591wxlvH7g0Dz5s ").collection("favorites");

    public FavoritesListRepository(MutableLiveData<List<Movie>> toBeUpdatedLiveData, MutableLiveData<String> toBeUpdatedErrorData) {
        this.placeHolderMovieListData = toBeUpdatedLiveData;
        this.placeHolderError = toBeUpdatedErrorData;
    }


    public void getFavoriteMovie() {
        List<Movie> favoriteMovies = new ArrayList<>();

        //EXAMPLE ADDING MOVIE TO A LIST
//        colRef.document("putMovieIDHere").set(MovieObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                String docId = documentReference.getId();
//                Log.d("Blargh", docId);
//            }
//        });

        //This fetches movies from the DB and posts it to the movie list data for recyclerview
        colRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
