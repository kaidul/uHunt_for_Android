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

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RankListAdapter extends BaseAdapter {
	private Context mContex;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public RankListAdapter(Context context,
			ArrayList< HashMap<String, String> > data) {
		this.mContex = context;
		this.data = data;
		inflater = (LayoutInflater) mContex
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.ranklist, null);
		RelativeLayout firstRow = (RelativeLayout) vi
				.findViewById(R.id.first_row);
		TextView nameAndUsername = (TextView) firstRow
				.findViewById(R.id.name_username);
		TextView rank = (TextView) firstRow.findViewById(R.id.rank);
		RelativeLayout secondRow = (RelativeLayout) vi
				.findViewById(R.id.second_row);
		TextView solvedAndSubmission = (TextView) secondRow
				.findViewById(R.id.solved_and_submissions);

		HashMap<String, String> submissionInfo = new HashMap<String, String>();
		submissionInfo = data.get(position);

		nameAndUsername.setText(submissionInfo.get(CommonUtils.KEY_NAME) + " ("
				+ submissionInfo.get(CommonUtils.KEY_RANK_USERNAME) + ")");
		String text = "Solved : " + submissionInfo.get(CommonUtils.KEY_RANK_AC)
				+ ", Submissions : "
				+ submissionInfo.get(CommonUtils.KEY_RANK_NOS);
		solvedAndSubmission.setText(text);
		String ranking = submissionInfo.get(CommonUtils.KEY_SUBMISSION_RANK)
				.equals("-1") ? "" : submissionInfo
				.get(CommonUtils.KEY_SUBMISSION_RANK);
		rank.setText(ranking);

		return vi;
	}

}
