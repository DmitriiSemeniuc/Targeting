package com.targetting.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CampaignObjectResponse(

    @SerializedName("channel")
    var channel: String,

    @SerializedName("fee")
    var fee: Float,

    @SerializedName("currency")
    var currency: String,

    @SerializedName("details")
    var details: List<String>

) : Parcelable