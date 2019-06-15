package org.onlinejudge.uhunt.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.onlinejudge.uhunt.data.UvaRepository

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected var uvaRepository: UvaRepository = UvaRepository.newInstance(application)
}