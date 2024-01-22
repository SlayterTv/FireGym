package com.slaytertv.firegym.ui.view.ownworkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.slaytertv.firegym.MainActivity
import com.slaytertv.firegym.databinding.FragmentMyOwnWorkoutBinding
import com.slaytertv.firegym.ui.viewmodel.ownworkout.MyWorkoutViewModel
import com.slaytertv.firegym.util.toast
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

    private val viewModel: MyWorkoutViewModel by viewModels()
    val adapterentrenamientoList by lazy {
        EntrenamientosListAdapter(
            onItemEdit = { pos, item ->
                println(item)
            },
            onItemElimina = { pos,item ->
                println(item)
            }
        )
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
        observer()
        botones()

        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerentrenamientos.layoutManager = staggeredGridLayoutManagerB
        binding.recyclerentrenamientos.adapter = adapterentrenamientoList

        viewModel.getCalendarioWorkout()
    }

    private fun observer() {
        viewModel.myworkoutalllistroom.observe(viewLifecycleOwner){state->
            if(state.isNullOrEmpty()){
                toast("no hay")
                println(state)
            }else{
                toast("si hay")
                adapterentrenamientoList.updateList(state.toMutableList())
                for(states in state){
                    println(states)
                }
            }
        }
    }

    private fun botones() {
        binding.otro.setOnClickListener {
            dialog("Crea tu propia rutina")
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