package com.targetting.injection

import com.targetting.network.ApiClient
import com.targetting.network.response.CampaignResponseMapper
import com.targetting.network.response.TargetResponseMapper
import com.targetting.persistence.database.CampaignDao
import com.targetting.persistence.database.CampaignModelMapper
import com.targetting.persistence.database.TargetDao
import com.targetting.persistence.database.TargetModelMapper
import com.targetting.persistence.repository.CampaignRepository
import com.targetting.persistence.repository.TargetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTargetRepository(
        targetDao: TargetDao,
        apiClient: ApiClient,
        targetModelMapper: TargetModelMapper,
        targetResponseMapper: TargetResponseMapper
    ): TargetRepository {
        return TargetRepository(targetDao, apiClient, targetModelMapper, targetResponseMapper)
    }

    @Singleton
    @Provides
    fun provideCampaignRepository(
        campaignDao: CampaignDao,
        apiClient: ApiClient,
        campaignModelMapper: CampaignModelMapper,
        campaignResponseMapper: CampaignResponseMapper
    ): CampaignRepository {
        return CampaignRepository(
            campaignDao,
            apiClient,
            campaignModelMapper,
            campaignResponseMapper
        )
    }
}