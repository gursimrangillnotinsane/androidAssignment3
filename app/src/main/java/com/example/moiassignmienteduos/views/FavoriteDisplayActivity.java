package com.example.moiassignmienteduos.views;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moiassignmienteduos.R;
import com.example.moiassignmienteduos.databinding.ActivityFavoriteDisplayBinding;
import com.example.moiassignmienteduos.viewModel.FavoritesDisplayViewModel;

public class FavoriteDisplayActivity extends AppCompatActivity {

    ActivityFavoriteDisplayBinding binding;

    FavoritesDisplayViewModel viewModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String imdbID = getIntent().getStringExtra("id");

        viewModelObj = new ViewModelProvider(this).get(FavoritesDisplayViewModel.class);

        viewModelObj.getMovieLiveData().observe(this,movie->{
            if(movie!=null){
                binding.titleTextView.setText(movie.getTitle());
                binding.yearTextView.setText("Year:  " + movie.getYear());
                binding.ratingTextView.setText("IMDB Rating:  " + movie.getRating());
                binding.languageTextView.setText("Language:  " + movie.getLanguage());
                binding.plotTextView.setText("Plot:  " + movie.getPlot());

                Glide.with(this)
                        .load(movie.getPoster())
                        .placeholder(R.drawable.placeholder)
                        .into(binding.posterImageView);
            }
        });
        viewModelObj.fetchMovieDetails(imdbID);
        binding.backButton.setOnClickListener(v -> {
            finish();
        });

        binding.deleteButton.setOnClickListener(view -> {
            viewModelObj.deleteFavoriteDetails(imdbID);
            Toast.makeText(getApplicationContext(), "Movie Unfavorited!", LENGTH_SHORT).show();
            finish();
        });
    }
}