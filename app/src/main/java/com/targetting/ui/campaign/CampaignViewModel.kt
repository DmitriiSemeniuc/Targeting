package com.targetting.ui.campaign

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.targetting.persistence.models.Campaign
import com.targetting.persistence.models.Channel
import com.targetting.persistence.repository.CampaignRepository
import com.targetting.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignViewModel @Inject constructor(
    application: Application,
    private val campaignRepository: CampaignRepository,
) :
    AndroidViewModel(application) {

    private val campaignsState: MutableLiveData<DataState<List<Campaign>>> = MutableLiveData()

    val campaigns: LiveData<DataState<List<Campaign>>>
        get() = campaignsState

    fun getCampaigns(channel: Channel) {
        viewModelScope.launch {
            campaignRepository.getCampaignsByChannel(channel.name)
                .onEach {
                    campaignsState.value = it
                }
                .launchIn(viewModelScope)
        }
    }
}