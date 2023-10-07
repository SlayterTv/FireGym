package com.slaytertv.firegym.ui.view.exerciselist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.slaytertv.firegym.databinding.FragmentExerciseListBinding
import com.slaytertv.firegym.ui.viewmodel.exerciselist.ExerciseListViewModel
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : Fragment() {
    val TAG :String ="HomeFragment"
    //creamos binding
    lateinit var binding: FragmentExerciseListBinding

    val viewModelExerciseList: ExerciseListViewModel by viewModels()
    val adapterexerciseList by lazy {
        ExerciseListAdapter(
            onItemClicked = { pos, item ->
                toast(item.toString())
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentExerciseListBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botones()
        observer()
        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerejericios.layoutManager = staggeredGridLayoutManagerB
        binding.recyclerejericios.adapter = adapterexerciseList
    }

    private fun observer() {
        viewModelExerciseList.exerciselist.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    //binding.homeProgresbarTop.show()
                }
                is UiState.Failure -> {
                    //binding.homeProgresbarTop.hide()
                    toast(state.error)
                }
                is UiState.Sucess -> {
                    //binding.homeProgresbarTop.hide()
                    adapterexerciseList.updateList(state.data.toMutableList())
                }
            }
        }

    }

    private fun botones() {
        binding.neck.setOnClickListener {
            viewModelExerciseList.getExerciselist("neck")
        }
        binding.cardio.setOnClickListener {
            viewModelExerciseList.getExerciselist("cardio")
        }
    }
}