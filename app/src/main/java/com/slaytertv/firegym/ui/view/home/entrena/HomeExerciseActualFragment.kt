package com.slaytertv.firegym.ui.view.home.entrena

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.databinding.FragmentHomeExerciseActualBinding
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExerciseActualFragment : Fragment() {
    val TAG:String = "HomeExerciseActualFragment"
    lateinit var binding: FragmentHomeExerciseActualBinding

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
        recuperar()
    }

    private fun recuperar() {
        val cardItem = arguments?.getParcelable<DiaEntrenamiento>(HomeExerciseActualFragment.ARG_CARD_ITEM)
        val cardIdx = arguments?.getString("idx")
        binding.cc.text = "$cardIdx ${cardItem?.dia} ${cardItem?.estado} // \n ${cardItem}"

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