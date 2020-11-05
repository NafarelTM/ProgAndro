package com.example.HelloWorld;

import android.content.Context;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    String[] filmTitle;
    String[] filmDescription;
    int[] filmCover;
    Context context;
    public  RecyclerViewAdapter(Context context, String[] filmTitle, String[] filmDescription, int[] filmCover){
        this.context = context;
        this.filmTitle = filmTitle;
        this.filmDescription = filmDescription;
        this.filmCover = filmCover;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_film, parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(filmTitle[position]);
        holder.description.setText(filmDescription[position]);
        holder.img.setImageResource(filmCover[position]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.description.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_NONE);
            holder.title.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_NONE);
        }
    }

    @Override
    public int getItemCount() {
        return filmTitle.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.filmTitle);
            description = itemView.findViewById(R.id.filmDescription);
            img = itemView.findViewById(R.id.filmCover);
        }
    }
}
