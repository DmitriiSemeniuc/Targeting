package com.targetting.persistence.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Channel(
    val name: String,
    val targets: List<String>
) : Parcelable