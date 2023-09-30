package com.slaytertv.firegym

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slaytertv.firegym.databinding.ActivitySliderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SliderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySliderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}