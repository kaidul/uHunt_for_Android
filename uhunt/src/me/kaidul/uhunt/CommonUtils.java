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

public class CommonUtils {

	final static boolean isDebuggable = false;
	
	final static String API_URL_PREFIX = "http://uhunt.felix-halim.net/api/";
	final static String USER_SUBMISSION_URL = API_URL_PREFIX + "subs-user-last/";
	final static String LIVE_SUBMISSION_URL = API_URL_PREFIX + "poll/";
	final static String RANKLIST_URL = API_URL_PREFIX + "ranklist/";
	final static String ALL_SUBMISSIONS = API_URL_PREFIX + "subs-user/";
	final static String USER_ID_TO_USERNAME = API_URL_PREFIX + "uname2uid/";
	final static String SPECIFIC_PROBLEM_URL = API_URL_PREFIX + "p/num/";
	final static String PREFERENCE_NAME = "me.kaidul.uhunt";
	final static String PROBLEM_LIST_URL = API_URL_PREFIX + "p";
	final static String FILE_ALL_SUBMISSIONS = "all_submissions.json";
	final static String FILE_PROBLEM_LIST = "problem_list.json";
	final static String FILE_COMPETITIVE_PROGRAMMING_3 = "competitive_programming_edition_3.json";
	final static String PROBLEM_URL = "http://uva.onlinejudge.org/external/";
	final static String KEY_USERNAME = "uname";
	final static String KEY_NAME = "name";
	final static String KEY_SUBMISSION = "subs";
	final static String KEY_ID = "id";
	final static String KEY_SUBMISSION_ID = "sid";
	final static String KEY_PROBLEM_ID = "pid";
	final static String KEY_USER_ID = "uid";
	final static String KEY_VERDICT_ID = "ver";
	final static String KEY_VERDICT_COLOR = "verdict_color";
	final static String KEY_RUNTIME = "run";
	final static String KEY_SUBMISSION_TIME = "sbt";
	final static String KEY_LANGUAGE_ID = "lan";
	final static String KEY_SUBMISSION_RANK = "rank";
	final static String KEY_MESSAGE = "msg";

	final static String LAST_SAVED = "last_saved";
	final static String KEY_RANK_USERNAME = "username";
	final static String KEY_RANK_AC = "ac";
	final static String KEY_RANK_NOS = "nos";
	
	final static String PROBLEM_DATABASE = "problem_database.db";
	final static int PROBLEM_DATABASE_VERSION = 1;
	final static String PROBLEM_TABLE = "problem_table";
	final static String KEY_PROBLEM_TITLE = "title";
	final static String KEY_PROBLEM_NO = "num";
	final static String KEY_PROBLEM_NO_TITLE = "no_and_title";
	final static String KEY_VERDICT_SERIES = "verdict_series";
	final static String KEY_DACU = "dacu";
	final static String KEY_BEST_RT = "mrun";
	final static String KEY_BEST_M = "mmem";
	final static String KEY_NO_VERDICT = "nover";
	final static String KEY_NO_SE = "sube";
	final static String KEY_NO_NOT_JUDGE = "noj";
	final static String KEY_NO_IN_QUEUE = "inq";
	final static String KEY_NO_CE = "ce";
	final static String KEY_NO_RF = "rf";
	final static String KEY_NO_RE = "re";
	final static String KEY_NO_OLE = "ole";
	final static String KEY_NO_TLE = "tle";
	final static String KEY_NO_MLE = "mle";
	final static String KEY_NO_WA = "wa";
	final static String KEY_NO_PE = "pe";
	final static String KEY_NO_AC = "ac";
	final static String KEY_NO_RTL = "rtl";
	final static String KEY_NO_STATUS = "status";
	final static String[] alias = {"90", "80", "70", "50", "60", "30", "40", "10"};
	final static String[] aliases = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	final static String KEY_BOOK_NAME = "competitive_programming_edition_3.txt";
	
	final static String ALL_VERDICT_TOGETHER = "all_verdicts_together";
	final static String submissionIsCached = "submissionIsCached";
	final static String problemListisCached = "problem_is_cached";
	
	final static String DEFAULT_USER_ID = "135364";
	final static String DEFAULT_USERNAME = "Kaidul";
	final static String ABOUT_HTML = "file:///android_asset/about.html";
	
}
