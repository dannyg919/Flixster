package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;


@Parcel
public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;
    int movieId;

    String url;
    String posterSize;
    String backdropSize;


    public Movie(){}

    public Movie(JSONObject jsonObject, JSONObject config) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
        url = config.getString("secure_base_url");
        posterSize = config.getJSONArray("poster_sizes").getString(3);
        backdropSize = config.getJSONArray("backdrop_sizes").getString(3);

    }



    public static List<Movie> fromJsonArray (JSONArray movieJsonArray, JSONObject imageConfig) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i),imageConfig));
        }
        return movies;
    }


    public String getPosterPath() throws JSONException {

        return url + posterSize + posterPath;

    }
    public String getBackdropPath() throws JSONException {
        return url + backdropSize + backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getMovieId() { return movieId; }


}
