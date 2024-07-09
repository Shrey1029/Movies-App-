package com.example.movieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.Adapter.FilmListAdapter;
import com.example.movieapp.Domain.ListFilm;
import com.example.movieapp.R;
import com.google.gson.Gson;

public class dashboard extends AppCompatActivity {
    private RecyclerView.Adapter adapterNewMovies, adapterUpComing;
    private RecyclerView recyclerViewNewMovies, recyclerViewUpComing;
    private RequestQueue mRequestQueue;
    private ProgressBar loading1, loading2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        sendRequest1();
        sendRequest2();
    }

    private void initView() {
        recyclerViewNewMovies = findViewById(R.id.view1);
        recyclerViewNewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing = findViewById(R.id.view2);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading1 = findViewById(R.id.loading1);
        loading2 = findViewById(R.id.loading2);
    }

    private void sendRequest1() {
        // Initialize the request queue if not already initialized
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }

        // Show the loading indicator
        loading1.setVisibility(View.VISIBLE);

        // Create the StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hide the loading indicator
                        loading1.setVisibility(View.GONE);

                        // Parse the response using Gson
                        Gson gson = new Gson();
                        ListFilm items = gson.fromJson(response, ListFilm.class);
                        adapterNewMovies = new FilmListAdapter(items);
                        recyclerViewNewMovies.setAdapter(adapterNewMovies);

                        // Handle the parsed data (items) here
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        loading1.setVisibility(View.GONE);
                        // Optionally show an error message
                    }
                }
        );

        // Add the request to the request queue
        mRequestQueue.add(stringRequest);
    }

    private void sendRequest2() {
        // Initialize the request queue if not already initialized
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }

        // Show the loading indicator
        loading2.setVisibility(View.VISIBLE);

        // Create the StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=3",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Hide the loading indicator
                        loading2.setVisibility(View.GONE);

                        // Parse the response using Gson
                        Gson gson = new Gson();
                        ListFilm items = gson.fromJson(response, ListFilm.class);
                        adapterUpComing = new FilmListAdapter(items);
                        recyclerViewUpComing.setAdapter(adapterUpComing);

                        // Handle the parsed data (items) here
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        loading2.setVisibility(View.GONE);
                        // Optionally show an error message
                    }
                }
        );

        // Add the request to the request queue
        mRequestQueue.add(stringRequest);
    }
}
