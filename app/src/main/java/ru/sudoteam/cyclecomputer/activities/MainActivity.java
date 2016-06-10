package ru.sudoteam.cyclecomputer.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.accounts.Account;
import ru.sudoteam.cyclecomputer.app.accounts.Error;
import ru.sudoteam.cyclecomputer.app.accounts.User;
import ru.sudoteam.cyclecomputer.app.eventbus.UniversalEvent;
import ru.sudoteam.cyclecomputer.data.HelperDB;
import ru.sudoteam.cyclecomputer.fragments.AboutFragment;
import ru.sudoteam.cyclecomputer.fragments.FriendsFragment;
import ru.sudoteam.cyclecomputer.fragments.MainFragment;
import ru.sudoteam.cyclecomputer.fragments.ProfileFragment;
import ru.sudoteam.cyclecomputer.fragments.RouteFragment;
import ru.sudoteam.cyclecomputer.fragments.SettingsFragment;


public class MainActivity extends CycleBaseActivity {

    private static final int MAIN_POSITION = 1;
    private static final int FRIENDS_POSITION = 2;
    private static final int ROUTE_POSITION = 3;
    private static final int SETTINGS_POSITION = 5;
    private static final int ABOUT_POSITION = 6;
    private int mLastPosition = 1;

    public static final int REQUEST_CODE = 36;

    public static final int BT_ENABLE_REQUIRE = 13;


    private Toolbar mToolbar;
    private Drawer mDrawer;
    private AccountHeader mHeader;
    private FragmentManager mFragmentManager;

    private Fragment mLastFragment;
    private ProfileDrawerItem mProfile;
    private TypedArray mTypedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypedArray = obtainStyledAttributes(mThemeId,
                new int[]{R.attr.drawerHeader});
        //TODO
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_with_fragment);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.main);
        mProfile = new ProfileDrawerItem();
        buildHeader();
        buildDrawer();

        mFragmentManager = getFragmentManager();
        if (savedInstanceState != null)
            mLastFragment = (Fragment) getLastCustomNonConfigurationInstance();
        else mLastFragment = new MainFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mLastFragment)
                .commit();
        loadProfile();
        //startActivity(new Intent(this, ConnectActivity.class));
    }

    private void loadProfile() {
        App.getAccount().userInfo(new Account.UserLoaded() {
            @Override
            public void onUserLoaded(User user) {
                String userText = user.firstName + "\n" + user.lastName;
                mProfile.withName(userText);
                WeakReference<Target> userImageReference = new WeakReference<>(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mProfile.withIcon(bitmap);
                        mHeader.addProfiles(mProfile);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }

                });
                Target target = userImageReference.get();
                Picasso.with(mContext)
                        .load(user.imgURL)
                        .into(target);
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(mContext, error.msg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void buildHeader() {
        TypedArray ta = obtainStyledAttributes(mThemeId,
                new int[]{R.attr.drawerHeader, R.attr.drawerBackground});
        mHeader = new AccountHeaderBuilder()
                .withActivity(mContext)
                .withHeaderBackground(ta.getDrawable(0))
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View v, IProfile profile, boolean current) {
                        v.setEnabled(false);
                        mToolbar.setTitle(R.string.profile);
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, mLastFragment = new ProfileFragment())
                                .commit();
                        mDrawer.setSelection(mLastPosition, false);
                        v.setEnabled(true);
                        return false;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return true;
                    }
                })
                .build();
        ta.recycle();
    }

    private void buildDrawer() {

        mDrawer = new DrawerBuilder()
                .withActivity(mContext)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)
                .withDisplayBelowStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.main).withIcon(R.mipmap.ic_main),
                        new PrimaryDrawerItem().withName(R.string.friends).withIcon(R.mipmap.ic_friends),
                        new PrimaryDrawerItem().withName(R.string.route).withIcon(R.mipmap.ic_route),

                        new SectionDrawerItem().withName(R.string.tools),
                        new PrimaryDrawerItem().withName(R.string.settings).withIcon(R.mipmap.ic_tools),
                        new PrimaryDrawerItem().withName(R.string.about).withIcon(R.mipmap.ic_about)
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    mLastPosition = position;
                    int resTitle = 0;
                    switch (position) {
                        case MAIN_POSITION:
                            mLastFragment = new MainFragment();
                            resTitle = R.string.main;
                            break;
                        case FRIENDS_POSITION:
                            mLastFragment = new FriendsFragment();
                            resTitle = R.string.friends;
                            break;
                        case ROUTE_POSITION:
                            mLastFragment = new RouteFragment();
                            resTitle = R.string.route;
                            break;
                        case SETTINGS_POSITION:
                            mLastFragment = new SettingsFragment();
                            resTitle = R.string.settings;
                            break;
                        case ABOUT_POSITION:
                            mLastFragment = new AboutFragment();
                            resTitle = R.string.about;
                    }
                    mToolbar.setTitle(resTitle);
                    transaction.replace(R.id.fragment_container, mLastFragment).commit();
                    return false;
                })
                .withActionBarDrawerToggleAnimated(true)
                .build();
        mDrawer.setSelection(MAIN_POSITION);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UniversalEvent event) {
        if (event.requestCode == BT_ENABLE_REQUIRE) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mLastFragment;
    }

    @Override
    protected void onDestroy() {
        HelperDB.closeDB();
        super.onDestroy();
    }

}
