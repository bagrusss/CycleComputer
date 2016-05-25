package ru.sudoteam.cyclecomputer.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.activities.MapActivity;

public class RouteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route, container, false);
    }

    @Override
    public void onStart() {
        startActivity(new Intent(getActivity(), MapActivity.class));
        super.onStart();
    }
}
