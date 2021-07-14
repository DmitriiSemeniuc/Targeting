package com.targetting.utils

import android.os.SystemClock
import android.view.View

class OnClickListenerThrottled(private val block: () -> Unit, val duration: Int = 400) :
    View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < duration) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()

        block()
    }
}