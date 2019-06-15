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
