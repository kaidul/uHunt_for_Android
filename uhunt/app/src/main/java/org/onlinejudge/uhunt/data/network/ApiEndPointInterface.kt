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

package org.onlinejudge.uhunt.data.network

import org.onlinejudge.uhunt.CommonUtils
import org.onlinejudge.uhunt.data.network.model.UserSubmission
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndPointInterface {
    @GET("${CommonUtils.USER_ID_TO_USERNAME_URL}{${CommonUtils.USER_NAME_PARAM}}")
    fun login(@Path(CommonUtils.USER_NAME_PARAM) username: String): Call<String>

    @GET("${CommonUtils.ALL_SUBMISSIONS_URL}{${CommonUtils.USER_ID_PARAM}}")
    fun getUserSubmissions(@Path(CommonUtils.USER_ID_PARAM) userId: String): Call<UserSubmission>
}
