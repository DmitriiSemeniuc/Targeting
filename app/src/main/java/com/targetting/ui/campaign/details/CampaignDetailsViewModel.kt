package com.targetting.ui.campaign.details

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.targetting.persistence.models.Campaign
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignDetailsViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

}