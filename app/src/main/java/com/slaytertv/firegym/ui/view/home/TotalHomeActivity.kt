package com.slaytertv.firegym.ui.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.databinding.ActivityTotalHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TotalHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTotalHomeBinding
    private val sharedViewModel by viewModels<MySharedViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTotalHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendarioItem = intent.getParcelableExtra<CalendarioEntrenamientoEntity>("calendarioItem")
        if (calendarioItem != null) {
            println("mando a l fragment $calendarioItem")
            sharedViewModel.calendarioItem = calendarioItem
        }
    }
    class MySharedViewModel : ViewModel() {
        var calendarioItem: CalendarioEntrenamientoEntity? = null
    }
}