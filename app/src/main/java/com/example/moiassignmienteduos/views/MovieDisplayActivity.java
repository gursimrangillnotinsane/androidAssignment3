package com.example.moiassignmienteduos.views;
import android.os.Bundle;

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
            }
        });
        viewModelObj.fetchMovieDetails(imdbID);
        binding.backButton.setOnClickListener(v -> {
                finish();
        });

    }
}
