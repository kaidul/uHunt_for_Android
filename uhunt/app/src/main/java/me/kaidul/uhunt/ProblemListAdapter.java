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

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProblemListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<ContentValues> data;
	private static LayoutInflater inflater = null;

	public ProblemListAdapter(Activity a, ArrayList<ContentValues> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0).get(ProblemListFragment.PROBLEM_NO);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.problem_list_layout, null);
		ImageView signIcon = (ImageView) vi.findViewById(R.id.verdict_sign);
		TextView title = (TextView) vi.findViewById(R.id.problem_title);
		ImageView starIcon = (ImageView) vi.findViewById(R.id.star_icon);
		
		ContentValues values = new ContentValues();
		values = data.get(position);
		Integer verdictSign = values.getAsInteger(ProblemListFragment.VERDICT_SIGN);
		if (verdictSign != null) {
			if (verdictSign == 1) {
				signIcon.setImageResource(R.drawable.right);
			} else if (verdictSign == 2) {
				signIcon.setImageResource(R.drawable.cross);
			}
		} else {
			// nothing
		}
		title.setText(values.get(ProblemListFragment.PROBLEM_NO) + " "
				+ values.get(ProblemListFragment.PROBLEM_TITLE));
		if (values.getAsBoolean(ProblemListFragment.STARRED)) {
			starIcon.setImageResource(R.drawable.star);
		}
		
		return vi;
	}

}
