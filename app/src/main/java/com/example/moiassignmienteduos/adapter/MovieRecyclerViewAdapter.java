package com.example.moiassignmienteduos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moiassignmienteduos.R;
import com.example.moiassignmienteduos.model.Movie;
import com.example.moiassignmienteduos.views.FavoriteDisplayActivity;
import com.example.moiassignmienteduos.views.MainActivity;
import com.example.moiassignmienteduos.views.MovieDisplayActivity;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private final Context context;
    private List<Movie> movieList;
    private String mode;

    // Constructor to receive the movie list
    public MovieRecyclerViewAdapter(Context context, List<Movie> movieList,String mode){
        this.movieList = movieList;
        this.context = context;
        this.mode = mode;  // Set mode
    }

    // Inflate the item layout and return a new ViewHolder
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(currentView);
    }

    // Bind data to each ViewHolder item
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleText.setText(movie.getTitle());
        holder.yearText.setText("Year: " + movie.getYear());
        holder.ratingText.setText("Rating: " + movie.getRating());

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .placeholder(R.drawable.placeholder)
                .into(holder.poster);

        holder.button.setOnClickListener(v -> {
            Intent intent;
            if (mode.equals("search")) {
                intent = new Intent(context, MovieDisplayActivity.class);

            } else { // mode is "favorites"
                intent = new Intent(context, FavoriteDisplayActivity.class);
            }
            intent.putExtra("id", movie.getImdbID());
            context.startActivity(intent);
        });
    }

    // Return total number of items
    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, yearText, ratingText;
        Button button;

        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.movieTitleText);
            yearText = itemView.findViewById(R.id.movieYearText);
            ratingText = itemView.findViewById(R.id.movieRatingText);
            button = itemView.findViewById(R.id.infoButton);
            poster=itemView.findViewById(R.id.movieimageView);
        }
    }
    public void updateMovieList(List<Movie> newMovies) {
        this.movieList = newMovies;
        notifyDataSetChanged();
    }

}
