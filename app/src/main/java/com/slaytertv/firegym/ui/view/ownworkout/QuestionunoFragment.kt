package com.slaytertv.firegym.ui.view.ownworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.slaytertv.firegym.R
import com.slaytertv.firegym.databinding.FragmentQuestionunoBinding

class QuestionunoFragment : Fragment() {
    lateinit var binding: FragmentQuestionunoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestionunoBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botoneNB()
    }

    private fun botoneNB() {
        binding.otro.setOnClickListener {
            findNavController().navigate(R.id.action_questionunoFragment_to_questiondosFragment)
        }
    }

}