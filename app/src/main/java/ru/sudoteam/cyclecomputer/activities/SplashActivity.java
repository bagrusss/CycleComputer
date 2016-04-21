package ru.sudoteam.cyclecomputer.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.Timer;
import java.util.TimerTask;

import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;
import ru.sudoteam.cyclecomputer.app.AuthHelper;
import ru.sudoteam.emulator.activities.Emulator;

public class SplashActivity extends CycleBaseActivity implements View.OnClickListener {

    private AlertDialog.Builder mBuilder;
    private AlertDialog mAuthDialog;
    private Intent mNavigationIntent;
    private SharedPreferences mSharedPreferences;

    private BroadcastReceiver mBluetoothReceiver;

    private Timer mTimer;

    public static class Item {
        public final String text;
        public final int icon;

        public Item(String text, Integer icon) {
            this.text = text;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(App.SHARED_PREFERENCES, MODE_PRIVATE);
        mNavigationIntent = new Intent(mContext, MainActivity.class);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        mBluetoothReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                            BluetoothAdapter.ERROR);
                    if (BluetoothAdapter.STATE_ON == state) {
                        clientActivity();
                    }
                }
            }
        };
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
    protected void onStart() {
        registerReceiver(mBluetoothReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (check()) {
                        selectModeDialog(mBuilder);
                    }
                });
            }
        }, 1000);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBluetoothReceiver);
        mTimer.cancel();
        mTimer = null;
        super.onStop();
    }

    private boolean check() {
        mBuilder = new AlertDialog.Builder(mContext);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showDialogLENotSupport(mBuilder);
            return false;
        }
        if (!adapter.isEnabled()) {
            showDialogEnableBT(mBuilder, adapter);
            return false;
        }
        return true;
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
                .create()
                .show();
    }

    private void showDialogLENotSupport(AlertDialog.Builder builder) {
        builder.setTitle(R.string.dialog_bt_title)
                .setMessage(R.string.dialog_bt_message_not_support)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    System.exit(0);
                });
        builder.create().show();
    }

    private void selectModeDialog(AlertDialog.Builder builder) {
        final Item items[] = new Item[]{
                new Item(getString(R.string.mode_client), R.mipmap.ic_baker),
                new Item(getString(R.string.mode_emulator), R.mipmap.ic_displays)
        };
        ListAdapter adapter = new ArrayAdapter<Item>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                items
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView) v.findViewById(android.R.id.text1);
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);
                return v;
            }
        };
        builder.setTitle(R.string.title_app_mode)
                .setIcon(android.R.drawable.btn_star)
                .setMessage(null)
                .setCancelable(false)
                .setAdapter(adapter, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            clientActivity();
                            break;
                        case 1:
                            startActivity(new Intent(mContext, Emulator.class));
                            break;
                    }
                    mContext.finish();
                })
                .create()
                .show();
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

    private void clientActivity() {
        if (mSharedPreferences.getInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_NONE) == App.KEY_AUTH_NONE)
            showDialogAuth(mContext, mBuilder);
        else {
            startActivity(mNavigationIntent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageVK:
                AuthHelper.loginVK(mContext);
                mAuthDialog.cancel();
                break;
            case R.id.imageGoogle:
                AuthHelper.loginGoogle(mContext);
                mAuthDialog.cancel();
        }
    }

}