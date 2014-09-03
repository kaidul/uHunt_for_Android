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
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.gson.stream.JsonReader;

public class RankList extends SherlockListFragment {

	RankListAdapter adapter = null;
	SharedPreferences prefs;
	ArrayList<HashMap<String, String>> ranklist = new ArrayList<HashMap<String, String>>();
	private GetRankListTask rankListTask = null;

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ConnectionDetector cd = new ConnectionDetector(getSherlockActivity());
		if (cd.isConnectingToInternet()) {
			prefs = getSherlockActivity().getSharedPreferences(
					CommonUtils.PREFERENCE_NAME, 0);
			rankListTask = new GetRankListTask();
			rankListTask.execute(CommonUtils.RANKLIST_URL
					+ prefs.getString(CommonUtils.KEY_USER_ID,
							CommonUtils.DEFAULT_USER_ID) + "/10/10");
		} else {
			MainActivity.networkAvailabilityNotice(getSherlockActivity());
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	protected class GetRankListTask extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			InputStreamReader isr = new JSONDownloader()
					.getJSONStringFromUrl(params[0]);
			ArrayList<HashMap<String, String>> result = null;
			if (isr != null) {
				result = new ArrayList<HashMap<String, String>>();
				JsonReader reader = new JsonReader(isr);
				try {
					reader.beginArray();
					while (reader.hasNext()) {
						HashMap<String, String> map = new HashMap<String, String>();
						reader.beginObject();
						if (reader.nextName().equals(
								CommonUtils.KEY_SUBMISSION_RANK)) {
							map.put(CommonUtils.KEY_SUBMISSION_RANK,
									reader.nextString());
						}
						reader.skipValue();
						reader.skipValue();
						reader.skipValue();
						reader.skipValue();
						reader.skipValue();
						map.put(CommonUtils.KEY_NAME, reader.nextString());
						reader.skipValue();
						map.put(CommonUtils.KEY_RANK_USERNAME,
								reader.nextString());
						reader.skipValue();
						map.put(CommonUtils.KEY_RANK_AC, reader.nextString());
						reader.skipValue();
						map.put(CommonUtils.KEY_RANK_NOS, reader.nextString());
						while (reader.hasNext())
							reader.skipValue();
						result.add(map);
						reader.endObject();
					}
					reader.endArray();
				} catch (IOException e) {
					if (CommonUtils.isDebuggable) {
						Log.e("GSON Parser",
								"Error parsing data " + e.toString());
					}
					result = null;
				} finally {
					try {
						reader.close();
					} catch (IOException e) {

					}
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			if (result != null) {
				adapter = new RankListAdapter(getSherlockActivity(), result);
				setListAdapter(adapter);
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (rankListTask != null
				&& rankListTask.getStatus() != AsyncTask.Status.FINISHED) {
			rankListTask.cancel(true);
		}
	}
}