/*
 * uHunt for Android - The most comprehensive Android app for uHunt and Competitive programming
 *   Copyright (C) 2018 Kaidul Islam, Esraa Ibrahim
 *
 *   This file is part of uHunt for Android.
 *
 *   uHunt for Android is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   uHunt for Android is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with uHunt for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.onlinejudge.uhunt

object CommonUtils {
    const val BASE_URL = "https://uhunt.onlinejudge.org/api/"
    const val USER_ID_TO_USERNAME_URL = "uname2uid/"
    const val USER_NAME_PARAM = "uname"
    const val ALL_SUBMISSIONS_URL = "subs-user/"
    const val USER_ID_PARAM = "user-id"
    const val CP_BOOK_PROBLEMS_URL = "cpbook/3"
    const val USER_SUBMISSION_URL = BASE_URL + "subs-user-last/"
    const val LIVE_SUBMISSION_URL = BASE_URL + "poll/"
    const val RANKLIST_URL = BASE_URL + "ranklist/"
    const val SPECIFIC_PROBLEM_URL = BASE_URL + "p/num/"
    const val PREFERENCE_NAME = "org.onlinejudge.uhunt"
    const val PROBLEM_LIST_URL = BASE_URL + "p"
    const val FILE_ALL_SUBMISSIONS = "all_submissions.json"
    const val FILE_PROBLEM_LIST = "problem_list.json"
    const val FILE_COMPETITIVE_PROGRAMMING_3 = "competitive_programming_edition_3.json"
    const val PROBLEM_URL = "http://uva.onlinejudge.org/external/"
    const val KEY_USERNAME = "uname"
    const val KEY_NAME = "name"
    const val KEY_SUBMISSION = "subs"
    const val KEY_ID = "id"
    const val KEY_SUBMISSION_ID = "sid"
    const val KEY_PROBLEM_ID = "pid"
    const val KEY_USER_ID = "uid"
    const val KEY_VERDICT_ID = "ver"
    const val KEY_VERDICT_COLOR = "verdict_color"
    const val KEY_RUNTIME = "run"
    const val KEY_SUBMISSION_TIME = "sbt"
    const val KEY_LANGUAGE_ID = "lan"
    const val KEY_SUBMISSION_RANK = "rank"
    const val KEY_MESSAGE = "msg"
    const val LAST_SAVED = "last_saved"
    const val KEY_RANK_USERNAME = "username"
    const val KEY_RANK_AC = "ac"
    const val KEY_RANK_NOS = "nos"
    const val PROBLEM_DATABASE = "problem_database.db"
    const val PROBLEM_DATABASE_VERSION = 1
    const val PROBLEM_TABLE = "problem_table"
    const val KEY_PROBLEM_TITLE = "title"
    const val KEY_PROBLEM_NO = "num"
    const val KEY_PROBLEM_NO_TITLE = "no_and_title"
    const val KEY_VERDICT_SERIES = "verdict_series"
    const val KEY_DACU = "dacu"
    const val KEY_BEST_RT = "mrun"
    const val KEY_BEST_M = "mmem"
    const val KEY_NO_VERDICT = "nover"
    const val KEY_NO_SE = "sube"
    const val KEY_NO_NOT_JUDGE = "noj"
    const val KEY_NO_IN_QUEUE = "inq"
    const val KEY_NO_CE = "ce"
    const val KEY_NO_RF = "rf"
    const val KEY_NO_RE = "re"
    const val KEY_NO_OLE = "ole"
    const val KEY_NO_TLE = "tle"
    const val KEY_NO_MLE = "mle"
    const val KEY_NO_WA = "wa"
    const val KEY_NO_PE = "pe"
    const val KEY_NO_AC = "ac"
    const val KEY_NO_RTL = "rtl"
    const val KEY_NO_STATUS = "status"
    val alias = arrayOf("90", "80", "70", "50", "60", "30", "40", "10")
    val aliases = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    const val KEY_BOOK_NAME = "competitive_programming_edition_3.txt"
    const val ALL_VERDICT_TOGETHER = "all_verdicts_together"
    const val submissionIsCached = "submissionIsCached"
    const val problemListisCached = "problem_is_cached"
    const val DEFAULT_USER_ID = "135364"
    const val DEFAULT_USERNAME = "Kaidul"
    const val ABOUT_HTML = "file:///android_asset/about.html"
}