package com.example.HelloWorld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FragmentLeft extends Fragment {
    private static final String TAG = FragmentLeft.class.getSimpleName();

    String[] filmTitle;
    String[] filmDescription;
    RecyclerView recyclerView;
    int[] filmCover ={R.drawable.bucin,R.drawable.passenger, R.drawable.boruto, R.drawable.onepiece
            ,R.drawable.avengers
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: FragmentLeft");
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        filmTitle = getResources().getStringArray(R.array.filmTitle);
        filmDescription = getResources().getStringArray(R.array.filmDescription);
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerViewAdapter recycleViewAdapter = new RecyclerViewAdapter(requireContext(), filmTitle, filmDescription, filmCover);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return view;
    }
}
