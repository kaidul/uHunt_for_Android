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

package org.onlinejudge.uhunt.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.onlinejudge.uhunt.data.network.ApiEndPointClient
import org.onlinejudge.uhunt.data.network.ApiEndPointInterface
import org.onlinejudge.uhunt.data.network.model.UserSubmission
import org.onlinejudge.uhunt.data.sharedprefs.UvaSharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UvaRepository {

    private lateinit var apiEndPointInterface: ApiEndPointInterface
    private lateinit var uvaSharedPreferences: UvaSharedPreferences

    fun newInstance(application: Application): UvaRepository {
        apiEndPointInterface = ApiEndPointClient.newInstance()
        uvaSharedPreferences = UvaSharedPreferences.newInstance(application)
        return UvaRepository
    }

    var userIdSharedPrefs: String?
        get() = uvaSharedPreferences.userId
        set(value) {
            uvaSharedPreferences.userId = value
        }

    fun login(username: String): LiveData<String> {
        val userId = MutableLiveData<String>()
        apiEndPointInterface.login(username).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                userId.value = response.body()
                userIdSharedPrefs = userId.value

                // Save UserId to shared preferences if not saved
                //if (uvaSharedPreferences.userId === CommonUtils.DEFAULT_USER_ID) {

                //}
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
        return userId
    }

    fun getUserSubmissions(userId: String): LiveData<UserSubmission> {
        val userSubmissions = MutableLiveData<UserSubmission>()
        apiEndPointInterface.getUserSubmissions(userId).enqueue(object : Callback<UserSubmission> {
            override fun onResponse(call: Call<UserSubmission>, response: Response<UserSubmission>) {
                userSubmissions.value = response.body()
            }

            override fun onFailure(call: Call<UserSubmission>, t: Throwable) {

            }

        })
        return userSubmissions
    }
}
