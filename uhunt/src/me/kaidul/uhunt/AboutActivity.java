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
package me.kaidul.uhunt;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class AboutActivity extends SherlockActivity {
	String appID;
	private WebView webView;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*setContentView(R.layout.about_activity);
		TextView appVersion = (TextView) findViewById(R.id.version);
		try {
			String versionName = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).versionName;
			appID = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).packageName;
			appVersion.setText("Version " + versionName);
		} catch (NameNotFoundException e) {

		}
		TextView appDetails = (TextView) findViewById(R.id.details);
		String txt = "This is a third-party android application for the popular site uHunt which is a complementary tool for UVa online-judge that keeps statistics, provide selections of problems to solve, and exposes a web API for other web developers to build upon it. This application is powered by uHunt web API and developed by Reytz. <br/><br/> This application is developed to browse & think with problems anywhere you're. Any recommendation or suggestion will be highly appreciated. The target user of this app are not ordinary users, they are problem solvers! So suggestion will be taken with much importance.";
		appDetails.setText(Html.fromHtml(txt));*/
		
		setContentView(R.layout.about_webview);
		webView = (WebView) findViewById(R.id.about_webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(CommonUtils.ABOUT_HTML);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.rate_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private boolean StartActivity(Intent intent) {
		try {
			startActivity(intent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	/*
	public void openBrowserForfacebook(View view) {
		Intent internetIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(getResources().getString(R.string.devs_fb_link)));
		internetIntent.setComponent(new ComponentName("com.android.browser",
				"com.android.browser.BrowserActivity"));
		internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(internetIntent);
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		switch (item.getItemId()) {
		case R.id.rate:
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id=" + appID));
			if (StartActivity(intent) == false) {
				intent.setData(Uri
						.parse("https://play.google.com/store/apps/details?id="
								+ appID));
				if (StartActivity(intent) == false) {
					Toast.makeText(
							this,
							"Could not open Android market, please install the market app.",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
