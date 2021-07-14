package com.targetting.persistence.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    @JvmStatic
    fun toTarget(value: String): TargetEntity? {
        return Gson().fromJson(value, TargetEntity::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun fromTarget(target: TargetEntity): String {
        return Gson().toJson(target)
    }

    @TypeConverter
    @JvmStatic
    fun toCampaign(value: String): CampaignEntity? {
        return Gson().fromJson(value, CampaignEntity::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun fromCampaign(campaign: CampaignEntity): String {
        return Gson().toJson(campaign)
    }

    @TypeConverter
    @JvmStatic
    fun toListOfString(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromListOfString(objects: List<String>): String {
        return Gson().toJson(objects)
    }
}