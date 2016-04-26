package ru.sudoteam.cyclecomputer.app.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.sudoteam.cyclecomputer.R;

/**
 * Created by bagrusss on 25.04.16.
 * Used for AboutFragment
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    List<UserItem> mUsers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.profile_image);
            name = (TextView) v.findViewById(R.id.item_user_name);
        }
    }

    public UserRecyclerAdapter(List<UserItem> users) {
        this.mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mUsers.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

}
