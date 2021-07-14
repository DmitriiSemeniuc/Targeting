package com.targetting.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TargetEntity::class, CampaignEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun targetDao(): TargetDao

    abstract fun campaignDao(): CampaignDao

    companion object {
        const val DATABASE_NAME: String = "marketing_db"
    }
}