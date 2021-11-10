package com.keixxdd.getip.presentation.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.keixxdd.getip.databinding.ActivityMainBinding
import com.keixxdd.getip.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.GONE

        binding.button.setOnClickListener{
            viewModel.getIp()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.data.collect{ data ->
                when(data){
                    is MainViewModel.GetIpEvent.Success -> {
                        with(binding) {
                            textView.text = data.resultString
                            textView.setTextColor(resources.getColor(android.R.color.tab_indicator_text, theme))
                            textView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            button.text = "Get ip"
                        }
                    }
                    is MainViewModel.GetIpEvent.Error -> {
                        with(binding) {
                            textView.setTextColor(Color.RED)
                            textView.text = data.errorText
                            textView.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            button.text = "Retry"
                        }
                    }
                    is MainViewModel.GetIpEvent.Loading -> {
                        with(binding){
                            progressBar.visibility = View.VISIBLE
                            textView.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }
}
