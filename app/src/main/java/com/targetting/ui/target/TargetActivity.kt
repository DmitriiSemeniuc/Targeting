package com.targetting.ui.target

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
import com.targetting.databinding.ActivityTargetBinding
import com.targetting.extensions.setOnClickListenerThrottled
import com.targetting.persistence.models.Target
import com.targetting.ui.channel.ChannelActivity
import com.targetting.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetActivity : AppCompatActivity() {

    val viewModel: TargetingViewModel by viewModels()

    private lateinit var binding: ActivityTargetBinding
    private var targetAdapter: TargetAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_target
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeViewModel()
        setOnClickListeners()
        viewModel.getTargets()
    }

    private fun observeViewModel() {
        viewModel.targets.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<List<Target>> -> {
                    setLoadingVisibility(false)
                    displayTargets(dataState.data)
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
        binding.btnNext.setOnClickListenerThrottled {
            onNextButtonPressed()
        }
    }

    private fun setLoadingVisibility(visible: Boolean) {
        binding.pbLoading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun displayTargets(list: List<Target>) {
        targetAdapter = TargetAdapter(list, object : TargetAdapter.OnSelectListener {
            override fun onSelect(selected: Boolean) {
                enableActionButton(selected)
            }
        })
        binding.rvTargets.adapter = targetAdapter
    }

    private fun enableActionButton(enable: Boolean) {
        binding.btnNext.isEnabled = enable
    }

    private fun onNextButtonPressed() {
        val selectedTargets = targetAdapter?.getSelected()
        selectedTargets?.let { targets ->
            navigateToTarget(targets)
            targetAdapter?.reset()
        }
    }

    private fun navigateToTarget(targets: List<Target>) {
        val bundle = Bundle().apply {
            putParcelableArray(
                ChannelActivity.EXTRA_TARGETS,
                targets.toTypedArray()
            )
        }
        ChannelActivity.navigate(this, bundle)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun displayError(error: String?) {
        error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
        }
    }

    companion object {

        fun navigate(context: Context) {
            val activityNavigator = ActivityNavigator(context)
            val destination =
                activityNavigator.createDestination()
                    .setIntent(Intent(context, TargetActivity::class.java))
            activityNavigator.navigate(destination, null, null, null)
        }
    }
}