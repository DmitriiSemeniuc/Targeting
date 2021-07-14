package com.targetting.persistence.repository

import com.targetting.persistence.database.TargetDao
import com.targetting.network.ApiClient
import com.targetting.network.response.TargetResponseMapper
import com.targetting.persistence.database.TargetModelMapper
import com.targetting.persistence.models.Target
import com.targetting.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class TargetRepository constructor(
    private val targetDao: TargetDao,
    private val apiClient: ApiClient,
    private val modelMapper: TargetModelMapper,
    private val responseMapper: TargetResponseMapper
) {
    suspend fun getTargets(): Flow<DataState<List<Target>>> = flow {
        emit(DataState.Loading)
        delay(500)
        try {
            val response = apiClient.getTargets()
            if (response.error != null) {
                emit(DataState.Error(IOException(response.error)))
            } else {
                val targets = responseMapper.mapFromList(response.data!!)
                targetDao.insert(modelMapper.mapToList(targets))
                val cachedTargets = targetDao.getAll()
                emit(DataState.Success(modelMapper.mapFromList(cachedTargets)))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}