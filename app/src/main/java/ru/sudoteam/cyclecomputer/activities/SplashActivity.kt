package ru.sudoteam.cyclecomputer.activities

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.sudoteam.cyclecomputer.R
import ru.sudoteam.cyclecomputer.app.App
import ru.sudoteam.emulator.activities.Emulator
import java.util.*

class SplashActivity : BaseActivity() {

    private var mBuilder: AlertDialog.Builder? = null
    private var mSharedPreferences: SharedPreferences? = null

    private var mTimer: Timer? = null

    private var mBluetoothReceiver: BroadcastReceiver? = null

    class Item(val text: String, val icon: Int?) {
        override fun toString(): String {
            return text
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharedPreferences = getSharedPreferences(App.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        setContentView(R.layout.activity_splash)
        setStatusBarColor(android.R.color.background_dark)
        mBluetoothReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    if (BluetoothAdapter.STATE_ON == state) {
                        selectModeDialog(mBuilder!!)
                    }
                }
            }
        }
        registerReceiver(mBluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        delay()
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(color)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(mBluetoothReceiver)
        mTimer!!.cancel()
        mTimer = null
        super.onDestroy()
    }

    private fun delay() {
        mTimer = Timer()
        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (check()) {
                        selectModeDialog(mBuilder!!)
                    }
                }
            }
        }, 2000)
    }

    private fun check(): Boolean {
        mBuilder = AlertDialog.Builder(mContext)
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showDialogLENotSupport(mBuilder!!)
            return false
        }
        if (!adapter.isEnabled) {
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            finish()
            System.exit(0)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun showDialogLENotSupport(builder: AlertDialog.Builder) {
        with(builder) {
            setTitle(R.string.dialog_bt_title)
            setMessage(R.string.dialog_bt_message_not_support)
            setCancelable(false)
            setPositiveButton(android.R.string.ok) { dialog, which -> System.exit(0) }
            create().show()
        }
    }

    private fun selectModeDialog(builder: AlertDialog.Builder) {
        val items = arrayOf(Item(getString(R.string.mode_client), R.mipmap.ic_baker), Item(getString(R.string.mode_emulator), R.mipmap.ic_displays))
        val adapter = object : ArrayAdapter<Item>(this,
                android.R.layout.select_dialog_item,
                android.R.id.text1, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                Log.d("adapter dialog ", v.toString())
                val tv = v.findViewById(android.R.id.text1) as TextView
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon!!, 0, 0, 0)
                val dp5 = (5 * resources.displayMetrics.density + 0.5f).toInt()
                tv.compoundDrawablePadding = dp5
                return v
            }
        }
        with(builder) {
            setTitle(R.string.title_app_mode)
            setIcon(android.R.drawable.btn_star)
            setCancelable(false)
            setMessage(null)
            setAdapter(adapter) { dialog, which ->
                when (which) {
                    0 -> clientActivity()
                    1 -> startActivity(Intent(mContext, Emulator::class.java))
                }
                mContext.finish()
            }
            create().show()
        }
    }

    private fun clientActivity() {
        if (mSharedPreferences!!.getInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_NONE) == App.KEY_AUTH_NONE)
            startActivity(Intent(mContext, SignInActivity::class.java))
        else {
            startActivity(Intent(mContext, MainActivity::class.java))
        }
        finish()
    }

}