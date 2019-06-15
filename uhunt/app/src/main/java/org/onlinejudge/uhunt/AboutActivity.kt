/*
 * uHunt for Android - The most comprehensive Android app for uHunt and Competitive programming
 * Copyright (C) 2013 Kaidul Islam
 * 
 * This file is part of uHunt for Android.

 * uHunt for Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * uHunt for Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with uHunt for Android.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.onlinejudge.uhunt

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import kotlinx.android.synthetic.main.about_webview.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_webview)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        about_web_view.loadUrl(CommonUtils.ABOUT_HTML)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.rate_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
            }
            R.id.rate -> {
                openAppInPlayStoreForRating()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAppInPlayStoreForRating() {
        val uri = Uri.parse(getString(R.string.url_market_app) + packageName)
        val goToMarketIntent = Intent(Intent.ACTION_VIEW, uri)

        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= 21) {
            flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        goToMarketIntent.addFlags(flags)

        try {
            startActivity(goToMarketIntent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.url_playstore_web) + packageName))

            startActivity(intent)
        }
    }
}
