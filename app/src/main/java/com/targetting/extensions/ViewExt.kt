package com.targetting.extensions

import android.view.View
import com.targetting.utils.OnClickListenerThrottled

fun View.setOnClickListenerThrottled(block: () -> Unit) {
    this.setOnClickListener(OnClickListenerThrottled(block))
}