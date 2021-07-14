package com.targetting.persistence.repository

interface Mapper<Entity, Model> {

    fun mapFrom(entity: Entity): Model

    fun mapTo(model: Model): Entity
}