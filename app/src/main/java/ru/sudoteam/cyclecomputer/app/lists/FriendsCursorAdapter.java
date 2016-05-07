package ru.sudoteam.cyclecomputer.app.lists;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.data.HelperDB;

/**
 * Created by bagrusss on 26.04.16.
 * CursorAdapter for RecyclerView
 */

public class FriendsCursorAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private Cursor mCursor;
    private final String mSeparator = "\n";

    public FriendsCursorAdapter(Cursor с) {
        this.mCursor = с;
    }

    /**
     * Redraw RecyclerView with new data from Cursor
     *
     * @param c - new Cursor
     */
    public void swapCursor(Cursor c) {
        mCursor = c;
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.name.setText(mCursor.getString(mCursor.getColumnIndex(HelperDB.FIRST_NAME)));
        holder.name.append(mSeparator);
        holder.name.append(mCursor.getString(mCursor.getColumnIndex(HelperDB.SECOND_NAME)));
        Picasso.with(holder.image.getContext())
                .load(mCursor.getString(mCursor.getColumnIndex(HelperDB.IMAGE_URL)))
                .error(R.drawable.demo_profile)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }
}
