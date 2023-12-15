package com.slaytertv.firegym.ui.view.exerciselist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.slaytertv.firegym.data.model.ExerciseEntity
import com.slaytertv.firegym.databinding.FragmentExerciseBinding
import com.slaytertv.firegym.ui.viewmodel.exerciselist.ExerciseListViewModel
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {
    val TAG :String ="ExerciseFragment"
    lateinit var binding: FragmentExerciseBinding
    private val viewModelExerciseList: ExerciseListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (this::binding.isInitialized){
            binding.root
        }else {
            binding = FragmentExerciseBinding.inflate(layoutInflater)
            binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        recuperardatoscarta()
        binding.savebase.setOnClickListener {
            val cardItem = arguments?.getParcelable<ExerciseEntity>(ExerciseFragment.ARG_CARD_ITEM)
            val setserieTextView: TextView = binding.serie
            val setrepetiTextView: TextView = binding.repeti
            val setpesoTextView: TextView = binding.peso
            if(setserieTextView.text.toString().isNullOrBlank()){
                setserieTextView.requestFocus()
                toast("agregue la cantidad de series a realizar")
                return@setOnClickListener
            }
            if(setrepetiTextView.text.toString().isNullOrBlank()){
                setrepetiTextView.requestFocus()
                toast("agregue la cantidad de repeticiones a realizar")
                return@setOnClickListener
            }
            if(setpesoTextView.text.toString().isNullOrBlank()){
                setpesoTextView.requestFocus()
                toast("agregue el peso base que maneja")
                return@setOnClickListener
            }
            toast("$cardItem ${setrepetiTextView.text.toString()} ${setserieTextView.text.toString()} ${setpesoTextView.text.toString()} ")
            viewModelExerciseList.updateExerciseDatosIni(cardItem!!, setserieTextView.text.toString().toInt() , setrepetiTextView.text.toString().toInt() ,setpesoTextView.text.toString().toDouble())
        }
    }

    private fun recuperardatoscarta() {
        val cardItem = arguments?.getParcelable<ExerciseEntity>(ExerciseFragment.ARG_CARD_ITEM)


        val setName: TextView = binding.textName
        val setCategoTextView: TextView = binding.textCatego
        val setIdTextView: TextView = binding.textId
        val setserieTextView: TextView = binding.serie
        val setrepetiTextView: TextView = binding.repeti
        val setpesoTextView: TextView = binding.peso

        if(cardItem?.foto!=""){
            Glide.with(this).load(cardItem?.foto).into(binding.image)
        }else{Glide.with(this).load("data:image/png;base64,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").into(binding.image)}
        if(cardItem?.foto2!=""){
            Glide.with(this).load(cardItem?.foto2).into(binding.imagedos)
        }else{Glide.with(this).load("https://previews.123rf.com/images/arcady31/arcady311207/arcady31120700083/14643679-no-hay-se%C3%B1ales-c%C3%A1mara-de-fotos.jpg").into(binding.imagedos)}


        setName.text = cardItem!!.name
        setCategoTextView.text = cardItem.category
        setIdTextView.text = cardItem.id.toString()

        setserieTextView.text = cardItem.serie.toString()
        setrepetiTextView.text = cardItem.repeticion.toString()
        setpesoTextView.text = cardItem.peso.toString()


        //recycler progresbar
        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManagerB
        val progressBar = cardItem.bar_data?.let { ExerciseProgressBarAdapter(it) }
        binding.recyclerView.adapter = progressBar
        //recycler list muscle group
        val recyclerViewMuscles = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerViewMuscles.layoutManager = recyclerViewMuscles
        val muscleGroupAdapter = cardItem.muscle_group?.let { MuscleGroupAdapter(it) }
        binding.recyclerViewMuscles.adapter = muscleGroupAdapter
        //recycler list spec group
        val recyclerViewSpecs = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclerViewSpecgroup.layoutManager = recyclerViewSpecs
        val muscleSpecAdapter = cardItem.spec_group?.let { SpecsGroupAdapter(it) }
        binding.recyclerViewSpecgroup.adapter = muscleSpecAdapter

    }
    private fun observer(){
        viewModelExerciseList.exerciseupdatedatoiniroom.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Sucess -> {
                    println("se modifico los datos")
                }
                is UiState.Failure -> {
                    println("no se modifico los datos")
                }
                else -> {
                    println("no se Actualizo los datos")
                }
            }
        }
    }

    companion object {
        private const val ARG_CARD_ITEM = "arg_card_item"
        fun newInstance(cardItem: ExerciseEntity): ExerciseFragment {
            val args = Bundle().apply {
                putParcelable(ARG_CARD_ITEM,cardItem)
            }
            val fragment = ExerciseFragment()
            fragment.arguments = args
            return fragment
        }
    }
}