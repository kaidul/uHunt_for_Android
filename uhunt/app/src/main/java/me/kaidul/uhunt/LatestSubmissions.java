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
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LatestSubmissions extends ListFragment {

	SharedPreferences prefs;
	LatestSubmissionsAdapter adapter = null;
	ArrayList<HashMap<String, String>> submissions = new ArrayList<HashMap<String, String>>();
	private GetSubmissionsListTask submissionTask = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ConnectionDetector cd = new ConnectionDetector(getActivity());
		if (cd.isConnectingToInternet()) {
			prefs = getActivity().getSharedPreferences(
					CommonUtils.PREFERENCE_NAME, 0);
			submissionTask = new GetSubmissionsListTask();
			submissionTask.execute(CommonUtils.USER_SUBMISSION_URL
					+ prefs.getString(CommonUtils.KEY_USER_ID, "339") + "/10");
		} else {
			MainActivity.networkAvailabilityNotice(getActivity());
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	protected class GetSubmissionsListTask extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			InputStreamReader isr = new JSONDownloader()
					.getJSONStringFromUrl(params[0]);
			ArrayList<HashMap<String, String>> result = null;
			if (isr != null) {
				result = new ArrayList<>();
				MainActivity activity = (MainActivity) getActivity();
				JsonReader reader = new JsonReader(isr);
				try {
					reader.beginObject();
					reader.nextName(); // name
					reader.skipValue();
					reader.nextName(); // uname
					reader.skipValue();
					String subs = reader.nextName(); // subs
					if (subs.equals(CommonUtils.KEY_SUBMISSION)) {
						reader.beginArray();
						while (reader.hasNext()) {
							HashMap<String, String> map = new HashMap<String, String>();
							reader.beginArray();
							reader.skipValue(); // submission id
							String txt = "Latest problem";
							Problems obj = MainActivity.problems.get(reader
									.nextInt());
							if (obj != null) {
								txt = obj.getProblemsInfo();
							}
							map.put(CommonUtils.KEY_PROBLEM_ID, txt);
							Verdict verdict = activity.verdicts.get(reader
									.nextString()); // verdict id
							map.put(CommonUtils.KEY_VERDICT_COLOR,
									verdict.verdictColorHex);
							map.put(CommonUtils.KEY_VERDICT_ID, verdict.name);
							Double execTime = reader.nextDouble() / 1000; // execution
																			// time
							map.put(CommonUtils.KEY_RUNTIME,
									execTime.toString() + "s");
							reader.skipValue(); // submission time
							map.put(CommonUtils.KEY_LANGUAGE_ID,
									activity.languageCode.get(reader
											.nextString())); // lanugage
																// id
							map.put(CommonUtils.KEY_SUBMISSION_RANK,
									reader.nextString()); // rank
							result.add(map);
							reader.endArray();
						}
						reader.endArray();
					}
					reader.endObject();
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
						// error closing Json reader
					}
				}
			}
			if (result != null)
				Collections.reverse(result);
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			if (result != null) {
				adapter = new LatestSubmissionsAdapter(getActivity(),
						result);
				setListAdapter(adapter);
//				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (submissionTask != null
				&& submissionTask.getStatus() != AsyncTask.Status.FINISHED) {
			submissionTask.cancel(true);
		}
	}
}
