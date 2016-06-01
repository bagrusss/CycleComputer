package ru.sudoteam.cyclecomputer.app.lists;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by bagrusss on 01.06.16.
 * *
 */
public class ConnectAdapter extends BaseAdapter {

    private Set<BluetoothDevice> mSet;
    private List<BluetoothDevice> mData;
    private Context mContext;

    public ConnectAdapter(Context context, Set<BluetoothDevice> set) {
        mSet = set;
        mData = new ArrayList<>(mSet);
        mContext = context;
    }

    @Override
    public void notifyDataSetChanged() {
        mData = new ArrayList<>(mSet);
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder vh;
        if (v == null) {
            v = View.inflate(mContext, android.R.layout.simple_list_item_1, null);
            vh = new ViewHolder();
            vh.deviceView = (TextView) v.findViewById(android.R.id.text1);
            v.setTag(vh);
        } else vh = (ViewHolder) v.getTag();
        BluetoothDevice device = mData.get(position);
        vh.deviceView.setText(device.getName());
        vh.deviceView.append(" ");
        vh.deviceView.append(device.getAddress());
        return v;
    }

    private static class ViewHolder {
        TextView deviceView;
    }

}
