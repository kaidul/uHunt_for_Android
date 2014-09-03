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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackFormActivity extends SherlockActivity {
	Button btnOK;
	EditText txtMessage;
	TextView text;
	String appID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_form);
		try {
			appID = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).packageName;
		} catch (NameNotFoundException e) {

		}
		text = (TextView) findViewById(R.id.text);
		text.setText("If you have any suggestions regarding this app or notice any bugs, please let us know about it. We welcome suggestions, questions, and best of all, brutal honesty.");
		btnOK = (Button) findViewById(R.id.btnOK);
		txtMessage = (EditText) findViewById(R.id.etMessage);
		txtMessage.requestFocus();
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String to = "kaidulislam90@gmail.com";
				String anotherTo = "ikaidul@yahoo.com";
				String subject = "Feedback on uHunt";
				String message = txtMessage.getText().toString();
				Intent mail = new Intent(Intent.ACTION_SEND);
				mail.putExtra(Intent.EXTRA_EMAIL, new String[] { to, anotherTo });
				mail.putExtra(Intent.EXTRA_SUBJECT, subject);
				mail.putExtra(Intent.EXTRA_TEXT, message);
				mail.setType("message/rfc822");
				startActivity(Intent.createChooser(mail, "Send Feedback via:"));
			}
		});
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