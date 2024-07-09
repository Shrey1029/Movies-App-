package com.example.movieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movieapp.Adapter.ImageListAdapter;
import com.example.movieapp.Domain.FilmItem;
import com.example.movieapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class filmUI extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private ProgressBar progressBar;
    private TextView movienameTxt, rating, time, date, Synopsis, Stars;
    private NestedScrollView scrollView2;
    private int filmId;
    private ShapeableImageView pic1;
    private ImageView pic2, backImg;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterImgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_ui);

        filmId = getIntent().getIntExtra("id", 0);
        initView();
        mRequestQueue = newRequestQueue(this);
        sendRequest();
    }



    private void sendRequest() {
        mRequestQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView2.setVisibility(View.GONE);
        String url = "https://moviesapi.ir/api/v1/movies/" + filmId;
        stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView2.setVisibility(View.VISIBLE);
            FilmItem item = gson.fromJson(response, FilmItem.class);
            Glide.with(filmUI.this)
                    .load(item.getPoster())
                    .into(pic1);
            Glide.with(filmUI.this)
                    .load(item.getPoster())
                    .into(pic2);

            movienameTxt.setText(item.getTitle());
            rating.setText(item.getRated());
            time.setText(item.getRuntime());
            date.setText(item.getReleased());
            Synopsis.setText(item.getPlot());
            Stars.setText(item.getActors());

            if (item.getImages() != null) {
                adapterImgList = new ImageListAdapter(item.getImages());
                recyclerView.setAdapter(adapterImgList);
            }


        }, error -> {
            progressBar.setVisibility(View.GONE);
            scrollView2.setVisibility(View.VISIBLE);
            Log.e("filmUI", "onErrorResponse: " + error.toString());

        });

        mRequestQueue.add(stringRequest);
    }
    private void initView() {
        movienameTxt = findViewById(R.id.movieName);
        progressBar = findViewById(R.id.progressBar);
        scrollView2= findViewById(R.id.scrollView2);
        pic1 = findViewById(R.id.smallimg);
        pic2 = findViewById(R.id.bigimg);
        rating = findViewById(R.id.rating);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        Synopsis = findViewById(R.id.text25);
        Stars = findViewById(R.id.text24);
        backImg = findViewById(R.id.backimg);
        recyclerView = findViewById(R.id.imgrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        backImg.setOnClickListener(v -> finish());
    }
}
