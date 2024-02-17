package com.slaytertv.firegym.ui.view.home.entrena

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.databinding.FragmentHomeExerciseActualBinding
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExerciseActualFragment : Fragment() {
    val TAG:String = "HomeExerciseActualFragment"
    lateinit var binding: FragmentHomeExerciseActualBinding

    private var idxx:Long = 0
    private var partesCEje: List<Ejercicio> = arrayListOf()
    var cardItem: DiaEntrenamiento? = null

    private val adapterHomeentreinipartecuer by lazy {
        HomeExerciseParteCuerAdapter(
            onItemClicked = { pos, item ->
                cargardatosdeeje(item)
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

        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclertodo.layoutManager = staggeredGridLayoutManagerB
        binding.recyclertodo.adapter = adapterHomeentreinipartecuer

        recuperar()
        botones()
    }

    private fun botones() {
        binding.fineje.setOnClickListener {
            toast(cardItem.toString())
        }
    }

    private fun recuperar() {
        val cardItem2 = arguments?.getParcelable<DiaEntrenamiento>(HomeExerciseActualFragment.ARG_CARD_ITEM)
        val idx:Long =arguments?.getString("idx")!!.toLong()
        when {
            cardItem2.toString().isNotBlank() -> {
                cardItem = cardItem2
            }
        }
        when {
            idx.toString().isNotBlank() -> {
                idxx = arguments?.getString("idx")!!.toLong()
            }
        }
        partesCEje = cardItem?.partesCuerpo?.flatMap { it.ejercicios ?: emptyList() } ?: emptyList()
        adapterHomeentreinipartecuer.updateList(partesCEje)
    }
    private fun cargardatosdeeje(item: Ejercicio) {
        binding.cc.text = item.nombre
        binding.ser.text = item.series.toString()
        binding.repe.text = item.repeticiones.toString()
        binding.estado.text = item.estado
        Glide.with(this).load(item.imageneje).transform(
            RoundedCorners(25)
        ).into(binding.imageView4)
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