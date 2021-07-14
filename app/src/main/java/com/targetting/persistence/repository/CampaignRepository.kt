package com.targetting.persistence.repository

import com.targetting.network.ApiClient
import com.targetting.network.response.CampaignResponseMapper
import com.targetting.persistence.database.CampaignDao
import com.targetting.persistence.database.CampaignModelMapper
import com.targetting.persistence.models.Campaign
import com.targetting.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CampaignRepository constructor(
    private val campaignDao: CampaignDao,
    private val apiClient: ApiClient,
    private val modelMapper: CampaignModelMapper,
    private val responseMapper: CampaignResponseMapper
) {

    suspend fun getCampaignsByChannel(channel: String): Flow<DataState<List<Campaign>>> = flow {
        emit(DataState.Loading)
        delay(500)
        try {
            val response = apiClient.getCampaigns()
            if (response.error != null) {
                emit(DataState.Error(IOException(response.error)))
            } else {
                val campaigns = responseMapper.mapFromList(response.data!!)
                campaignDao.insert(modelMapper.mapToList(campaigns))
                val cachedCampaigns = campaignDao.getByChannelName(channel)
                emit(DataState.Success(modelMapper.mapFromList(cachedCampaigns)))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}