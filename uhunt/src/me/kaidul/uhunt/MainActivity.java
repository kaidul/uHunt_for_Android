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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.stream.JsonReader;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.view.GravityCompat;
import android.text.InputType;

public class MainActivity extends SherlockFragmentActivity {

	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	SharedPreferences prefs = null;
	public static boolean isProblemSearch = false;
	public static boolean isProblemStatistics = false;
	public static boolean hasConnection = false;
	int bufferSize = 8192;
	int mPosition = -1;
	private String[] title;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	/** Fragment instance **/
	Fragment profile = new Profile();
	Fragment submissionStatistics = new SubmissionStatistics();
	Fragment CPExercise = new CompetitiveProgramming();
	// Fragment progressYears = new ProgressOverTheYears();
	Fragment latestSubmissions = new LatestSubmissions();
	Fragment liveSubmissions = new LiveSubmissions();
	Fragment problemSearch = new ProblemWebView();
	Fragment problemStatistics = new ProblemStatistics();
	Fragment rankList = new RankList();
	Fragment solvedProblemLevel = new SolvedProblemLevel();

	static int totalSubmission;
	static Set<Integer> failedLists = new TreeSet<Integer>();
	static Set<Integer> solvedLists = new TreeSet<Integer>();
	static String name, userName;
	static int currentPosition = 0;
	static boolean reCreate = false;
	static boolean refresh = false;
	static boolean _refresh = false;

	public HashMap<String, String> languageCode = new HashMap<String, String>();
	public HashMap<String, Verdict> verdicts = new HashMap<String, Verdict>();
	@SuppressLint("UseSparseArrays")
	public static HashMap<String, Integer> solvedorTried = new HashMap<String, Integer>();
	Boolean isFirst = false;
	static int problemNo = 100;
	int position;
	int fiveDays = 86400000 * 7;
	static boolean exit = false;
	static boolean changeUser = false;
	static ArrayList<CoOrdinate> coOrdinates = new ArrayList<CoOrdinate>();
	@SuppressLint("UseSparseArrays")
	static HashMap<Integer, Problems> problems = new HashMap<Integer, Problems>();

	/* AsyncTask Object */
	private GetProblemListTask problemTask = null;
	private GetAllSubmissionsTask submissionTask = null;
	private GetUserIdTask userIDTask = null;
	private ProcessingTask processingTask = null;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			return;
		}

		// related code for navigation drawer
		title = getResources().getStringArray(R.array.navigation_drawer);
		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, title));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		// set drawer gesture open-close gesture off
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		// creating sharedpreference instance
		prefs = getSharedPreferences(CommonUtils.PREFERENCE_NAME, MODE_PRIVATE);

		// mapping lanugage code with corresponding name
		languageCode.put("1", "ANSI C");
		languageCode.put("2", "Java");
		languageCode.put("3", "C++");
		languageCode.put("4", "Pascal");
		languageCode.put("5", "C++11");

		// mapping verdict information with corresponding verdict code
		verdicts.put("0", new Verdict("In queue", "", "#000000"));
		verdicts.put("10", new Verdict("Submission error", "", "#000000"));
		verdicts.put("15", new Verdict("Can't be judged", "", "#000000"));
		verdicts.put("20", new Verdict("In queue", "", "#000000"));
		verdicts.put("35", new Verdict("Restricted Error", "", "#000000"));
		verdicts.put("30", new Verdict("Compile Error", "ce", "#AAAA00"));
		verdicts.put("40", new Verdict("Runtime Error", "re", "#00AAAA"));
		verdicts.put("45", new Verdict("Output limit", "ot", "#000000"));
		verdicts.put("50", new Verdict("Time limit", "tl", "#0000FF"));
		verdicts.put("60", new Verdict("Memory limit", "ml", "#6767D0"));
		verdicts.put("70", new Verdict("Wrong answer", "wa", "#FF0000"));
		verdicts.put("80", new Verdict("PresentationE", "pe", "#666600"));
		verdicts.put("90", new Verdict("Accepted", "ac", "#00AA00"));
		// a dummy fragment will be shown while network oriented task will
		// execute
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, new PlaceHolderFragment())
					.commit();
		}

		getSupportFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {

					@Override
					public void onBackStackChanged() {
						invalidateOptionsMenu();
						Fragment fragment = getSupportFragmentManager()
								.findFragmentById(R.id.content_frame);
						if (fragment != null) {
							updateTitleAndDrawer(fragment);
						}

					}
				});

		// check whether internet connection is available or not
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		hasConnection = cd.isConnectingToInternet();

		// for the first launch
		if (prefs.getBoolean("firstrun", true)) {
			// in case of no connection
			if (hasConnection == false) {
				networkAvailabilityNotice(this);
				//finish();
				return;
			}
			// next time it is no longer first run
			prefs.edit().putBoolean("firstrun", false).commit();
			// name picker dialog
			ShowDialogUserNamePicker(null);
		} else { // for the cases except first lauch
			if (hasConnection == false) {
				// check whether the data were saved successfully
				boolean okay = prefs.getBoolean(CommonUtils.submissionIsCached,
						false)
						&& prefs.getBoolean(CommonUtils.problemListisCached,
								false);
				// if data was not written properly, then showing an alert
				// message
				if (okay == false) {
					new AlertDialog.Builder(this)
							.setTitle("Error!")
							.setMessage("Data didn't cached properly, Check your internet connection")
							.setCancelable(false)
							.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dlg, int sumthin) {
											dlg.cancel();
											finish();
										}
									}).show();
				} else { // if everything is okay, start submissionTask
					Toast.makeText(
							this,
							"Internet is not available! A cached copy is shown!",
							Toast.LENGTH_SHORT).show();
					submissionTask = new GetAllSubmissionsTask();
					submissionTask.execute(CommonUtils.ALL_SUBMISSIONS
							+ prefs.getString(CommonUtils.KEY_USER_ID,
									CommonUtils.DEFAULT_USER_ID));
				}
			} else { // if internet connection is available, start
						// submissionTask without any heistation
				submissionTask = new GetAllSubmissionsTask();
				submissionTask.execute(CommonUtils.ALL_SUBMISSIONS
						+ prefs.getString(CommonUtils.KEY_USER_ID,
								CommonUtils.DEFAULT_USER_ID));
			}
		}
	}

	/**
	 * @param context
	 * @return <code>true</code> if network connection is available otherwise
	 *         <code>false</code>
	 */
	public static void networkAvailabilityNotice(Context context) {
		new AlertDialog.Builder(context)
				.setTitle("Internet Unavailable")
				.setMessage(
						"Please check your network connection or try again later!")
				.setCancelable(false)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						dlg.cancel();
					}
				}).show();
	}

	private void updateTitleAndDrawer(Fragment fragment) {
		String fragClassName = fragment.getClass().getName();
		int position = 0;
		if (fragClassName.equals(Profile.class.getName())) {
			position = 0;
		} else if (fragClassName.equals(SubmissionStatistics.class.getName())) {
			position = 1;
		} else if (fragClassName.equals(SolvedProblemLevel.class.getName())) {
			position = 2;
		} else if (fragClassName.equals(LatestSubmissions.class.getName())) {
			position = 3;
		} else if (fragClassName.equals(CompetitiveProgramming.class.getName())) {
			position = 4;
		} else if (fragClassName.equals(ProblemWebView.class.getName())) {
			position = 5;
		} else if (fragClassName.equals(RankList.class.getName())) {
			position = 6;
		} else if (fragClassName.equals(LiveSubmissions.class.getName())) {
			position = 7;
		} else if (fragClassName.equals(ProblemStatistics.class.getName())) {
			position = 8;
		}
		mPosition = position;
		setTitle(title[position]);
		mDrawerList.setItemChecked(position, true);
	}

	@SuppressLint("NewApi")
	static int getDeviceWidth(Activity activity) {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			return size.x;
		} else {
			Display display = activity.getWindowManager().getDefaultDisplay();
			return display.getWidth();
		}
	}

	void ShowDialogUserNamePicker(String text) {
		final EditText input = new EditText(this);
		if (text != null)
			input.setText(text);

		new AlertDialog.Builder(this)
				.setTitle("Add User")
				.setMessage("Enter your Username")
				.setView(input)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String userName = input.getEditableText().toString();
						if (userName.equals(prefs.getString(
								CommonUtils.KEY_USERNAME, "this_is_dummy"))) {
							if (CommonUtils.isDebuggable) {
								Log.d("skipped", "no need to send request!");
							}
							return;
						}
						prefs.edit()
								.putString(CommonUtils.KEY_USERNAME, userName)
								.commit();
						userIDTask = new GetUserIdTask();
						// Encoding userName
						try {
							userName = URLEncoder.encode(userName, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						userIDTask.execute(CommonUtils.USER_ID_TO_USERNAME
								+ userName);
					}
				}).show();
	}

	void ProblemNoPicker(final int requestCode) {
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		new AlertDialog.Builder(this)
				.setTitle("Enter problem Id")
				.setView(input)
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String str = input.getEditableText().toString();
						if (str.equals("")) {
							new AlertDialog.Builder(MainActivity.this)
									.setTitle("Invalid Problem No")
									.setMessage(
											"Please Enter a valid problem no.!")
									.setNeutralButton(
											"OK",
											new DialogInterface.OnClickListener() {

												public void onClick(
														DialogInterface dlg,
														int sumthin) {
													ProblemNoPicker(requestCode);
												}
											}).show();
							return;
						}
						problemNo = Integer.parseInt(str);
						if (requestCode == 1) {
							ProblemWebView searchProblem = (ProblemWebView) getSupportFragmentManager()
									.findFragmentById(R.id.content_frame);
							if (searchProblem != null) {
								searchProblem.updateUrl(problemNo);
							}
						} else if (requestCode == 2) {
							ProblemStatistics problemStatistics = (ProblemStatistics) getSupportFragmentManager()
									.findFragmentById(R.id.content_frame);
							if (problemStatistics != null) {
								problemStatistics.updateUrl(problemNo);
							}
						}
					}
				}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (reCreate) {
			MenuItem item = menu.findItem(R.id.refresh);
			item.setVisible(true);
		}
		if (refresh) {
			MenuItem item = menu.findItem(R.id.refresh_2);
			item.setVisible(true);
		}
		if (_refresh) {
			MenuItem item = menu.findItem(R.id.refresh_3);
			item.setVisible(true);
		}
		if (isProblemSearch) {
			MenuItem item = menu.findItem(R.id.search_problem);
			item.setVisible(true);
		}
		if (isProblemStatistics) {
			MenuItem item = menu.findItem(R.id.problem_statistics);
			item.setVisible(true);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			break;
		case R.id.exit:
			System.exit(0);
			break;
		case R.id.change_user:
			changeUser = true;
			ShowDialogUserNamePicker(prefs.getString(CommonUtils.KEY_USERNAME,
					CommonUtils.DEFAULT_USERNAME));
			break;
		case R.id.about:
			Intent intent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.feedback:
			Intent feedback = new Intent(MainActivity.this,
					FeedbackFormActivity.class);
			startActivity(feedback);
			break;
		case R.id.menu_settings:
			Intent settings = new Intent(MainActivity.this,
					SettingsActivity.class);
			startActivity(settings);
			break;
		case R.id.search_problem:
			ProblemNoPicker(1);
			break;
		case R.id.problem_statistics:
			ProblemNoPicker(2);
			break;
		case R.id.refresh:
			reloadActivity();
			break;
		case R.id.refresh_2:
			ProblemWebView problemSearchFrag = (ProblemWebView) getSupportFragmentManager()
					.findFragmentById(R.id.content_frame);
			problemSearchFrag.updateUrl(problemNo);
			break;
		case R.id.refresh_3:
			ProblemStatistics problemStatisticsFrag = (ProblemStatistics) getSupportFragmentManager()
					.findFragmentById(R.id.content_frame);
			problemStatisticsFrag.updateUrl(problemNo);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	public void reloadActivity() {
		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void selectItem(int position) {
		currentPosition = position;
		Fragment fragmentName = null;
		exit = false;
		switch (position) {
		case 0:
			fragmentName = profile;
			break;
		case 1:
			fragmentName = submissionStatistics;
			break;
		case 2:
			fragmentName = solvedProblemLevel;
			break;
		case 3:
			fragmentName = latestSubmissions;
			break;
		case 4:
			fragmentName = CPExercise;
			break;
		case 5:
			fragmentName = problemSearch;
			break;
		case 6:
			fragmentName = rankList;
			break;
		case 7:
			fragmentName = liveSubmissions;
			break;
		case 8:
			fragmentName = problemStatistics;
		default:
			break;
		}
		replaceFragment(fragmentName, position);
		if (Build.VERSION.SDK_INT < 11) {
			supportInvalidateOptionsMenu();
		} else {
			invalidateOptionsMenu();
		}
	}

	private void replaceFragment(Fragment fragment, int position) {
		FragmentManager manager = getSupportFragmentManager();
		String backStateName = fragment.getClass().getName();
		String fragmentTag = backStateName;

		boolean fragmentPopped = manager
				.popBackStackImmediate(backStateName, 0);
		if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(R.id.content_frame, fragment, fragmentTag);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(backStateName);
			ft.commit();
		}
		mDrawerList.setItemChecked(position, true);
		setTitle(title[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(title);
	}

	/**
	 * Fetch User id from user name and start rankList fetching thread
	 * 
	 */
	protected class GetUserIdTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			InputStreamReader isr = new JSONDownloader()
					.getJSONStringFromUrl(params[0]);
			BufferedReader buffReader = null;
			String user = null;
			if (isr != null) {
				buffReader = new BufferedReader(isr);
				if (buffReader != null) {
					try {
						user = buffReader.readLine();
					} catch (IOException e) {
					}
				}
			}
			return user;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.equals("0")) {
					new AlertDialog.Builder(MainActivity.this)
							.setTitle("Invalid Username")
							.setMessage("Please Enter a valid username!")
							.setNeutralButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dlg, int sumthin) {
											ShowDialogUserNamePicker(null);
										}
									}).show();
					return;
				}
				prefs.edit().putString(CommonUtils.KEY_USER_ID, result)
						.commit();
				if (changeUser) {
					currentPosition = 0;
					reloadActivity();
					return;
				}
				if (CommonUtils.isDebuggable) {
					Log.d("GetUserIdTask", "GetUserIdTask is completed");
				}
				submissionTask = new GetAllSubmissionsTask();
				submissionTask.execute(CommonUtils.ALL_SUBMISSIONS + result);
			}
		}

	}

	protected class GetAllSubmissionsTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			InputStreamReader isr = null;
			if (hasConnection) {
				// flag is set to false as data is not cached yet
				prefs.edit().putBoolean(CommonUtils.submissionIsCached, false)
						.commit();
				isr = new JSONDownloader().getJSONStringFromUrl(params[0]);
				if (isr != null) {
					BufferedReader reader = new BufferedReader(isr, bufferSize);
					StringBuilder sb = new StringBuilder();
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							sb.append(line + "\n");
						}
					} catch (IOException e) {
					}
					writeToFile(sb.toString(), CommonUtils.FILE_ALL_SUBMISSIONS);
					// set flag to true to make sure that data is cached
					// successfully
					prefs.edit()
							.putBoolean(CommonUtils.submissionIsCached, true)
							.commit();
				}
			} else {
				// nothing
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			problemTask = new GetProblemListTask();
			problemTask.execute(CommonUtils.PROBLEM_LIST_URL);
		}

	}

	protected class GetProblemListTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			InputStreamReader isr = null;
			BufferedReader br = null;
			if (hasConnection) {
				Date date = new Date();
				long savedTime = prefs.getLong(CommonUtils.LAST_SAVED,
						date.getTime());
				long now = date.getTime();
				if (now - savedTime > fiveDays
						|| (now - savedTime <= fiveDays && prefs.getBoolean(
								CommonUtils.problemListisCached, false) == false)) {
					if (CommonUtils.isDebuggable) {
						Log.d("updating", "need to update!");
					}
					prefs.edit()
							.putBoolean(CommonUtils.problemListisCached, false)
							.commit();
					isr = new JSONDownloader().getJSONStringFromUrl(params[0]);
					if (isr != null) {
						br = new BufferedReader(isr, bufferSize);
						StringBuilder sb = new StringBuilder();
						String line = null;
						try {
							while ((line = br.readLine()) != null) {
								sb.append(line + "\n");
							}
						} catch (IOException e) {
							Log.d("problem", "in file writting");
						}
						writeToFile(sb.toString(),
								CommonUtils.FILE_PROBLEM_LIST);
						prefs.edit()
								.putBoolean(CommonUtils.problemListisCached,
										true).commit();
					}
					try {
						isr.close();
					} catch (IOException e) {
					}
					try {
						br.close();
					} catch (IOException e) {
					}
					prefs.edit().putLong(CommonUtils.LAST_SAVED, now).commit();
				} else {
					if (CommonUtils.isDebuggable) {
						Log.d("old_copy", "Old copy is rendering");
					}
					try {
						isr = new InputStreamReader(
								openFileInput(CommonUtils.FILE_PROBLEM_LIST));
					} catch (FileNotFoundException e) {
						if (CommonUtils.isDebuggable) {
							Log.d("file_not_found", "File is missing!");
						}
					}
				}
			} else {
				try {
					isr = new InputStreamReader(
							openFileInput(CommonUtils.FILE_PROBLEM_LIST));
				} catch (FileNotFoundException e) {
					if (CommonUtils.isDebuggable) {
						Log.d("file_not_found", "File is missing!");
					}
				}
			}
			if (CommonUtils.isDebuggable) {
				Log.d("start", "mapping start");
			}
			try {
				isr = new InputStreamReader(
						openFileInput(CommonUtils.FILE_PROBLEM_LIST));
			} catch (FileNotFoundException e1) {

			}
			JsonReader reader = new JsonReader(isr);
			try {
				reader.beginArray();
				while (reader.hasNext()) {
					reader.beginArray();
					problems.put(
							reader.nextInt(),
							new Problems(reader.nextString(), reader
									.nextString(), reader.nextInt()));
					while (reader.hasNext())
						reader.skipValue();
					reader.endArray();
				}
				reader.endArray();
			} catch (IOException e) {
				if (CommonUtils.isDebuggable) {
					Log.d("problems", "hashmaping problem");
				}
			} finally {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			processingTask = new ProcessingTask();
			processingTask.execute();
		}
	}

	protected class ProcessingTask extends AsyncTask<Void, Void, Void> {

		@SuppressLint("UseSparseArrays")
		@Override
		protected Void doInBackground(Void... params) {
			// auxiliary variable
			HashSet<Integer> acceptedList = new HashSet<Integer>();
			HashSet<Integer> triedList = new HashSet<Integer>();
			HashMap<Integer, Integer> problemLevels = new HashMap<Integer, Integer>();
			HashMap<String, Integer> countVerdict = new HashMap<String, Integer>();

			// initialize verdict count variable
			countVerdict.put("10", 0);
			countVerdict.put("15", 0);
			countVerdict.put("20", 0);
			countVerdict.put("30", 0);
			countVerdict.put("35", 0);
			countVerdict.put("40", 0);
			countVerdict.put("45", 0);
			countVerdict.put("50", 0);
			countVerdict.put("60", 0);
			countVerdict.put("70", 0);
			countVerdict.put("80", 0);
			countVerdict.put("90", 0);

			// initialize problem level variables
			for (int i = 0; i <= 10; i++) {
				problemLevels.put(i, 0);
			}

			// parse & manipulate JSON
			try {
				InputStream is = null;
				try {
					is = openFileInput(CommonUtils.FILE_ALL_SUBMISSIONS);
				} catch (FileNotFoundException e) {

				}
				JsonReader reader = new JsonReader(new InputStreamReader(is,
						"UTF-8"));

				reader.beginObject();
				reader.nextName();
				name = reader.nextString();
				reader.nextName();
				userName = reader.nextString();
				totalSubmission = 0;
				reader.nextName();
				reader.beginArray();
				while (reader.hasNext()) {
					totalSubmission++;
					reader.beginArray();
					reader.skipValue();
					int problemID = reader.nextInt();
					String verdictCode = reader.nextString();
					countVerdict.put(verdictCode,
							countVerdict.get(verdictCode) + 1);

					// if verdict is AC, add it to accepted List and add it to
					// problem Level with it's level
					if (verdictCode.equals("90")) {
						boolean hasAlready = acceptedList.contains(problemID);
						if (!hasAlready) {
							acceptedList.add(problemID);
							Integer level = 0;
							if (problems.get(problemID) != null) {
								level = problemLevel(problems.get(problemID).problemDACU);
							}
							problemLevels.put(level,
									problemLevels.get(level) + 1);
						}
					} else {
						triedList.add(problemID);
					}
					while (reader.hasNext())
						reader.skipValue();
					reader.endArray();
				}
				reader.endArray();
				while (reader.hasNext()) {
					reader.skipValue();
				}
				reader.endObject();
				reader.close();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {

			}

			// verdict grpah and level graph statistics value update
			Editor editor = prefs.edit();
			for (int i = 0; i < CommonUtils.alias.length; i++) {
				editor.putInt(CommonUtils.alias[i],
						countVerdict.get(CommonUtils.alias[i]));
			}
			for (int i = 1; i <= 10; i++) {
				editor.putInt(CommonUtils.aliases[i], problemLevels.get(i));
			}
			editor.commit();

			// subtract accepted problem list from tried list to derive
			// "tried but ont yet solved" list
			triedList.removeAll(acceptedList);
			solvedLists = new TreeSet<Integer>(acceptedList);
			failedLists = new TreeSet<Integer>(triedList);

			// store all problems with the property whether it is AC or ~AC
			for (Iterator<Integer> it = solvedLists.iterator(); it.hasNext();) {
				solvedorTried.put(problems.get(it.next()).problemNo, 1);
			}
			for (Iterator<Integer> it = failedLists.iterator(); it.hasNext();) {
				solvedorTried.put(problems.get(it.next()).problemNo, 2);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
			selectItem(currentPosition);
			mDrawerToggle.syncState();
		}

	}

	/**
	 * @param dacu
	 * @return level of a problem (difficulty level). theory given by Felix
	 *         Halim
	 */
	public Integer problemLevel(int dacu) {
		return ((int) (10 - Math.floor(Math.min(10, Math.log(dacu)))));
	}

	// as the name implies
	private void writeToFile(String data, String fileName) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput(fileName, Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			if (CommonUtils.isDebuggable) {
				Log.e("Exception", "File write failed: " + e.toString());
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			if (exit) {
				finish();
			} else {
				Toast.makeText(MainActivity.this, "Press again to exit",
						Toast.LENGTH_SHORT).show();
				exit = true;
			}
		} else {
			super.onBackPressed();
		}
		return;
	}

	@Override
	protected void onStop() {
		super.onStop();
		exit = false;
		if (userIDTask != null
				&& userIDTask.getStatus() != AsyncTask.Status.FINISHED) {
			userIDTask.cancel(true);
		}
		if (submissionTask != null
				&& submissionTask.getStatus() != AsyncTask.Status.FINISHED) {
			submissionTask.cancel(true);
		}
		if (problemTask != null
				&& problemTask.getStatus() != AsyncTask.Status.FINISHED) {
			problemTask.cancel(true);
		}
		if (processingTask != null
				&& processingTask.getStatus() != AsyncTask.Status.FINISHED) {
			processingTask.cancel(true);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
