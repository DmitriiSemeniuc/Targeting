package com.targetting.persistence.database

import com.targetting.extensions.toMD5
import com.targetting.persistence.models.Campaign
import com.targetting.persistence.repository.Mapper
import javax.inject.Inject

class CampaignModelMapper
@Inject
constructor() : Mapper<CampaignEntity, Campaign> {

    override fun mapFrom(entity: CampaignEntity): Campaign {
        return Campaign(
            channel = entity.channel,
            fee = entity.fee,
            currency = entity.currency,
            details = entity.details
        )
    }

    override fun mapTo(model: Campaign): CampaignEntity {
        return CampaignEntity(
            id = "${model.channel}${model.fee}".toMD5(),
            channel = model.channel,
            fee = model.fee,
            currency = model.currency,
            details = model.details
        )
    }

    fun mapFromList(entities: List<CampaignEntity>): List<Campaign> {
        return entities.map { mapFrom(it) }
    }

    fun mapToList(models: List<Campaign>): List<CampaignEntity> {
        return models.map { mapTo(it) }
    }
}