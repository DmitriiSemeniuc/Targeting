package com.targetting.ui.campaign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.ActivityNavigator
import com.targetting.R
import com.targetting.databinding.ActivityCampaignBinding
import com.targetting.extensions.fromHtml
import com.targetting.extensions.setOnClickListenerThrottled
import com.targetting.persistence.models.Campaign
import com.targetting.persistence.models.Channel
import com.targetting.ui.campaign.details.CampaignDetailsBottomSheetFragment
import com.targetting.utils.DataState
import com.targetting.utils.OnClickListenerThrottled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignActivity : AppCompatActivity() {

    private val viewModel: CampaignViewModel by viewModels()

    private lateinit var binding: ActivityCampaignBinding
    private var channel: Channel? = null
    private var campaignAdapter: CampaignAdapter? = null
    private var campaign: Campaign? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_campaign
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        getExtras()
        channel?.let {
            setChannelName(it.name)
            viewModel.getCampaigns(it)
        }
        observeViewModel()
        setOnClickListener()
    }

    private fun getExtras() {
        intent?.let {
            it.getParcelableExtra<Channel>(EXTRA_CHANNEL)?.let { channel ->
                this.channel = channel
            }
        }
    }

    private fun observeViewModel() {
        viewModel.campaigns.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<List<Campaign>> -> {
                    setLoadingVisibility(false)
                    displayCampaigns(dataState.data)
                }
                is DataState.Loading -> {
                    setLoadingVisibility(true)
                }
                is DataState.Error -> {
                    setLoadingVisibility(false)
                    displayError(dataState.exception.message)
                }
            }
        })
    }

    private fun setOnClickListener() {
        binding.btnReview.setOnClickListenerThrottled {
            campaign?.let {
                reviewCampaign(it, channel!!)
            }
        }
        binding.tbCampaign.setNavigationOnClickListener(OnClickListenerThrottled({
            onBackPressed()
        }))
    }

    private fun reviewCampaign(campaign: Campaign, channel: Channel) {
        val dialog = CampaignDetailsBottomSheetFragment.newInstance(campaign, channel)
        dialog.show(supportFragmentManager, CampaignDetailsBottomSheetFragment.TAG)
    }

    private fun setLoadingVisibility(visible: Boolean) {
        binding.pbLoading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setChannelName(name: String) {
        val channelName = "${getString(R.string.channel)}: <b>$name</b>"
        binding.tvChannelName.text = channelName.fromHtml()
    }

    private fun displayCampaigns(list: List<Campaign>) {
        campaignAdapter = CampaignAdapter(list, object : CampaignAdapter.OnSelectListener {
            override fun onSelect(selected: Campaign?) {
                onCampaignSelected(selected)
            }
        })
        binding.rvCampaigns.adapter = campaignAdapter
    }

    private fun onCampaignSelected(campaign: Campaign?) {
        this.campaign = campaign
        enableActionButton(campaign != null)
    }

    private fun enableActionButton(enable: Boolean) {
        binding.btnReview.isEnabled = enable
    }

    private fun displayError(error: String?) {
        error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
        }
    }

    companion object {

        const val EXTRA_CHANNEL = "extra_channel"

        fun navigate(context: Context, args: Bundle) {
            val activityNavigator = ActivityNavigator(context)
            val destination =
                activityNavigator.createDestination()
                    .setIntent(Intent(context, CampaignActivity::class.java))
            activityNavigator.navigate(destination, args, null, null)
        }
    }
}