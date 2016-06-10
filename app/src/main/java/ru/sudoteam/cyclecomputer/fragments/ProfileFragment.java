package ru.sudoteam.cyclecomputer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.activities.SignInActivity;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.accounts.Account;
import ru.sudoteam.cyclecomputer.app.accounts.Error;
import ru.sudoteam.cyclecomputer.app.accounts.User;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;
import ru.sudoteam.cyclecomputer.app.lists.StatisticsAdapter;

import static ru.sudoteam.cyclecomputer.app.lists.StatisticsAdapter.StatisticItem;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView mFirstSecondName;
    private CircleImageView mProfileImage;
    private ListView mStatisticsList;
    private StatisticsAdapter mAdapter;

    public static final int REQUEST_CODE = 12;
    public static final int BT_ENABLE_REQUIRE = 13;

    public static final int REQUEST_UPDATE_DATA = 10;
    public static final int REQUEST_BLUETOOTH_DATA = 11;

    private StatisticItem mTimeItem;
    private StatisticItem mDistanceItem;
    private StatisticItem mCaloriesItem;

    private List<StatisticItem> mStatisticsData = new ArrayList<>(3);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mTimeItem = new StatisticItem(R.mipmap.ic_time, getString(R.string.text_trip_time),
                getString(R.string.text_trip_time_val));
        mDistanceItem = new StatisticItem(R.mipmap.ic_flag, getString(R.string.summary_distance),
                String.format(Locale.ENGLISH, getString(R.string.text_distance_val), 0.0f, "km"));
        mCaloriesItem = new StatisticItem(R.mipmap.ic_fire, getString(R.string.text_calories),
                getString(R.string.text_calories_val));

        mStatisticsData.add(mTimeItem);
        mStatisticsData.add(mDistanceItem);
        mStatisticsData.add(mCaloriesItem);

        mAdapter = new StatisticsAdapter(v.getContext(), mStatisticsData);
        mStatisticsList = (ListView) v.findViewById(R.id.statistics_list_view);
        mStatisticsList.setAdapter(mAdapter);
        mFirstSecondName = (TextView) v.findViewById(R.id.profile_first_second_name);
        mProfileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        v.findViewById(R.id.logout_button).setOnClickListener(this);
        loadProfileFromCycle();
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
        if (event.requestCode == REQUEST_CODE) {
            switch (event.action) {
                case REQUEST_UPDATE_DATA:

                    break;
                case REQUEST_BLUETOOTH_DATA:

                    break;
                case BT_ENABLE_REQUIRE:
                    //TODO to SplashActivity
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_CODE);
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadProfileFromCycle();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadProfileFromCycle() {
        // BluetoothServiceHelper.LoadProfile(getActivity(), REQUEST_CODE);
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

        loadProfileFromCycle();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        App.getAccount().logout();
        SharedPreferences.Editor editor = App.getAppPreferences().edit();
        editor.putInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_NONE);
        editor.apply();
        Activity activity = getActivity();
        activity.finish();
        startActivity(new Intent(activity, SignInActivity.class));
        v.setEnabled(true);
    }
}
