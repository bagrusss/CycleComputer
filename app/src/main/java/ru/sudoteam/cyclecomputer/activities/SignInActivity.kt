package ru.sudoteam.cyclecomputer.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.sudoteam.cyclecomputer.R
import ru.sudoteam.cyclecomputer.app.App
import ru.sudoteam.cyclecomputer.app.accounts.Account
import ru.sudoteam.cyclecomputer.app.accounts.AccountGoogle
import ru.sudoteam.cyclecomputer.app.accounts.Error

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
            R.id.button_google -> App.setAccount(AccountGoogle())
            R.id.button_vk -> {
            }
            else -> return
        }
        App.getAccount().login(this)
        v.isEnabled = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!App.getAccount().isLoginOK(requestCode, resultCode, data, object : Account.LoginCallback {
            override fun onOK() {
                finish()
                startActivity(mNavigationIntent)
            }

            override fun onError(error: Error) {
                Toast.makeText(mContext, error.msg, Toast.LENGTH_LONG).show()
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}
