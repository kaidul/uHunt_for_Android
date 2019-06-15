package org.onlinejudge.uhunt.profile

import android.app.Application
import androidx.lifecycle.LiveData
import org.onlinejudge.uhunt.base.BaseViewModel
import org.onlinejudge.uhunt.data.network.model.UserSubmission

class ProfileViewModel(application: Application) : BaseViewModel(application) {
    fun getCurrentLoggedInUserSubmissions(): LiveData<UserSubmission> {
        val userId = uvaRepository.userIdSharedPrefs
        return uvaRepository.getUserSubmissions(userId ?: "")
    }
}