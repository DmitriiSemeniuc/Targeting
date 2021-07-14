package com.targetting.persistence.database

import com.targetting.extensions.toMD5
import com.targetting.persistence.models.Target
import com.targetting.persistence.repository.Mapper
import javax.inject.Inject

class TargetModelMapper
@Inject
constructor() : Mapper<TargetEntity, Target> {

    override fun mapFrom(entity: TargetEntity): Target {
        return Target(
            group = entity.group,
            channels = entity.channels
        )
    }

    override fun mapTo(model: Target): TargetEntity {
        return TargetEntity(
            id = model.group.toMD5(),
            group = model.group,
            channels = model.channels
        )
    }

    fun mapFromList(entities: List<TargetEntity>): List<Target> {
        return entities.map { mapFrom(it) }
    }

    fun mapToList(models: List<Target>): List<TargetEntity> {
        return models.map { mapTo(it) }
    }
}