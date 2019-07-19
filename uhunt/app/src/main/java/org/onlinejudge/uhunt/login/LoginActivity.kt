/*
 * uHunt for Android - The most comprehensive Android app for uHunt and Competitive programming
 *   Copyright (C) 2018 Kaidul Islam, Esraa Ibrahim
 *
 *   This file is part of uHunt for Android.
 *
 *   uHunt for Android is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   uHunt for Android is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with uHunt for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.onlinejudge.uhunt.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import org.onlinejudge.uhunt.R
import org.onlinejudge.uhunt.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.activity_login_title)
        setSupportActionBar(toolbar)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            val username = et_username.text.toString().trim()
            if (TextUtils.isEmpty(username)) {
                til_username.error = getString(R.string.msg_error_username)
            } else {
                showLoadingIndicatorAndHideView()
                loginViewModel.login(username).observe(this, Observer {
                    hideLoadingIndicator()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }

    private fun showLoadingIndicatorAndHideView() {
        progress_circular.visibility = VISIBLE
        login_view.visibility = GONE
    }

    private fun hideLoadingIndicator() {
        progress_circular.visibility = GONE
    }
}
