package ru.sudoteam.cyclecomputer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.ArcProgress;

import ru.sudoteam.cyclecomputer.R;

public class MainFragment extends Fragment {

    ArcProgress mAimProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, null);

        return v;
    }
}
