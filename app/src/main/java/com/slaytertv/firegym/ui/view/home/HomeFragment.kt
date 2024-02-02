package com.slaytertv.firegym.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.slaytertv.firegym.databinding.FragmentHomeBinding
import com.slaytertv.firegym.ui.viewmodel.home.EntrenamientosHomeListAdapter
import com.slaytertv.firegym.ui.viewmodel.home.HomeViewModel
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    val adapterentrenamientoList by lazy {
        EntrenamientosHomeListAdapter(
            onItemSeleccion = { pos, item ->
                println(item)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        botones()
        recyclers()
        viewModel.getCalendarioWorkout()


    }

    private fun recyclers() {
        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerviewhomeentreactual.layoutManager = staggeredGridLayoutManagerB
        binding.recyclerviewhomeentreactual.adapter = adapterentrenamientoList
    }

    private fun botones() {

    }

    private fun observer() {
        viewModel.myworkoutalllistroom.observe(viewLifecycleOwner){state->
            if(state.isNullOrEmpty()){
                toast("no Existen Workouts creados")
                //println(state)
            }else{
                adapterentrenamientoList.updateList(state.toMutableList())
                for(states in state){
                    println(states)
                }
            }
        }
    }
}