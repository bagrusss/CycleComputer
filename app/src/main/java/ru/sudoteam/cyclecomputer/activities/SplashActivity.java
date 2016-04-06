package ru.sudoteam.cyclecomputer.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.AuthHelper;

public class SplashActivity extends CycleBaseActivity implements View.OnClickListener {

    private Button mStartButton;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAuthDialog;
    private Intent mNavigationIntent;
    private SharedPreferences mSharedPreferences;
    public static final String TAG_SPLASH_ACTIVITY = "SplashActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(App.SHARED_PREFERENCES, MODE_PRIVATE);
        mNavigationIntent = new Intent(mContext, NavigationActivity.class);
        setContentView(R.layout.activity_splash);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.background_dark));
        }

        mBuilder = new AlertDialog.Builder(mContext);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showDialogLENotSupport(mBuilder);
            return;
        }
        if (!adapter.isEnabled()) {
            showDialogEnableBT(mBuilder, adapter);
        }
        //TODO draw OK animation and start activity
        showStartButton();
    }

    private void showStartButton() {
        mStartButton.setVisibility(View.VISIBLE);
        mStartButton.setEnabled(true);
    }

    private void showDialogEnableBT(AlertDialog.Builder builder, BluetoothAdapter adapter) {
        builder.setTitle(R.string.dialog_bt_title)
                .setIcon(android.R.drawable.stat_sys_data_bluetooth)
                .setMessage(R.string.dialog_bt_message_enable)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    adapter.enable();
                    dialog.cancel();
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    System.exit(0);
                })
                .setCancelable(false);
        builder.create().show();
    }

    private void showDialogLENotSupport(AlertDialog.Builder builder) {
        builder.setTitle(R.string.dialog_bt_title)
                .setMessage(R.string.dialog_bt_message_not_support)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    System.exit(0);
                });
        builder.create().show();
    }

    private void showDialogAuth(Context context, AlertDialog.Builder builder) {
        View v = View.inflate(context, R.layout.dialog_login, null);
        ImageView vk = (ImageView) v.findViewById(R.id.imageVK);
        vk.setOnClickListener(this);
        ImageView google = (ImageView) v.findViewById(R.id.imageGoogle);
        google.setOnClickListener(this);
        mAuthDialog = builder.setView(v)
                .setCancelable(false)
                .setMessage(null)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                    System.exit(0);
                })
                .setTitle(R.string.dialog_auth_title)
                .create();
        mAuthDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                if (mSharedPreferences.getInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_NONE) == App.KEY_AUTH_NONE)
                    showDialogAuth(mContext, mBuilder);
                else {
                    startActivity(mNavigationIntent);
                    finish();
                }
                break;
            case R.id.imageVK:
                AuthHelper.loginVK(mContext);
                mAuthDialog.cancel();
                break;
            case R.id.imageGoogle:
                AuthHelper.loginGoogle(mContext);
                mAuthDialog.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                mAuthDialog.cancel();
                finish();
                startActivity(mNavigationIntent);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_VK);
                editor.putString(App.KEY_TOKEN, res.accessToken);
                editor.putString(App.KEY_VK_ID, res.userId);
                editor.apply();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(mContext, error.errorCode + "\n" + error.errorMessage, Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG_SPLASH_ACTIVITY, "activity destroyed");
        super.onDestroy();
    }
}
