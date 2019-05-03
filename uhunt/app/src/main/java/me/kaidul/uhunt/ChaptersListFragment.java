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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChaptersListFragment extends ListFragment {

	OnChapterSelectListener mCallback;

	public interface OnChapterSelectListener {
		public void onChapterSelected(int position);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < CompetitiveProgramming.chapterList.size(); i++) {
			items.add(CompetitiveProgramming.chapterList.get(i).chapterTitle);
		}
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.list_layout, items);
		setListAdapter(mAdapter);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getFragmentManager().findFragmentById(R.id.sub_category_fragment) != null) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnChapterSelectListener) getParentFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getParentFragment().toString()
					+ " must implement OnChapterSelectListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCallback.onChapterSelected(position);
		getListView().setItemChecked(position, true);
	}

}
