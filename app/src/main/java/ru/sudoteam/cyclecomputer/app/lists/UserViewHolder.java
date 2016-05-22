package ru.sudoteam.cyclecomputer.app.lists;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.sudoteam.cyclecomputer.R;

/**
 * Created by bagrusss on 26.04.16.
 * holder with CircleImageView
 */
public class UserViewHolder extends RecyclerView.ViewHolder {
    CircleImageView image;
    TextView name;

    public UserViewHolder(View v) {
        super(v);
        image = (CircleImageView) v.findViewById(R.id.profile_image);
        name = (TextView) v.findViewById(R.id.item_user_name);
    }
}
