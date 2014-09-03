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

import java.io.IOException;
import java.io.InputStreamReader;

import org.achartengine.GraphicalView;

import com.devspark.progressfragment.SherlockProgressFragment;
import com.google.gson.stream.JsonReader;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProblemStatistics extends SherlockProgressFragment {

	View mContentView;
	int[] verdictSeries = new int[9];
	String name = null, problemN = null, label = null;
	int level = 0;
	private GetProblemStatisticTask problemStatisticsTask = null;
	ConnectionDetector cd = null;

	@Override
	public void onResume() {
		MainActivity.isProblemStatistics = true;
		MainActivity._refresh = true;
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.submission_statistics,
				container, false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateUrl(MainActivity.problemNo);
	}

	protected class GetProblemStatisticTask extends
			AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			InputStreamReader isr = null;
			isr = new JSONDownloader().getJSONStringFromUrl(params[0]);
			if (isr != null) {
				JsonReader reader = new JsonReader(isr);
				String key;
				try {
					reader.beginObject();
					reader.nextName();
					reader.skipValue();
					key = reader.nextName();
					if (key.equals("num")) {
						problemN = reader.nextString();
					}
					key = reader.nextName();
					if (key.equals("title")) {
						name = reader.nextString();
					}
					key = reader.nextName();
					if (key.equals(CommonUtils.KEY_DACU)) {
						level = reader.nextInt();
						label = problemN
								+ " "
								+ name
								+ " (Level: "
								+ ((MainActivity) getSherlockActivity())
										.problemLevel(level) + ")";
					}
					for (int i = 0; i < 6; i++) {
						reader.skipValue();
					}
					key = reader.nextName();
					if (key.equals("sube")) {
						verdictSeries[7] = reader.nextInt();
					}
					for (int i = 0; i < 4; i++) {
						reader.skipValue();
					}
					key = reader.nextName();
					if (key.equals(CommonUtils.KEY_NO_CE)) {
						verdictSeries[5] = reader.nextInt();
					}
					reader.skipValue();
					reader.skipValue();
					key = reader.nextName();
					if (key.equals(CommonUtils.KEY_NO_RE)) {
						verdictSeries[6] = reader.nextInt();
					}
					reader.skipValue();
					reader.skipValue();
					key = reader.nextName();
					if (key.equals(CommonUtils.KEY_NO_TLE)) {
						verdictSeries[3] = reader.nextInt();
					}
					key = reader.nextName();
					if (key.equals("mle")) {
						verdictSeries[4] = reader.nextInt();
					}
					key = reader.nextName();
					if (key.equals("wa")) {
						verdictSeries[2] = reader.nextInt();
					}
					key = reader.nextName();
					if (key.equals("pe")) {
						verdictSeries[1] = reader.nextInt();
					}
					key = reader.nextName();
					if (key.equals("ac")) {
						verdictSeries[0] = reader.nextInt();
					}
					while (reader.hasNext())
						reader.skipValue();
					reader.endObject();
				} catch (IOException e) {

				} finally {
					try {
						reader.close();
					} catch (IOException e) {

					}
				}
				return true;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result == true) {
				BarGraphDrawer barGraph = new BarGraphDrawer(label,
						MainActivity.getDeviceWidth(getSherlockActivity()),
						verdictSeries);
				GraphicalView gView = barGraph.getView(getSherlockActivity());
				ViewGroup vg = (ViewGroup) getSherlockActivity().findViewById(
						R.id.bar_graph);
				vg.removeAllViews();
				vg.refreshDrawableState();
				vg.addView(gView);

				setContentShown(true);
			}
			return;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		BarGraphDrawer barGraph = new BarGraphDrawer(label,
				MainActivity.getDeviceWidth(getSherlockActivity()),
				verdictSeries);
		GraphicalView gView = barGraph.getView(getSherlockActivity());
		ViewGroup vg = (ViewGroup) getSherlockActivity().findViewById(
				R.id.bar_graph);
		vg.removeAllViews();
		vg.refreshDrawableState();
		vg.addView(gView);
	}

	void updateUrl(int problemNo) {
		MainActivity.problemNo = problemNo;
		setContentView(mContentView);
		setContentShown(false);
		cd = new ConnectionDetector(getSherlockActivity());
		if (cd.isConnectingToInternet()) {
			problemStatisticsTask = new GetProblemStatisticTask();
			problemStatisticsTask.execute(CommonUtils.SPECIFIC_PROBLEM_URL
					+ problemNo);
		} else {
			MainActivity.networkAvailabilityNotice(getSherlockActivity());
		}
	}

	@Override
	public void onStop() {
		if (problemStatisticsTask != null
				&& problemStatisticsTask.getStatus() == AsyncTask.Status.FINISHED) {
			problemStatisticsTask.cancel(true);
		}
		MainActivity.isProblemStatistics = false;
		MainActivity._refresh = false;
		super.onStop();
	}

}