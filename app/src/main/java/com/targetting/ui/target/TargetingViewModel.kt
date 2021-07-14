package com.targetting.ui.target

import android.app.Application
import androidx.lifecycle.*
import com.targetting.persistence.models.Target
import com.targetting.persistence.repository.TargetRepository
import com.targetting.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TargetingViewModel @Inject constructor(
    application: Application,
    private val targetRepository: TargetRepository
) : AndroidViewModel(application) {

    private val targetsState: MutableLiveData<DataState<List<Target>>> = MutableLiveData()

    val targets: LiveData<DataState<List<Target>>>
        get() = targetsState

    fun getTargets() {
        viewModelScope.launch {
            targetRepository.getTargets()
                .onEach {
                    targetsState.value = it
                }
                .launchIn(viewModelScope)
        }
    }
}

