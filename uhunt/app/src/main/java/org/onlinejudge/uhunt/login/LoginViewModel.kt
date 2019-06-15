package org.onlinejudge.uhunt.login

import android.app.Application
import androidx.lifecycle.LiveData
import org.onlinejudge.uhunt.base.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel(application) {
    fun login(username: String): LiveData<String> {
        return uvaRepository.login(username)
    }
}