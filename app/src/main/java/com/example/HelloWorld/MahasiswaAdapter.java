package com.example.HelloWorld;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.RecyclerViewHolder> {
    private static ArrayList<Mahasiswa> mahasiswaList;

    public MahasiswaAdapter(ArrayList<Mahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mahasiswa, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Mahasiswa mhs = mahasiswaList.get(position);
        holder.getNimMahasiswa().setText(mhs.getNim());
        holder.getNamaMahasiswa().setText(mhs.getNama());
        holder.getPhoneNoMahasiswa().setText(mhs.getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.list_mahasiswa;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private Context context;
        public RecyclerViewHolder(@NonNull View view) {
            super(view);
            textView1 = view.findViewById(R.id.nimMhsTxt);
            textView2 = view.findViewById(R.id.namaMhsTxt);
            textView3 = view.findViewById(R.id.phoneMhsTxt);
            context = view.getContext();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MahasiswaActivity.class);
                    String mhs = mahasiswaList.get(getAdapterPosition()).getNim();
                    intent.putExtra("STATE", "Edit");
                    intent.putExtra("DOC", "mahasiswa" + mhs);
                    context.startActivity(intent);
                }
            });
        }

        public TextView getNimMahasiswa(){
            return textView1;
        }

        public TextView getNamaMahasiswa(){
            return textView2;
        }

        public TextView getPhoneNoMahasiswa(){
            return textView3;
        }
    }
}
