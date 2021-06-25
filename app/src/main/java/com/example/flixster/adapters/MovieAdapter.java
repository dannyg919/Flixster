package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.MovieTrailerActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Inflate a layout (itemMovie) and return it in ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(movieView);
    }

    //Populate data into item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get movie at position
        Movie movie = movies.get(position);

        //Bind data into the ViewHolder
        try {
            holder.bind(movie);

        } catch (JSONException e){

        }

    }

    //Count of items in list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;


        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);


        }

        public void bind(Movie movie) throws JSONException {
            //Title
            tvTitle.setText(movie.getTitle());

            //Overview
            String overview;
            if (movie.getOverview().length() > 300){
                overview = movie.getOverview().substring(0,300) + "...";
            } else{
                overview = movie.getOverview();
            }

            tvOverview.setText(overview);

            //Image
            String imageUrl;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                imageUrl = movie.getBackdropPath();

            }else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.flicks_movie_placeholder)
                    .transform(new RoundedCornersTransformation(15, 10))
                    .override(200,300)
                    .into(ivPoster);

            //IF LANDSCAPE THEN WE CAN PLAY VIDEOS DIRECTLY FROM SCROLL
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {


                ivPoster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(context, MovieTrailerActivity.class);

                        i.putExtra("KEY", movie.getMovieId());

                        context.startActivity(i);
                    }
                });
            }

        }

        //Item OnClick
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {

                Movie movie = movies.get(position);

                Intent intent = new Intent(context, MovieDetailsActivity.class);

                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                context.startActivity(intent);
            }

        }
    }
}
