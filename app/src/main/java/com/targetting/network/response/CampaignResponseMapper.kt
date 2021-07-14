package com.targetting.network.response

import com.targetting.persistence.models.Campaign
import com.targetting.persistence.repository.Mapper
import javax.inject.Inject

class CampaignResponseMapper @Inject constructor() : Mapper<CampaignObjectResponse, Campaign> {

    override fun mapFrom(entity: CampaignObjectResponse): Campaign {
        return Campaign(
            channel = entity.channel,
            fee = entity.fee,
            currency = entity.currency,
            details = entity.details
        )
    }

    override fun mapTo(model: Campaign): CampaignObjectResponse {
        return CampaignObjectResponse(
            channel = model.channel,
            fee = model.fee,
            currency = model.currency,
            details = model.details
        )
    }

    fun mapFromList(entities: List<CampaignObjectResponse>): List<Campaign> {
        return entities.map { mapFrom(it) }
    }

    fun mapToList(models: List<Campaign>): List<CampaignObjectResponse> {
        return models.map { mapTo(it) }
    }
}