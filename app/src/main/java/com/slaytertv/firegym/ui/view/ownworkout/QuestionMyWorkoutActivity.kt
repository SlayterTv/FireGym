package com.slaytertv.firegym.ui.view.ownworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.slaytertv.firegym.R
import com.slaytertv.firegym.databinding.ActivityQuestionMyWorkoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionMyWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionMyWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionMyWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}