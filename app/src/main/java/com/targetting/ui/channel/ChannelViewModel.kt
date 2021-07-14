package com.targetting.ui.channel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.targetting.persistence.models.Channel
import com.targetting.persistence.models.Target
import com.targetting.utils.DataState
import javax.inject.Inject

class ChannelViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {

    private val channelsState: MutableLiveData<DataState<List<Channel>>> = MutableLiveData()
    val channels: LiveData<DataState<List<Channel>>>
        get() = channelsState

    fun setTargets(targets: List<Target>) {

        val channelNames = targets
            .map { it.channels }
            .flatten()
            .distinct()

        val channels = mutableListOf<Channel>()

        channelNames.forEach { channelName ->
            val channel = Channel(
                channelName,
                targets
                    .filter { it.channels.contains(channelName) }
                    .map { it.group })

            channels.add(channel)
        }
        channelsState.postValue(DataState.Success(channels))
    }
}