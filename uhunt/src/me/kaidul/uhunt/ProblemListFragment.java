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
import java.util.List;

import me.kaidul.uhunt.CompetitiveProgramming.ProblemList;
import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class ProblemListFragment extends SherlockListFragment {
	OnProblemSelectListener mCallback;
	final static String STARRED = "is_starred";
	final static String PROBLEM_NO = "problem_no";
	final static String PROBLEM_TITLE = "problem_title";
	final static String VERDICT_SIGN = "verdict_sign";
	
	public interface OnProblemSelectListener {
		public void OnProblemSelected(int problemNo);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<ProblemList> instance = CompetitiveProgramming.chapterList
				.get(ProblemsActivity.position1).subchapterList
				.get(ProblemsActivity.position2).subsubchapterList
				.get(ProblemsActivity.position3).problemList;
		ArrayList<ContentValues> problemProperties = new ArrayList<ContentValues>();
		for (int i = 0; i < instance.size(); i++) {
			ContentValues values = new ContentValues();
			values.put(STARRED, instance.get(i).isStarred);
			values.put(PROBLEM_NO, instance.get(i).problemNo);
			values.put(PROBLEM_TITLE, instance.get(i).problemTitle);
			values.put(VERDICT_SIGN, MainActivity.solvedorTried.get((instance.get(i).problemNo).toString()));
			problemProperties.add(values);
		}
		setListAdapter(new ProblemListAdapter(getSherlockActivity(), problemProperties));
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getFragmentManager()
				.findFragmentById(R.id.problem_details_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnProblemSelectListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnProblemSelectListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		int problemNo = (Integer) getListAdapter().getItem(position);
		mCallback.OnProblemSelected(problemNo);
		getListView().setItemChecked(position, true);
	}
}
