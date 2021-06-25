package com.example.flixster;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONException;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //PASS IN DATA FROM MOVIE CLICKED
        //Title
        binding.tvTitle.setText(movie.getTitle());
        //Overview
        binding.tvOverview.setText(movie.getOverview());

        //Image
        String imageUrl;

        try {
            if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                imageUrl = movie.getBackdropPath();

            }else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .transform(new RoundedCornersTransformation(15, 10))
                    .override(200,300)
                    .into(binding.ivPoster);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Rating
        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage / 2.0f);


        //Video Player
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