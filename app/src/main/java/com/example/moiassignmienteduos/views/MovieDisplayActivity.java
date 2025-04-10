package com.example.moiassignmienteduos.views;
import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moiassignmienteduos.R;
import com.example.moiassignmienteduos.databinding.MovieDisplayBinding;
import com.example.moiassignmienteduos.viewModel.MovieDisplayViewModel;

public class MovieDisplayActivity extends AppCompatActivity {
    //creating a view Binding var
    MovieDisplayBinding binding;

    //creating an instance of the MovieDisplayViewModel
    MovieDisplayViewModel viewModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MovieDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //the imdbID reveived from the intent of the recycleAdapter class's button
        String imdbID = getIntent().getStringExtra("id");

        viewModelObj = new ViewModelProvider(this).get(MovieDisplayViewModel.class);

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

                binding.favoriteButton.setOnClickListener(view -> {
                    viewModelObj.setFavoriteMovie(movie.getTitle(), movie.getYear(), imdbID, movie.getPoster(), movie.getPlot(), movie.getRating(), movie.getLanguage());
                    Toast.makeText(getApplicationContext(), "Movie Favorited!", LENGTH_SHORT).show();
                });
            }
        });
        viewModelObj.fetchMovieDetails(imdbID);
        binding.backButton.setOnClickListener(v -> {
                finish();
        });

    }
}
