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

import me.kaidul.uhunt.CompetitiveProgramming.SubChapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class SubSubChaptersListFragment extends SherlockListFragment {

	final static String POSITIONS = "positions";
	ArrayList<String> items = new ArrayList<String>();
	int mPosition1 = -1, mPosition2 = -1;
	ArrayAdapter<String> mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState != null) {
			int[] values = savedInstanceState.getIntArray(POSITIONS);
			mPosition1 = values[0];
			mPosition2 = values[1];
		}
		mAdapter = new ArrayAdapter<String>(getSherlockActivity(), R.layout.list_layout,
				items);
		setListAdapter(mAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
		Bundle args = getArguments();
		if (args != null) {
			int[] values = args.getIntArray(POSITIONS);
			mPosition1 = values[0];
			mPosition2 = values[1];
			updateList(mPosition1, mPosition2);
		} else if (mPosition1 != -1 && mPosition2 != -1) {
			updateList(mPosition1, mPosition2);
		}
		if (getFragmentManager().findFragmentById(R.id.sub_category_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		getListView().setItemChecked(position, true);
		Intent intent = new Intent(getSherlockActivity(),
				ProblemsActivity.class);
		intent.putExtra("positions", new int[] { mPosition1, mPosition2, position });
		startActivity(intent);
	}

	public void updateList(int prev, int position) {
		mPosition1 = prev;
		mPosition2 = position;
		SubChapter instance = CompetitiveProgramming.chapterList
				.get(mPosition1).subchapterList.get(mPosition2);
		items.clear();
		for (int i = 0; i < instance.subsubchapterList.size(); i++) {
			items.add(instance.subsubchapterList.get(i).subSubChapterTitle);
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putIntArray(POSITIONS, new int[] { mPosition1, mPosition2 });
	}
}
