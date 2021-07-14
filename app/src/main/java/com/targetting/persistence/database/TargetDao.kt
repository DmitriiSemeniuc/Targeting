package com.targetting.persistence.database

import androidx.room.*

@Dao
interface TargetDao {

    @Query("SELECT * FROM Target")
    suspend fun getAll(): List<TargetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TargetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<TargetEntity>)

    @Update
    suspend fun update(item: TargetEntity)

    @Delete
    suspend fun delete(item: TargetEntity)
}