package com.targetting.network

import com.targetting.network.response.CampaignObjectResponse
import com.targetting.network.response.Response
import com.targetting.network.response.TargetObjectResponse
import retrofit2.http.GET

interface ApiClient {

    companion object {

        private const val GET_TARGETS_URL = "187a9339-15eb-4b88-9e73-8e504793eeba"
        private const val GET_CAMPAIGNS = "d1f00d79-827b-46ed-8170-684e54ed82cb"
    }

    @GET(GET_TARGETS_URL)
    suspend fun getTargets(): Response<List<TargetObjectResponse>>

    @GET(GET_CAMPAIGNS)
    suspend fun getCampaigns(): Response<List<CampaignObjectResponse>>
}