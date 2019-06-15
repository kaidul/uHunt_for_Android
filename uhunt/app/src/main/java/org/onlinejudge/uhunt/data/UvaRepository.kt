package org.onlinejudge.uhunt.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.onlinejudge.uhunt.CommonUtils
import org.onlinejudge.uhunt.data.network.ApiEndPointClient
import org.onlinejudge.uhunt.data.network.ApiEndPointInterface
import org.onlinejudge.uhunt.data.network.model.UserSubmission
import org.onlinejudge.uhunt.data.sharedprefs.UvaSharedPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UvaRepository private constructor(application: Application) {
    private val apiEndPointInterface: ApiEndPointInterface = ApiEndPointClient.newInstance()
    private val uvaSharedPreferences: UvaSharedPreferences = UvaSharedPreferences(application)

    companion object {
//        @Volatile
//        private var instance: UvaRepository? = null

        fun newInstance(application: Application): UvaRepository {
//            if (instance == null) {
//                synchronized(UvaRepository::class.java) {
//                    instance = UvaRepository(application)
//                }
//            }
//            return instance
            return UvaRepository(application)
        }
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
