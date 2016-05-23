package ru.sudoteam.cyclecomputer.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.accounts.Account;
import ru.sudoteam.cyclecomputer.app.accounts.Error;
import ru.sudoteam.cyclecomputer.app.accounts.User;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;
import ru.sudoteam.cyclecomputer.app.lists.StatisticsAdapter;

public class ProfileFragment extends Fragment {

    private TextView mFirstSecondName;
    private CircleImageView mProfileImage;
    private ListView mStatisticsList;
    private StatisticsAdapter mAdapter;
    private SharedPreferences mPreferences;

    public static final int REQUEST_UPDATE_DATA = 10;

    private List<StatisticsAdapter.StatisticItem> mStatisticsData = new ArrayList<>(3);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mPreferences = v.getContext().getSharedPreferences(App.SHARED_PREFERENCES, App.MODE_PRIVATE);

        mStatisticsData.add(new StatisticsAdapter.StatisticItem(R.mipmap.ic_time, getString(R.string.text_trip_time),
                getString(R.string.text_trip_time_val)));
        mStatisticsData.add(new StatisticsAdapter.StatisticItem(R.mipmap.ic_flag,
                getString(R.string.summary_distance), getString(R.string.text_distance_val)));
        mStatisticsData.add(new StatisticsAdapter.StatisticItem(R.mipmap.ic_fire,
                getString(R.string.text_calories), getString(R.string.text_calories_val)));

        mAdapter = new StatisticsAdapter(v.getContext(), mStatisticsData);
        mStatisticsList = (ListView) v.findViewById(R.id.statistics_list_view);
        mStatisticsList.setAdapter(mAdapter);
        mFirstSecondName = (TextView) v.findViewById(R.id.profile_first_second_name);
        mProfileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        return v;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UniversalEvent event) {
        if (event.requestCode == REQUEST_UPDATE_DATA) {

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        App.getAccount().userInfo(new Account.UserLoaded() {
            @Override
            public void onUserLoaded(User user) {
                mFirstSecondName.setText(user.firstName);
                mFirstSecondName.append("\n");
                mFirstSecondName.append(user.lastName);
                Picasso.with(mProfileImage.getContext())
                        .load(user.imgURL)
                        .error(R.drawable.demo_profile)
                        .into(mProfileImage);
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(mProfileImage.getContext(), error.msg, Toast.LENGTH_SHORT).show();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }
}
