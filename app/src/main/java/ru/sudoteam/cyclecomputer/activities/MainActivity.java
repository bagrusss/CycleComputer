package ru.sudoteam.cyclecomputer.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.fragments.AboutFragment;
import ru.sudoteam.cyclecomputer.fragments.FriendsFragment;
import ru.sudoteam.cyclecomputer.fragments.MainFragment;
import ru.sudoteam.cyclecomputer.fragments.ProfileFragment;
import ru.sudoteam.cyclecomputer.fragments.RouteFragment;
import ru.sudoteam.cyclecomputer.fragments.SettingsFragment;

public class MainActivity extends CycleBaseActivity implements View.OnClickListener {

    private static final int MAIN_POSITION = 1;
    private static final int FRIENDS_POSITION = 2;
    private static final int ROUTE_POSITION = 3;
    private static final int SETTINGS_POSITION = 5;
    private static final int ABOUT_POSITION = 6;
    private int mLastPosition = 1;

    private Toolbar mToolbar;
    private Drawer mDrawer;
    private FragmentManager mFragmentManager;

    private CircleImageView mUserImage;
    private TextView mUserText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        View navigationHeader = View.inflate(mContext, R.layout.drawer_header, null);
        mUserImage = (CircleImageView) navigationHeader.findViewById(R.id.drawer_profile_image);
        mUserImage.setOnClickListener(this);
        mUserText = (TextView) navigationHeader.findViewById(R.id.drawer_user_text);
        mFragmentManager = getFragmentManager();
        mDrawer = new DrawerBuilder().withActivity(mContext)
                .withToolbar(mToolbar)
                .withHeader(navigationHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.main).withIcon(R.mipmap.ic_main),
                        new PrimaryDrawerItem().withName(R.string.friends).withIcon(R.mipmap.ic_friends),
                        new PrimaryDrawerItem().withName(R.string.route).withIcon(R.mipmap.ic_route),

                        new SectionDrawerItem().withName(R.string.tools),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.mipmap.ic_tools),
                        new PrimaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about)
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    Fragment fragment = null;
                    mLastPosition = position;
                    switch (position) {
                        case MAIN_POSITION:
                            fragment = new MainFragment();
                            mToolbar.setTitle(R.string.main);
                            break;
                        case FRIENDS_POSITION:
                            fragment = new FriendsFragment();
                            mToolbar.setTitle(R.string.friends);
                            break;
                        case ROUTE_POSITION:
                            fragment = new RouteFragment();
                            mToolbar.setTitle(R.string.route);
                            break;
                        case SETTINGS_POSITION:
                            fragment = new SettingsFragment();
                            mToolbar.setTitle(R.string.settings);
                            break;
                        case ABOUT_POSITION:
                            fragment = new AboutFragment();
                            mToolbar.setTitle(R.string.about);
                    }
                    transaction.replace(R.id.fragment_container, fragment).commit();
                    return false;
                })
                .build();
        mDrawer.setSelection(MAIN_POSITION);
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();

        VKRequest request = VKApi.users().get(VKParameters.from(
                VKApiConst.USER_IDS, getSharedPreferences(App.SHARED_PREFERENCES, MODE_PRIVATE)
                        .getString(App.KEY_VK_ID, "1"),
                VKApiConst.FIELDS, VKApiUser.FIELD_PHOTO_200));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiUser user = ((VKList<VKApiUser>) response.parsedModel).get(0);
                String userText = user.first_name + "\n" + user.last_name;
                mUserText.setText(userText);
                Picasso.with(mContext)
                        .load(user.photo_200)
                        .error(R.drawable.demo_profile)
                        .into(mUserImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Toast.makeText(mContext, "Picasso error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(mContext, error.errorReason, Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.drawer_profile_image) {
            v.setEnabled(false);
            mToolbar.setTitle(R.string.profile);
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .commit();
            if (mDrawer.isDrawerOpen()) {
                mDrawer.setSelection(mLastPosition);
                mDrawer.closeDrawer();
            }
            v.setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                if (mDrawer.isDrawerOpen())
                    mDrawer.closeDrawer();
                else mDrawer.openDrawer();
                return true;
        }
        return super.onKeyDown(keycode, e);
    }
}
