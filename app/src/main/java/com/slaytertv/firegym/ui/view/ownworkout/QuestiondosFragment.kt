package com.slaytertv.firegym.ui.view.ownworkout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.databinding.FragmentQuestiondosBinding
import com.slaytertv.firegym.util.SharedPrefConstants
import com.slaytertv.firegym.util.toast

class QuestiondosFragment : Fragment() {
    lateinit var binding: FragmentQuestiondosBinding
    private val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), QuestionMyWorkoutActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuestiondosBinding.inflate(layoutInflater)

        val calendario = arguments?.getParcelable<CalendarioEntrenamientoEntity>("calendario")
        toast(calendario.toString())
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botoneNB()
    }

    private fun botoneNB() {

        binding.otro.setOnClickListener {
            val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val isFirstRun = sharedPreferences.getBoolean(SharedPrefConstants.SLIDER_INICIAL, true)
            if (isFirstRun) {
                sharedPreferences.edit().putBoolean(SharedPrefConstants.SLIDER_INICIAL, false).apply()
            }
            val authIntent = Intent(requireContext(), MainActivity::class.java)
            authLauncher.launch(authIntent)
        }
    }
}