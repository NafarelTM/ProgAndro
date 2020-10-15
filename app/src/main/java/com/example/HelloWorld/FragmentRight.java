package com.example.HelloWorld;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentRight extends Fragment {
    private static final String TAG = FragmentRight.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: FragmentRight");
        return inflater.inflate(R.layout.fragment_right, container, false);
    }
}
