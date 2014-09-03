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

import org.achartengine.GraphicalView;

import com.actionbarsherlock.app.SherlockFragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubmissionStatistics extends SherlockFragment {

	int[] verdictSeries = new int[9];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.submission_statistics,
				container, false);
		SharedPreferences prefs = getSherlockActivity().getSharedPreferences(
				CommonUtils.PREFERENCE_NAME, 0);
		for (int i = 0; i < CommonUtils.alias.length; i++) {
			verdictSeries[i] = prefs.getInt(CommonUtils.alias[i], 0);
		}
		return rootView;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		BarGraphDrawer barGraph = new BarGraphDrawer("Submission Statistics",
				MainActivity.getDeviceWidth(getSherlockActivity()),
				verdictSeries);
		GraphicalView gView = barGraph.getView(getSherlockActivity());
		ViewGroup vg = (ViewGroup) getSherlockActivity().findViewById(
				R.id.bar_graph);
		vg.removeAllViews();
		vg.refreshDrawableState();
		vg.addView(gView);
	}

	@SuppressLint("NewApi")
	@Override
	public void onStart() {
		super.onStart();
		BarGraphDrawer barGraph = new BarGraphDrawer("Submission Statistics",
				MainActivity.getDeviceWidth(getSherlockActivity()),
				verdictSeries);
		GraphicalView gView = barGraph.getView(getSherlockActivity());
		ViewGroup vg = (ViewGroup) getSherlockActivity().findViewById(
				R.id.bar_graph);
		vg.addView(gView);
	}
	
	@Override
	public void onResume() {
		MainActivity.reCreate = true;
		super.onResume();
	}
	
	@Override
	public void onStop() {
		MainActivity.reCreate = false;
		super.onStop();
	}

}
