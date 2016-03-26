package ru.sudoteam.cyclecomputer.activities;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import ru.sudoteam.cyclecomputer.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartButton;
    private Context mContext = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.background_dark));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showDialogLENotSupport(builder);
            return;
        }
        if (!adapter.isEnabled()) {
            showDialogEnableBT(builder, adapter);
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
            /*NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_menu_info_details)
                    .setContentTitle("Bluetooth 4.0 LE")
                    .setContentText("Bluetooth 4.0 LE supports!")
                    .setSound(alarmSound);
            nm.notify(2, builder.build());*/
        builder.setTitle(R.string.dialog_bt_title)
                .setMessage(R.string.dialog_bt_message_not_support)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    System.exit(0);
                });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
            finish();
        }
    }
}
