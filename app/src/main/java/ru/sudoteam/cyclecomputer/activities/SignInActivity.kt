package ru.sudoteam.cyclecomputer.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.sudoteam.cyclecomputer.R
import ru.sudoteam.cyclecomputer.app.App
import ru.sudoteam.cyclecomputer.app.accounts.AuthHelper

class SignInActivity : BaseActivity(), View.OnClickListener {

    private var mNavigationIntent: Intent? = null
    private var mSharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        mNavigationIntent = Intent(mContext, MainActivity::class.java)
        mSharedPreferences = getSharedPreferences(App.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        findViewById(R.id.button_vk)?.setOnClickListener(this)
        findViewById(R.id.button_google)?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        v.isEnabled = false
        when (v.id) {
            R.id.button_vk -> {
                AuthHelper.loginVK(mContext)
            }
            R.id.button_google -> {
                AuthHelper.loginGoogle(mContext)
            }
        }
        v.isEnabled = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
            override fun onResult(res: VKAccessToken) {
                finish()
                startActivity(mNavigationIntent)
                val editor = mSharedPreferences!!.edit()
                with(editor) {
                    putInt(ru.sudoteam.cyclecomputer.app.App.KEY_AUTH_TYPE, ru.sudoteam.cyclecomputer.app.App.KEY_AUTH_VK)
                    putString(ru.sudoteam.cyclecomputer.app.App.KEY_TOKEN, res.accessToken)
                    putString(ru.sudoteam.cyclecomputer.app.App.KEY_VK_ID, res.userId)
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
}
