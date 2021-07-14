package com.targetting.persistence.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Target(
    val group: String,
    val channels: List<String>
) : Parcelable