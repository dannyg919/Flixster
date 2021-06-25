package com.example.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONException;
import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());

        try {
            Glide.with(this)
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .override(200,300)
                    .into(binding.ivPoster);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage / 2.0f);

        binding.ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);

                i.putExtra("KEY",movie.getMovieId());

                MovieDetailsActivity.this.startActivity(i);

            }
        });


    }
}