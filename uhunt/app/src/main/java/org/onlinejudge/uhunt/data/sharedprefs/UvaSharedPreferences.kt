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

package org.onlinejudge.uhunt.data.sharedprefs

import android.content.Context
import android.content.SharedPreferences

import org.onlinejudge.uhunt.CommonUtils

object UvaSharedPreferences {

    private lateinit var uvaSharedPref: SharedPreferences

    fun newInstance(context: Context): UvaSharedPreferences {
        uvaSharedPref = context.getSharedPreferences(CommonUtils.PREFERENCE_NAME, Context.MODE_PRIVATE)
        return UvaSharedPreferences
    }

    var userId: String?
        get() = uvaSharedPref.getString(CommonUtils.KEY_USER_ID, CommonUtils.DEFAULT_USER_ID)
        set(value) = uvaSharedPref.edit().putString(CommonUtils.KEY_USER_ID, value).apply()
}
