package com.targetting.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.targetting.R
import com.targetting.persistence.models.Campaign
import com.targetting.persistence.models.Channel

object IntentHelper {

    fun sendEmail(context: Context, receiver: String, subject: String, message: String) {
        val to = arrayOf(receiver)
        val intent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:")
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, to)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }

        try {
            val chooser = Intent.createChooser(intent, "Send Campaign via email...")
            context.startActivity(chooser)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "No email client found.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

