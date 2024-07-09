package com.example.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Activity.filmUI;
import com.example.movieapp.Domain.Datum;
import com.example.movieapp.Domain.ListFilm;
import com.example.movieapp.R;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    private ListFilm items;
    private Context context;

    public FilmListAdapter(ListFilm items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);  // Use 'inflate' instead of 'view'
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum item = items.getData().get(position);
        holder.titleTxt.setText(item.getTitle());
        holder.scoreTxt.setText(item.getImdbRating());

        // Example of using Glide to load an image into an ImageView
        Glide.with(context)
                .load(item.getPoster())
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), filmUI.class);
                intent.putExtra("id", item.getId());  // Use item to get the correct id
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null && items.getData() != null) {  // Ensure getData is used correctly
            return items.getData().size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, scoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
