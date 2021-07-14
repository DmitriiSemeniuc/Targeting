package com.targetting.extensions

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.toMD5(): String {
    try {
        val digest = MessageDigest.getInstance("MD5")
        digest.update(this.toByteArray())
        val messageDigest = digest.digest()

        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            hexString.append(Integer.toHexString(0xFF and aMessageDigest.toInt()))
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

fun String.fromHtml(): Spanned {
    return when {
        this.isEmpty() -> { // return an empty spannable if the html is null
            SpannableString("")
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            // FROM_HTML_MODE_LEGACY is the behaviour that was used for versions below android N
            // we are using this flag to give a consistent behaviour
            HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        else -> {
            Html.fromHtml(this)
        }
    }
}