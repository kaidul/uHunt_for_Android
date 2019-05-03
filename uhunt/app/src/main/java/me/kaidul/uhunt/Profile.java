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

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Iterator;

@SuppressLint("UseSparseArrays")
public class Profile extends Fragment {

	SharedPreferences prefs = null;
	private TextView solvedProblems, failedToSolve, solvedAndSubmissions,
			nameAndusername, failedTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.profile, container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		solvedProblems = (TextView) getActivity().findViewById(
				R.id.solved_problem_list);
		failedToSolve = (TextView) getActivity().findViewById(
				R.id.tried_but_failure_list);
		solvedAndSubmissions = (TextView) getActivity().findViewById(
				R.id.solved_and_submissions);
		nameAndusername = (TextView) getActivity().findViewById(
				R.id.name_and_username);
		failedTitle = (TextView) getActivity().findViewById(
				R.id.tried_but_failure);
		prefs = getActivity().getSharedPreferences(
				CommonUtils.PREFERENCE_NAME, 0);

		// putting
		nameAndusername.setText(MainActivity.name
				+ " ("
				+ MainActivity.userName + ")");
		solvedAndSubmissions.setText("Solved : "
				+ MainActivity.solvedLists.size() + ", Submissions : "
				+ MainActivity.totalSubmission);
		failedTitle.setText("Tried but not yet solved: "
				+ MainActivity.failedLists.size());
		Problems obj = null;
		for (Iterator<Integer> it = MainActivity.solvedLists.iterator(); it
				.hasNext();) {
			obj = MainActivity.problems.get(it.next());
			if (obj != null) {
				solvedProblems.append(String.format("%7s", obj.problemNo));
			}
		}

		for (Iterator<Integer> it = MainActivity.failedLists.iterator(); it
				.hasNext();) {
			obj = MainActivity.problems.get(it.next());
			if (obj != null) {
				failedToSolve.append(String.format("%7s", obj.problemNo));
			}
		}
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
