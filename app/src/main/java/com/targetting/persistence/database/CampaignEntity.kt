package com.targetting.persistence.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Campaign")
@Parcelize
data class CampaignEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val channel: String = "",
    val fee: Float = 0f,
    val currency: String = "",
    val details: List<String> = listOf()
) : Parcelable