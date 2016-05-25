package ru.sudoteam.cyclecomputer.fragments;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.eventbus.Event;
import ru.sudoteam.cyclecomputer.app.lists.FriendsCursorAdapter;
import ru.sudoteam.cyclecomputer.data.HelperDB;
import ru.sudoteam.cyclecomputer.services.NetworkServiceHelper;

public class FriendsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mFriendsRecyclerView;
    private FriendsCursorAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh;

    private Loader mLoader;

    public final int REQUEST_CODE = 3;

    private static class FriendsLoader extends CursorLoader {

        public FriendsLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            return HelperDB.getInstance(getContext()).getAppFriends();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        mFriendsRecyclerView = (RecyclerView) v.findViewById(R.id.friend_recycler_view);
        mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.fragment_swipe_layout);
        mSwipeRefresh.setColorSchemeResources(R.color.blue, R.color.orange, R.color.green);
        mSwipeRefresh.setOnRefreshListener(() -> loadFriends(true));
        mAdapter = new FriendsCursorAdapter(null);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(mFriendsRecyclerView.getContext());
        mFriendsRecyclerView.setLayoutManager(lm);
        mFriendsRecyclerView.setAdapter(mAdapter);
        mLoader = getLoaderManager().initLoader(0, null, this);
        loadFriends(false);
        return v;
    }

    private void loadFriends(boolean update) {
        if (update)
            NetworkServiceHelper.startLoadFriends(getActivity(), REQUEST_CODE);
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new FriendsLoader(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        mAdapter.swapCursor(c);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSwipeRefresh.setRefreshing(false);
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
    public void onEvent(Event event) {
        if (event.action == REQUEST_CODE && event.status == Event.OK) {
            mLoader.forceLoad();
        }
    }
}
