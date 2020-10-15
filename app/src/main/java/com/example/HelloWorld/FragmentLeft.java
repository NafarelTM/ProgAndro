package com.example.HelloWorld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentLeft extends Fragment {
    private static final String TAG = FragmentLeft.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: FragmentLeft");
        return inflater.inflate(R.layout.fragment_left, container, false);
    }
}
