package com.targetting.persistence.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Target")
@Parcelize
data class TargetEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val group: String = "",
    val channels: List<String> = listOf()
) : Parcelable