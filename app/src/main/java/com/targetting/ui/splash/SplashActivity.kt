package com.targetting.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.targetting.R
import com.targetting.databinding.ActivitySplashBinding
import com.targetting.ui.target.TargetActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeViewModel()
        viewModel.goToTargetDelayed()
    }

    private fun observeViewModel() {
        viewModel.goToTarget.observe(this, {
            navigateToTarget()
        })
    }

    private fun navigateToTarget() {
        TargetActivity.navigate(this)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}