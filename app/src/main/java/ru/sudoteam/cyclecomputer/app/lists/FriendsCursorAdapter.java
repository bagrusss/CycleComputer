package ru.sudoteam.cyclecomputer.app.lists;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.utils.CursorRecyclerViewAdapter;
import ru.sudoteam.cyclecomputer.data.HelperDB;

/**
 * Created by bagrusss on 26.04.16.
 * CursorAdapter for RecyclerView
 */

public class FriendsCursorAdapter extends CursorRecyclerViewAdapter<UserViewHolder> {

    private final String mSeparator = " ";

    public FriendsCursorAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, Cursor c) {
        holder.name.setText(c.getString(c.getColumnIndex(HelperDB.FIRST_NAME)));
        holder.name.append(mSeparator);
        holder.name.append(c.getString(c.getColumnIndex(HelperDB.SECOND_NAME)));
        Picasso.with(holder.image.getContext())
                .load(c.getString(c.getColumnIndex(HelperDB.IMAGE_URL)))
                .error(R.drawable.demo_profile)
                .into(holder.image);
    }
}
