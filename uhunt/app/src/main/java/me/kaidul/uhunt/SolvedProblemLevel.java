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

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.achartengine.GraphicalView;

public class SolvedProblemLevel extends Fragment {

	int[] verdictSeries = new int[12];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.submission_statistics,
				container, false);
		SharedPreferences prefs = getActivity().getSharedPreferences(
				CommonUtils.PREFERENCE_NAME, 0);
		for (int i = 1; i <= 10; i++) {
			verdictSeries[i - 1] = prefs.getInt(CommonUtils.aliases[i], 0);
		}
		return rootView;
	}

	@Override
	public void onStart() {
		super.onStart();
		LevelGraphDrawer barGraph = new LevelGraphDrawer(
				getString(R.string.solved_problem_level),
				MainActivity.getDeviceWidth(getActivity()) - 20,
				verdictSeries);
		GraphicalView gView = barGraph.getView(getActivity());
		ViewGroup vg = (ViewGroup) getActivity().findViewById(
				R.id.bar_graph);
		vg.addView(gView);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LevelGraphDrawer barGraph = new LevelGraphDrawer(
				getString(R.string.solved_problem_level),
				MainActivity.getDeviceWidth(getActivity()) - 20,
				verdictSeries);
		GraphicalView gView = barGraph.getView(getActivity());
		ViewGroup vg = (ViewGroup) getActivity().findViewById(
				R.id.bar_graph);
		vg.removeAllViews();
		vg.refreshDrawableState();
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
