package com.targetting.ui.campaign.details

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.targetting.R
import com.targetting.databinding.FragmentCampaignDetailsBinding
import com.targetting.extensions.setOnClickListenerThrottled
import com.targetting.persistence.models.Campaign
import com.targetting.persistence.models.Channel
import com.targetting.utils.IntentHelper
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class CampaignDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: CampaignDetailsViewModel by viewModels()
    private lateinit var binding: FragmentCampaignDetailsBinding
    private val decimalFormatted = DecimalFormat("##0")
    private lateinit var campaign: Campaign
    private lateinit var channel: Channel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val d = dialogInterface as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            with(bottomSheetBehavior) {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
            }
        }

        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
        getExtras()
    }

    private fun getExtras() {
        campaign = arguments?.getParcelable<Campaign>(EXTRA_CAMPAIGN) as Campaign
        channel = arguments?.getParcelable<Channel>(EXTRA_CHANNEL) as Channel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_campaign_details, container, false
        )

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayCampaign(campaign, channel)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSend.setOnClickListenerThrottled {
            activity?.let {
                sendEmail()
            }
        }
        binding.btnClose.setOnClickListenerThrottled {
            dismissAllowingStateLoss()
        }
    }

    private fun sendEmail() {
        val to = "bogus@bogus.com"
        val subject = "${getString(R.string.target)}: ${channel.targets.joinToString(", ")}"
        val message = StringBuilder()
            .append(subject)
            .append(System.lineSeparator())
            .append("${getString(R.string.channel)}: ${channel.name}")
            .append(System.lineSeparator())
            .append(
                "${getString(R.string.monthly_fee)} ${decimalFormatted.format(campaign.fee)} ${
                    campaign.currency
                }"
            )
            .append(System.lineSeparator())
            .append("${getString(R.string.campaign_details)}:")
            .append(System.lineSeparator())
            .append(campaign.details.joinToString(", "))
            .toString()

        IntentHelper.sendEmail(requireContext(), to, subject, message)
    }

    private fun displayCampaign(campaign: Campaign, channel: Channel) {
        setTargets(channel.targets)
        setChannel(channel.name)
        val fee = "${decimalFormatted.format(campaign.fee)} ${campaign.currency}"
        setFee(fee)
        setDetails(campaign.details)
    }

    private fun setTargets(targets: List<String>) {
        binding.tvTargets.text = targets.joinToString(", ")
    }

    private fun setChannel(name: String) {
        binding.tvCampaignName.text = name
    }

    private fun setFee(fee: String) {
        binding.tvCampaignFee.text = fee
    }

    private fun setDetails(details: List<String>) {
        details.forEach {
            val chip = Chip(binding.cgDetails.context)
            chip.text = it
            chip.setChipBackgroundColorResource(R.color.wildSand)
            chip.setTextColor(ContextCompat.getColor(binding.cgDetails.context, R.color.mineShaft))
            binding.cgDetails.addView(chip)
        }
    }

    companion object {

        const val EXTRA_CAMPAIGN = "extra_campaign"
        const val EXTRA_CHANNEL = "extra_channel"
        const val TAG = "CampaignDetailsFragment"

        fun newInstance(campaign: Campaign, channel: Channel): CampaignDetailsBottomSheetFragment {
            val bundle = Bundle().apply {
                putParcelable(EXTRA_CAMPAIGN, campaign)
                putParcelable(EXTRA_CHANNEL, channel)
            }
            return CampaignDetailsBottomSheetFragment().apply {
                this.arguments = bundle
            }
        }
    }
}