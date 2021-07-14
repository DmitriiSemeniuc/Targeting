package com.targetting.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TargetObjectResponse(

    @SerializedName("group")
    var group: String,

    @SerializedName("channels")
    var channels: List<String>

) : Parcelable