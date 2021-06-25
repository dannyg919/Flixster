package com.example.flixster;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=5779b3bced23debb5fa95a524fded6eb";
    public static final String TAG = "MovieTrailerActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        int movieId = getIntent().getIntExtra("KEY", 0);
        Log.d("hello", String.valueOf(movieId));


        //ASYNC CALL TO GET VIDEO ID FOR SPECIFIC MOVIE
        AsyncHttpClient client = new AsyncHttpClient();



        client.get(String.format(VIDEO_URL, movieId), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    String videoId = jsonObject.getJSONArray("results").getJSONObject(0).getString("key");
                    Log.i(TAG,"VideoId: " + videoId);
                    initializeVideo(videoId);

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
        //END OF ASYNC CALL





    }

    private void initializeVideo(String videoId) {
        //YOUTUBE PLAYER
        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
        Log.i("KEY ", getString(R.string.youtube_api_key));
        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                Log.d("hello", videoId);
                youTubePlayer.cueVideo(videoId);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}