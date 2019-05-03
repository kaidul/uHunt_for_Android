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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

public class ProblemsActivity extends FragmentActivity implements
		ProblemListFragment.OnProblemSelectListener {

	static int position1, position2, position3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent != null) {
			int[] values = intent.getIntArrayExtra("positions");
			position1 = values[0];
			position2 = values[1];
			position3 = values[2];
		}
		setContentView(R.layout.problems);
		if (findViewById(R.id.fragment_container) != null) {
			if (savedInstanceState != null) {
				return;
			}
			ProblemListFragment problemListFrag = new ProblemListFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, problemListFrag).commit();
		}
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void OnProblemSelected(int problemNo) {
		String url = CommonUtils.PROBLEM_URL + problemNo / 100 + "/"
				+ problemNo + ".html";
		ProblemDetailsFragment problemDetailsFrag = (ProblemDetailsFragment) getSupportFragmentManager()
				.findFragmentById(R.id.problem_details_fragment);
		if (problemDetailsFrag != null) {
			problemDetailsFrag.updateUrl(url);
		} else {
			ProblemDetailsFragment problemDetailsFragment = new ProblemDetailsFragment();
			Bundle args = new Bundle();
			args.putString(ProblemDetailsFragment.URL, url);
			args.putString(ProblemDetailsFragment.Title, "UVa " + problemNo);
			problemDetailsFragment.setArguments(args);
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction
					.replace(R.id.fragment_container, problemDetailsFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}
}
