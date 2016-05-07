package ru.sudoteam.cyclecomputer.app.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.sudoteam.cyclecomputer.R;

/**
 * Created by bagrusss on 25.04.16.
 * Used for AboutFragment
 */

public class DevelopersRecyclerAdapter extends RecyclerView.Adapter<UserViewHolder> {

    List<UserItem> mUsers;

    public DevelopersRecyclerAdapter(List<UserItem> users) {
        this.mUsers = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.name.setText(mUsers.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}
