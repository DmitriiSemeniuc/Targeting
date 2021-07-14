package com.targetting.persistence.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Campaign(
    val channel: String,
    val fee: Float,
    val currency: String,
    val details: List<String>
) : Parcelable