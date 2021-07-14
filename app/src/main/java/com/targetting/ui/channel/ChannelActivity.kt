package com.targetting.ui.channel

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
import com.targetting.databinding.ActivityChannelBinding
import com.targetting.persistence.models.Channel
import com.targetting.persistence.models.Target
import com.targetting.ui.campaign.CampaignActivity
import com.targetting.utils.DataState
import com.targetting.utils.OnClickListenerThrottled
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelActivity : AppCompatActivity() {

    val viewModel: ChannelViewModel by viewModels()

    private lateinit var binding: ActivityChannelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_channel
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        getExtras()
        observeViewModel()
        setOnClickListeners()
    }

    private fun getExtras() {
        intent?.let {
            val targets = it.getParcelableArrayExtra(EXTRA_TARGETS)?.toList() as? List<Target>
            targets?.let {
                viewModel.setTargets(targets)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.channels.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<List<Channel>> -> {
                    setLoadingVisibility(false)
                    displayChannels(dataState.data)
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

    private fun setOnClickListeners() {
        binding.tbChannel.setNavigationOnClickListener(OnClickListenerThrottled({
            onBackPressed()
        }))
    }

    private fun setLoadingVisibility(visible: Boolean) {
        binding.pbLoading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun displayChannels(list: List<Channel>) {
        val channelsAdapter = ChannelsAdapter(list, object : ChannelsAdapter.OnSelectListener {
            override fun onSelect(channel: Channel) {
                navigateToCampaign(channel)
            }
        })
        binding.rvChannels.adapter = channelsAdapter
    }

    private fun navigateToCampaign(channel: Channel) {
        val bundle = Bundle().apply {
            this.putParcelable(CampaignActivity.EXTRA_CHANNEL, channel)
        }
        CampaignActivity.navigate(this, bundle)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun displayError(error: String?) {
        error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
        }
    }

    companion object {

        const val EXTRA_TARGETS = "extra_targets"

        fun navigate(context: Context, args: Bundle) {
            val activityNavigator = ActivityNavigator(context)
            val destination =
                activityNavigator.createDestination()
                    .setIntent(Intent(context, ChannelActivity::class.java))
            activityNavigator.navigate(destination, args, null, null)
        }
    }
}