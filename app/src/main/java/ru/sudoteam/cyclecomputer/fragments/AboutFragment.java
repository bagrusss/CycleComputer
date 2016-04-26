package ru.sudoteam.cyclecomputer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.lists.UserItem;
import ru.sudoteam.cyclecomputer.app.lists.UserRecyclerAdapter;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        List<UserItem> users = new ArrayList<>(3);
        users.add(new UserItem(getString(R.string.dev_vlad), 0));
        users.add(new UserItem(getString(R.string.dev_dima), 0));
        users.add(new UserItem(getString(R.string.dev_pasha), 0));
        UserRecyclerAdapter adapter = new UserRecyclerAdapter(users);
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.user_recycler_view);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        return v;
    }


}
