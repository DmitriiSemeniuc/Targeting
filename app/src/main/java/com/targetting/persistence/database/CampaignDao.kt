package com.targetting.persistence.database

import androidx.room.*

@Dao
interface CampaignDao {

    @Query("SELECT * FROM Campaign")
    suspend fun getAll(): List<CampaignEntity>

    @Query("SELECT * FROM Campaign WHERE channel like :channel ORDER BY fee ASC")
    suspend fun getByChannelName(channel: String): List<CampaignEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CampaignEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<CampaignEntity>)

    @Update
    suspend fun update(item: CampaignEntity)

    @Delete
    suspend fun delete(item: CampaignEntity)
}