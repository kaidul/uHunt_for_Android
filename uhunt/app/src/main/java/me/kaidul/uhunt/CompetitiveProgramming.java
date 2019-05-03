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

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.progressfragment.ProgressFragment;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.kaidul.uhunt.ChaptersListFragment.OnChapterSelectListener;
import me.kaidul.uhunt.SubChaptersListFragment.OnSubChapterSelectListener;

public class CompetitiveProgramming extends ProgressFragment implements
		OnChapterSelectListener, OnSubChapterSelectListener {

	View mContentView;
	public static List<Chapter> chapterList = new ArrayList<Chapter>();
	private ProcessTask processTask = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(
				R.layout.competitive_programming_exercise, container, false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setContentShown(false);
		setContentView(mContentView);
		processTask = new ProcessTask();
		processTask.execute();
	}
	

	protected class ProcessTask extends AsyncTask<Void, Void, List<Chapter>> {

		@Override
		protected List<Chapter> doInBackground(Void... params) {
			InputStream inputStream = null;
			List<Chapter> tempList = new ArrayList<Chapter>();
			try {
				inputStream = getActivity().getAssets().open(
						CommonUtils.FILE_COMPETITIVE_PROGRAMMING_3);
				JsonReader reader = new JsonReader(new InputStreamReader(
						inputStream));

				reader.beginArray(); // array #1
				while (reader.hasNext()) {
					String chapterTitle = null;
					List<SubChapter> subList = new ArrayList<SubChapter>();
					reader.beginObject(); // object #2
					while (reader.hasNext()) {
						reader.skipValue();
						chapterTitle = reader.nextString();
						reader.skipValue();
						reader.beginArray(); // array #3
						while (reader.hasNext()) {
							String subChapterTitle = null;
							List<SubSubChapter> subSubList = new ArrayList<SubSubChapter>();
							reader.beginObject(); // object #4
							while (reader.hasNext()) {
								reader.skipValue();
								subChapterTitle = reader.nextString();
								reader.skipValue();
								reader.beginArray(); // array #5
								while (reader.hasNext()) {
									reader.beginArray(); // array #6
									String subSubChapterTitle = reader
											.nextString(); // sub-sub-category
															// title
									List<ProblemList> problemsList = new ArrayList<ProblemList>();
									while (reader.hasNext()) {
										int signedProblemID = reader.nextInt(); // problemNo
										String title = reader.nextString();
										if (signedProblemID < 0)
											problemsList.add(new ProblemList(
													Math.abs(signedProblemID),
													title, true));
										else
											problemsList.add(new ProblemList(
													signedProblemID, title,
													false));
									}
									reader.endArray(); // array #6
									subSubList.add(new SubSubChapter(
											subSubChapterTitle, problemsList));
								}
								reader.endArray(); // array #5
							}
							reader.endObject(); // object #4
							subList.add(new SubChapter(subChapterTitle,
									subSubList));
						}
						reader.endArray(); // array #3
					}
					reader.endObject(); // object #2
					tempList.add(new Chapter(chapterTitle, subList));
				}
				reader.endArray(); // array #1
				reader.close();

			} catch (IOException e) {
				// nothing
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// nothing
					}
				}
			}
			return tempList;
		}

		@Override
		protected void onPostExecute(List<Chapter> result) {
			super.onPostExecute(result);
			chapterList = result;
			FragmentTransaction transaction = getChildFragmentManager()
					.beginTransaction();
			Fragment chapterFragment = new ChaptersListFragment();
			Fragment subChapterFragment = new SubChaptersListFragment();
			Fragment subSubChapterFragment = new SubSubChaptersListFragment();
			if (mContentView.findViewById(R.id.fragment_container) != null) {
				transaction.replace(R.id.fragment_container, chapterFragment);
			} else {
				transaction.replace(R.id.category_fragment, chapterFragment);
				transaction.replace(R.id.sub_category_fragment,
						subChapterFragment);
				transaction.replace(R.id.sub_sub_category_fragment,
						subSubChapterFragment);
			}
			transaction.commit();
			setContentShown(true);
		}

	}
	
	static protected class Chapter {
		String chapterTitle;
		List<SubChapter> subchapterList;

		public Chapter(String chapterTitle, List<SubChapter> subchapterList) {
			this.chapterTitle = chapterTitle;
			this.subchapterList = subchapterList;
		}

	}

	static protected class SubChapter {
		String subChapterTitle;
		List<SubSubChapter> subsubchapterList;

		public SubChapter(String subChapterTitle,
				List<SubSubChapter> subsubchapterList) {
			this.subChapterTitle = subChapterTitle;
			this.subsubchapterList = subsubchapterList;
		}

	}

	static protected class SubSubChapter {
		String subSubChapterTitle;
		List<ProblemList> problemList;

		public SubSubChapter(String subSubChapterTitle,
				List<ProblemList> problemList) {
			this.subSubChapterTitle = subSubChapterTitle;
			this.problemList = problemList;
		}

	}

	static public class ProblemList {
		Integer problemNo;
		String problemTitle;
		boolean isStarred;

		public ProblemList(Integer problemNo, String problemTitle,
				boolean isStarred) {
			this.problemNo = problemNo;
			this.isStarred = isStarred;
			this.problemTitle = problemTitle;
		}

	}

	@Override
	public void onChapterSelected(int position) {
		SubChaptersListFragment subChaptersListFrag = (SubChaptersListFragment) getChildFragmentManager()
				.findFragmentById(R.id.sub_category_fragment);
		if (subChaptersListFrag != null) {
			subChaptersListFrag.updateList(position);
		} else {
			Fragment subChapterFragment = new SubChaptersListFragment();
			Bundle args = new Bundle();
			args.putInt(SubChaptersListFragment.CHAPTER_POSITION, position);
			subChapterFragment.setArguments(args);
			FragmentTransaction transaction = getChildFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.fragment_container, subChapterFragment);
			transaction.commit();
		}
	}

	@Override
	public void onSubChapterSelected(int prev, int position) {
		SubSubChaptersListFragment subSubChaptersListFrag = (SubSubChaptersListFragment) getChildFragmentManager()
				.findFragmentById(R.id.sub_sub_category_fragment);
		if (subSubChaptersListFrag != null) {
			subSubChaptersListFrag.updateList(prev, position);
		} else {
			Fragment subSubChapterFragment = new SubSubChaptersListFragment();
			Bundle args = new Bundle();
			args.putIntArray(SubSubChaptersListFragment.POSITIONS, new int[] {
					prev, position });
			subSubChapterFragment.setArguments(args);
			FragmentTransaction transaction = getChildFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.fragment_container, subSubChapterFragment);
			transaction.commit();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (processTask != null
				&& processTask.getStatus() != AsyncTask.Status.FINISHED) {
			processTask.cancel(true);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	

}