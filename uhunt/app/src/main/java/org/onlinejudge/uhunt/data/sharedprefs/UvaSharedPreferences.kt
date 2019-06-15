package org.onlinejudge.uhunt.data.sharedprefs

import android.content.Context
import android.content.SharedPreferences

import org.onlinejudge.uhunt.CommonUtils

class UvaSharedPreferences(context: Context) {
    private var uvaSharedPref: SharedPreferences = newInstance(context)

    companion object {
        fun newInstance(context: Context): SharedPreferences {
            return context.getSharedPreferences(CommonUtils.PREFERENCE_NAME, Context.MODE_PRIVATE)
        }
    }

    var userId: String?
        get() = uvaSharedPref.getString(CommonUtils.KEY_USER_ID, CommonUtils.DEFAULT_USER_ID)
        set(value) = uvaSharedPref.edit().putString(CommonUtils.KEY_USER_ID, value).apply()
}
