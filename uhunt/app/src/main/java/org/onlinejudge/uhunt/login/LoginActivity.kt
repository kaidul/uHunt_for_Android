package org.onlinejudge.uhunt.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import org.onlinejudge.uhunt.R
import org.onlinejudge.uhunt.main.MainActivity
import androidx.appcompat.widget.Toolbar


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
            showLoadingIndicatorAndHideView()
            val username = et_username.text.toString()
            loginViewModel.login(username).observe(this, Observer {
                hideLoadingIndicator()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
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
