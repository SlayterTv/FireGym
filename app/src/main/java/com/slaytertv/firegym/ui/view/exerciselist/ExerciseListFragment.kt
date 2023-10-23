package com.slaytertv.firegym.ui.view.exerciselist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.slaytertv.firegym.R
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
                val bundle = Bundle().apply {
                    putParcelable(ARG_CARD_ITEM, item)
                }
                findNavController().navigate(R.id.action_exerciseListFragment_to_exerciseFragment,bundle)
            }
        )
    }
    val adapterexerciseListCatego by lazy {
        ExerciseListCategoAdapter(
            onItemClicked = { pos, item ->
                llamadoviewmodel(item.tag)
                viewModelExerciseList.getExercisesByCategory(item.tag)
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
        observer()
        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerejericios.layoutManager = staggeredGridLayoutManagerB
        binding.recyclerejericios.adapter = adapterexerciseList

        val staggeredGridLayoutManagerC = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        binding.recyclercatego.layoutManager = staggeredGridLayoutManagerC
        binding.recyclercatego.adapter =adapterexerciseListCatego

    }

    private fun observer() {
        viewModelExerciseList.exerciselistroom.observe(viewLifecycleOwner) { state ->
            if(state.isNotEmpty()){
                adapterexerciseList.updateList(state.toMutableList())
            }else{

            }
        }
    }

    private fun llamadoviewmodel(tipo:String){
        viewModelExerciseList.getExerciselist(tipo)
    }
    companion object {
        private const val ARG_CARD_ITEM = "arg_card_item"
    }
}