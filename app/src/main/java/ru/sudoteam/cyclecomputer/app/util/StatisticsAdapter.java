package ru.sudoteam.cyclecomputer.app.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.sudoteam.cyclecomputer.R;

/**
 * Created by bagrusss on 19.04.16.
 *
 */

public class StatisticsAdapter extends BaseAdapter {

    private List<StatisticItem> mItems;
    private Context mContext;

    public StatisticsAdapter(Context context, List<StatisticItem> items) {
        mContext = context;
        mItems = items;
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView value;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View cv, ViewGroup parent) {
        ViewHolder holder;
        if (cv == null) {
            cv = View.inflate(mContext, R.layout.item_user_statistics, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) cv.findViewById(R.id.icon_statistic);
            holder.title = (TextView) cv.findViewById(R.id.statistic_title);
            holder.value = (TextView) cv.findViewById(R.id.statistic_value);
            cv.setTag(holder);
        } else {
            holder = (ViewHolder) cv.getTag();
        }
        StatisticItem item = mItems.get(position);
        holder.icon.setImageResource(item.resId);
        holder.title.setText(item.title);
        holder.value.setText(item.value);
        return cv;
    }
}
