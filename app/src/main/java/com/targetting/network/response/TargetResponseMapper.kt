package com.targetting.network.response

import com.targetting.persistence.models.Target
import com.targetting.persistence.repository.Mapper
import javax.inject.Inject

class TargetResponseMapper @Inject constructor() : Mapper<TargetObjectResponse, Target> {

    override fun mapFrom(entity: TargetObjectResponse): Target {
        return Target(
            group = entity.group,
            channels = entity.channels
        )
    }

    override fun mapTo(model: Target): TargetObjectResponse {
        return TargetObjectResponse(
            group = model.group,
            channels = model.channels
        )
    }

    fun mapFromList(entities: List<TargetObjectResponse>): List<Target> {
        return entities.map { mapFrom(it) }
    }

    fun mapToList(models: List<Target>): List<TargetObjectResponse> {
        return models.map { mapTo(it) }
    }
}