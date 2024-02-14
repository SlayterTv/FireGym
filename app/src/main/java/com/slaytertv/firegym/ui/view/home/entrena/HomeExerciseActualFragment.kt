package com.slaytertv.firegym.ui.view.home.entrena

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.databinding.FragmentHomeExerciseActualBinding
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExerciseActualFragment : Fragment() {
    val TAG:String = "HomeExerciseActualFragment"
    lateinit var binding: FragmentHomeExerciseActualBinding

    val adapterHomeentreinipartecuer by lazy {
        HomeExerciseParteCuerAdapter(
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
        binding = FragmentHomeExerciseActualBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
        binding.recyclertodo.layoutManager = staggeredGridLayoutManagerB
        binding.recyclertodo.adapter = adapterHomeentreinipartecuer

        recuperar()
    }

    private fun recuperar() {
        val cardItem = arguments?.getParcelable<DiaEntrenamiento>(HomeExerciseActualFragment.ARG_CARD_ITEM)
        val cardIdx = arguments?.getString("idx")
        binding.cc.text = "$cardIdx ${cardItem?.dia} ${cardItem?.estado} // \n ${cardItem}"



        var partesCEje: MutableList<Ejercicio> = arrayListOf()
        for( cuan in cardItem!!.partesCuerpo){
            println(cuan.ejercicios)
            for (cuanx in cuan.ejercicios!!){
                partesCEje.add(cuanx)
            }
        }

        adapterHomeentreinipartecuer.updateList(partesCEje)

    }

    companion object {
        private const val ARG_CARD_ITEM = "arg_card_item"
        fun newInstance(cardItem: DiaEntrenamiento,idx:String): HomeExerciseActualFragment {
            val args = Bundle().apply {
                putParcelable(ARG_CARD_ITEM,cardItem)
                putString("idx",idx)
            }
            val fragment = HomeExerciseActualFragment()
            fragment.arguments = args
            return fragment
        }
    }

}