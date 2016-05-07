package ru.sudoteam.cyclecomputer.activities

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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.sudoteam.cyclecomputer.R
import ru.sudoteam.cyclecomputer.app.App
import ru.sudoteam.cyclecomputer.app.accounts.AuthHelper
import ru.sudoteam.emulator.activities.Emulator
import java.util.*

class SplashActivity : BaseActivity(), View.OnClickListener {

    private var mBuilder: AlertDialog.Builder? = null
    private var mAuthDialog: AlertDialog? = null
    private var mNavigationIntent: Intent? = null
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
        mNavigationIntent = Intent(mContext, MainActivity::class.java)
        Toast.makeText(this, "Toast From Kotlin", Toast.LENGTH_LONG).show()
        mSharedPreferences = getSharedPreferences(App.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        setContentView(R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(android.R.color.transparent)
        }
        mBluetoothReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                    val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                    if (BluetoothAdapter.STATE_ON == state) {
                        clientActivity()
                    }
                }
            }
        }
        mTimer = Timer()
        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (check()) {
                        selectModeDialog(mBuilder!!)
                    }
                }
            }
        }, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                mAuthDialog!!.cancel()
                finish()
                startActivity(mNavigationIntent)
                val editor = mSharedPreferences!!.edit()
                with(editor) {
                    putInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_VK)
                    putString(App.KEY_TOKEN, res.accessToken)
                    putString(App.KEY_VK_ID, res.userId)
                    apply()
                }
            }

            override fun onError(error: VKError) {
                Toast.makeText(mContext, error.errorCode.toString() + "\n" + error.errorMessage, Toast.LENGTH_LONG).show()
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStart() {
        registerReceiver(mBluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(mBluetoothReceiver)
        mTimer!!.cancel()
        mTimer = null
        super.onStop()
    }

    private fun check(): Boolean {
        mBuilder = AlertDialog.Builder(mContext)
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showDialogLENotSupport(mBuilder!!)
            return false
        }
        if (!adapter.isEnabled) {
            showDialogEnableBT(mBuilder!!, adapter)
            return false
        }
        return true
    }

    private fun showDialogEnableBT(builder: AlertDialog.Builder, adapter: BluetoothAdapter) {
        with(builder) {
            setTitle(R.string.dialog_bt_title)
            setIcon(android.R.drawable.stat_sys_data_bluetooth)
            setMessage(R.string.dialog_bt_message_enable)
            setPositiveButton(android.R.string.ok) { dialog, which ->
                adapter.enable()
                dialog.cancel()
            }
            setNegativeButton(android.R.string.cancel) { dialog, which -> System.exit(0) }
            create().show()
        }
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

    private fun showDialogAuth(context: Context, builder: AlertDialog.Builder) {
        val v = View.inflate(context, R.layout.dialog_login, null)
        val vk = v.findViewById(R.id.imageVK) as ImageView
        vk.setOnClickListener(this)
        val google = v.findViewById(R.id.imageGoogle) as ImageView
        google.setOnClickListener(this)
        mAuthDialog = with(builder) {
            setView(v)
            setCancelable(false)
            setMessage(null)
            setNegativeButton(android.R.string.cancel) { dialog, which -> System.exit(0) }
            setTitle(R.string.dialog_auth_title)
            return@with builder.create()
        }
        mAuthDialog!!.show()
    }

    private fun clientActivity() {
        if (mSharedPreferences!!.getInt(App.KEY_AUTH_TYPE, App.KEY_AUTH_NONE) == App.KEY_AUTH_NONE)
            showDialogAuth(mContext, mBuilder!!)
        else {
            startActivity(mNavigationIntent)
            finish()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageVK -> AuthHelper.loginVK(mContext)
            R.id.imageGoogle -> AuthHelper.loginGoogle(mContext)
        }
        mAuthDialog!!.cancel()
    }
}