package com.targetting.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.targetting.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val goToTarget: SingleLiveEvent<Unit> = SingleLiveEvent()

    fun goToTargetDelayed() {
        viewModelScope.launch {
            delay(2000)
            goToTarget.postValue(Unit)
            withContext(Dispatchers.Main) {
                goToTarget.postValue(Unit)
            }
        }
    }
}