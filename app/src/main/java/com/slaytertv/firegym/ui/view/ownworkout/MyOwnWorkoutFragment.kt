package com.slaytertv.firegym.ui.view.ownworkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.databinding.FragmentMyOwnWorkoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOwnWorkoutFragment : Fragment() {
    lateinit var binding: FragmentMyOwnWorkoutBinding
    private val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val mainIntent = Intent(requireContext(), MainActivity::class.java)
            startActivity(mainIntent)
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyOwnWorkoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botones()
    }

    private fun botones() {
        binding.otro.setOnClickListener {
       dialog("solo si eres nivel medio,experto o si ya tienes una idea de como preparar una rutina entra aca")

        }
    }
    fun dialog(msg:String){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Mensaje")
            .setMessage(msg)
            /*.setNeutralButton(resources.getString(android.R.string.cancel)) { dialog, which ->
                toast("donde apuntas")
            }*/
            .setNegativeButton("cancelar") { dialog, which ->

            }
            .setPositiveButton("aceptar") { dialog, which ->
                val authIntent = Intent(requireContext(), QuestionMyWorkoutActivity::class.java)
                authLauncher.launch(authIntent)
            }
            .show()
    }

}