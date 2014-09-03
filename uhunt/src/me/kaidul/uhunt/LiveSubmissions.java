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
import java.util.Collections;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.progressfragment.SherlockProgressFragment;
import com.google.gson.stream.JsonReader;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

public class LiveSubmissions extends SherlockProgressFragment {

	private View mContentView;
	PullToRefreshListView list;
	LiveSubmissionAdapter adapter = null;
	private String sinceID;
	ArrayList<HashMap<String, String>> submissions = new ArrayList<HashMap<String,String>>();
	private GetLiveSubmissionsTask liveSubmissionTask = null;
	private GetLatestLiveTask latestLiveTask = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.live_submission_fragment,
				container, false);
		list = (PullToRefreshListView) mContentView.findViewById(R.id.list);
		list.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				latestLiveTask = new GetLatestLiveTask();
				latestLiveTask.execute(CommonUtils.LIVE_SUBMISSION_URL
						+ sinceID);
			}
		});
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setContentView(mContentView);
		setContentShown(false);
		setEmptyText(R.string.empty);
		ConnectionDetector cd = new ConnectionDetector(getSherlockActivity());
		if (cd.isConnectingToInternet()) {
			liveSubmissionTask = new GetLiveSubmissionsTask();
			liveSubmissionTask.execute(CommonUtils.LIVE_SUBMISSION_URL
						+ "0");
		} else {
			MainActivity.networkAvailabilityNotice(getSherlockActivity());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	protected class GetLiveSubmissionsTask extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
			InputStreamReader isr = new JSONDownloader().getJSONStringFromUrl(params[0]);
			ArrayList<HashMap<String, String>> result = null;
			if (isr != null) {
				result = new ArrayList<HashMap<String, String>>();
				MainActivity activity = (MainActivity) getSherlockActivity();
				JsonReader reader = new JsonReader(isr);
				try {
					reader.beginArray();
					while (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							reader.nextName(); // id
							reader.skipValue();
							reader.nextName(); // type
							if (reader.nextString().equals("lastsubs")) {
								String message = reader.nextName(); // msg
								if (message.equals(CommonUtils.KEY_MESSAGE)) {
									reader.beginObject();
									HashMap<String, String> map = new HashMap<String, String>();
									reader.skipValue(); // sid
									reader.skipValue();
									reader.skipValue(); // uid
									reader.skipValue();
									String problemID = reader.nextName(); // pid
									if (problemID
											.equals(CommonUtils.KEY_PROBLEM_ID)) {
										String txt = "Latest problem";
										Problems obj = MainActivity.problems
												.get(reader.nextInt());
										if (obj != null) {
											txt = obj.getProblemsInfo();
										}
										map.put(CommonUtils.KEY_PROBLEM_ID, txt);
									}
									String verdictID = reader.nextName(); // verdict
									if (verdictID
											.equals(CommonUtils.KEY_VERDICT_ID)) {
										Verdict verdict = activity.verdicts
												.get(reader.nextString());
										map.put(CommonUtils.KEY_VERDICT_COLOR,
												verdict.verdictColorHex);
										map.put(CommonUtils.KEY_VERDICT_ID,
												verdict.name);
									}
									String languageID = reader.nextName(); // lang
									if (languageID
											.equals(CommonUtils.KEY_LANGUAGE_ID)) {
										map.put(CommonUtils.KEY_LANGUAGE_ID,
												activity.languageCode
														.get(reader
																.nextString()));
									}
									String execTime = reader.nextName(); // run
									if (execTime
											.equals(CommonUtils.KEY_RUNTIME)) {
										Double runTime = reader.nextDouble() / 1000;
										map.put(CommonUtils.KEY_RUNTIME,
												runTime.toString() + "s");
									}
									reader.skipValue(); // mem
									reader.skipValue();
									String rank = reader.nextName(); // rank
									if (rank.equals(CommonUtils.KEY_SUBMISSION_RANK)) {
										map.put(CommonUtils.KEY_SUBMISSION_RANK,
												reader.nextString());
									}
									reader.skipValue(); // sbt
									reader.skipValue();
									if (reader.nextName().equals( // name
											CommonUtils.KEY_NAME)) {
										map.put(CommonUtils.KEY_NAME,
												reader.nextString());
									}
									if (reader.nextName().equals( // uname
											CommonUtils.KEY_USERNAME)) {
										map.put(CommonUtils.KEY_USERNAME,
												reader.nextString());
									}
									result.add(map);
									reader.endObject();
								}
							}

						}
						reader.endObject();
					}
					reader.endArray();
				} catch (IOException e) {
					result = null;
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						
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
				submissions = result;
				adapter = new LiveSubmissionAdapter(getSherlockActivity(),
						submissions);
				list.setAdapter(adapter);
				setContentEmpty(false);
				setContentShown(true);
			} 
		}
	}

	protected class GetLatestLiveTask extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
			InputStreamReader isr = new JSONDownloader().getJSONStringFromUrl(params[0]);
			ArrayList<HashMap<String, String>> result = null;
			if (isr != null) {
				MainActivity activity = (MainActivity) getSherlockActivity();
				JsonReader reader = new JsonReader(isr);
				result = new ArrayList<HashMap<String, String>>();
				try {
					reader.beginArray();
					while (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							reader.nextName(); // id
							reader.skipValue();
							reader.nextName(); // type
							if (reader.nextString().equals("lastsubs")) {
								String message = reader.nextName(); // msg
								if (message.equals(CommonUtils.KEY_MESSAGE)) {
									reader.beginObject();
									HashMap<String, String> map = new HashMap<String, String>();
									reader.skipValue(); // sid
									reader.skipValue();
									reader.skipValue(); // uid
									reader.skipValue();
									String problemID = reader.nextName(); // pid
									if (problemID
											.equals(CommonUtils.KEY_PROBLEM_ID)) {
										String txt = "Latest problem";
										Problems obj = MainActivity.problems
												.get(reader.nextInt());
										if (obj != null) {
											txt = obj.getProblemsInfo();
										}
										map.put(CommonUtils.KEY_PROBLEM_ID, txt);
									}
									String verdictID = reader.nextName(); // verdict
									if (verdictID
											.equals(CommonUtils.KEY_VERDICT_ID)) {
										Verdict verdict = activity.verdicts
												.get(reader.nextString());
										map.put(CommonUtils.KEY_VERDICT_COLOR,
												verdict.verdictColorHex);
										map.put(CommonUtils.KEY_VERDICT_ID,
												verdict.name);
									}
									String languageID = reader.nextName(); // lang
									if (languageID
											.equals(CommonUtils.KEY_LANGUAGE_ID)) {
										map.put(CommonUtils.KEY_LANGUAGE_ID,
												activity.languageCode
														.get(reader
																.nextString()));
									}
									String execTime = reader.nextName(); // run
									if (execTime
											.equals(CommonUtils.KEY_RUNTIME)) {
										Double runTime = reader.nextDouble() / 1000;
										map.put(CommonUtils.KEY_RUNTIME,
												runTime.toString() + "s");
									}
									reader.skipValue(); // mem
									reader.skipValue();
									if (reader.nextName().equals(
											CommonUtils.KEY_SUBMISSION_RANK)) { // rank
										map.put(CommonUtils.KEY_SUBMISSION_RANK,
												reader.nextString());
									}
									reader.skipValue(); // sbt
									reader.skipValue();
									if (reader.nextName().equals( // name
											CommonUtils.KEY_NAME)) {
										map.put(CommonUtils.KEY_NAME,
												reader.nextString());
									}
									if (reader.nextName().equals( // uname
											CommonUtils.KEY_USERNAME)) {
										map.put(CommonUtils.KEY_USERNAME,
												reader.nextString());
									}
									result.add(map);
									reader.endObject();
								}
							}

						}
						reader.endObject();
					}
					reader.endArray();
				} catch (IOException e1) {
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
				for (int i = 0; i < result.size(); i++) {
					submissions.add(0, result.get(i));
				}
				list.onRefreshComplete();
			}
		}

	}

	@Override
	public void onStop() {
		super.onStop();
		if (liveSubmissionTask != null
				&& liveSubmissionTask.getStatus() != AsyncTask.Status.FINISHED) {
			liveSubmissionTask.cancel(true);
		}
		if (latestLiveTask != null
				&& latestLiveTask.getStatus() != AsyncTask.Status.FINISHED) {
			latestLiveTask.cancel(true);
		}
	}
}
