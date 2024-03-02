package com.slaytertv.firegym.ui.view.home.entrena

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.DatosDiarios
import com.slaytertv.firegym.data.model.DiaEntrenamiento
import com.slaytertv.firegym.data.model.Ejercicio
import com.slaytertv.firegym.databinding.FragmentHomeExerciseActualBinding
import com.slaytertv.firegym.ui.viewmodel.home.entrena.HomeExerciseActualViewModel
import com.slaytertv.firegym.util.UiState
import com.slaytertv.firegym.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExerciseActualFragment : Fragment() {
    val TAG:String = "HomeExerciseActualFragment"
    lateinit var binding: FragmentHomeExerciseActualBinding
    private val viewModel: HomeExerciseActualViewModel by viewModels()

    //dialog de cronometro y recuperar datos
    private var cronometro: Chronometer? = null
    private var pesodialog: TextView? = null
    private var repedialog: TextView? = null
    private var dialog: AlertDialog? = null

    //datos q me llegan del anterior fragemtn
    private var idxx:Long = 0
    private var iddiaentre:Int=0
    private var idpartecuerpo:String=""
    private var idejercicio:String=""

    //adaptadores para seleccionar una paret del cuerpo o actualizar el recycler
    private val adapterHomeentreinipartecuer by lazy {
        HomeExerciseParteCuerAdapter(
            onItemClicked = { pos, item ->
                idpartecuerpo=item.tipo
                idejercicio= item.id.toString()
                cargardatosdeeje(item)
            }
        )
    }

    private val adapterHomeentreinirecycler by lazy {
        HomeExerciseParteCRecyclerAdapter(
            onItemClicked = { pos, item ->
                println(iddiaentre)
                println(idpartecuerpo)
                println(idejercicio)
                println(item.series)

                val nuevosDatosDiarios: DatosDiarios = DatosDiarios(item.series,item.repeticiones,item.peso,"finalizado","120.2")
                viewModel.actualizarDatosDiariosDeEjercicio(idxx,idejercicio,item.series-1, nuevosDatosDiarios,iddiaentre)

                abrirDialogoConCronometro(idxx,idejercicio,item.series-1, nuevosDatosDiarios)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeExerciseActualBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staggeredGridLayoutManagerB = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.recyclertodo.layoutManager = staggeredGridLayoutManagerB
        binding.recyclertodo.adapter = adapterHomeentreinipartecuer

        val staggeredGridLayoutManagerR = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.datosdiarios.layoutManager = staggeredGridLayoutManagerR
        binding.datosdiarios.adapter = adapterHomeentreinirecycler

        recuperar()
        botones()
        observer()
    }

    private fun observer() {
        viewModel.actualizarDiaEntrenamiento.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {

                }
                is UiState.Sucess -> {
                    toast("cambiando  a activity")
                }
                is UiState.Failure -> {
                    val errorMessage = state.error
                    println(errorMessage)
                }
                else -> {}
            }
        }
        viewModel.datosDiariosUpdater.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Sucess -> {
                    val nuevosDatosDiarios = state.data
                    viewModel.recuperarDiaEntreEspecifico(idxx,iddiaentre)
                    adapterHomeentreinirecycler.updateList(nuevosDatosDiarios)
                    println("si")
                }
                is UiState.Failure -> {
                    val errorMessage = state.error
                    println(errorMessage)
                }
                else -> {}
            }
        }

        viewModel.diaEntrenamientoActual.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Sucess -> {
                    val nuevosDiaEntrenamiento = state.data
                    val partesCEje = nuevosDiaEntrenamiento.partesCuerpo.flatMap { it.ejercicios ?: emptyList() } ?: emptyList()
                    adapterHomeentreinipartecuer.updateList(partesCEje)
                }
                is UiState.Failure -> {
                    val errorMessage = state.error
                    println(errorMessage)
                }
                else -> {}
            }
        }
    }

    private fun botones() {
        binding.fineje.setOnClickListener {
            //toast(cardItem.toString())
            viewModel.finalizarDiaEntrenamiento(idxx,iddiaentre)
        }
        binding.agregarecycler.setOnClickListener {
            viewModel.agregarDatosDiariosDeEjercicio(idxx, idejercicio,DatosDiarios(10,0,"0.0","pendiente","0.0"),iddiaentre)
        }
        binding.quitarrecycler.setOnClickListener {
            viewModel.quitarDatosDiariosDeEjercicio(idxx, idejercicio,iddiaentre)
        }
    }

    private fun recuperar() {
        val cardItem2 = arguments?.getParcelable<DiaEntrenamiento>(HomeExerciseActualFragment.ARG_CARD_ITEM)
        val idx:Long =arguments?.getString("idx")!!.toLong()
        if (cardItem2.toString().isNotBlank() && idx.toString().isNotBlank()) {
            iddiaentre=cardItem2!!.diaN
            idxx = arguments?.getString("idx")!!.toLong()
            viewModel.recuperarDiaEntreEspecifico(idxx,iddiaentre)
        }
    }
    private fun cargardatosdeeje(item: Ejercicio) {
        binding.cc.text = item.nombre
        binding.ser.text = item.series.toString()
        binding.repe.text = item.repeticiones.toString()
        binding.estado.text = item.estado
        Glide.with(this).load(item.imageneje).transform(
            RoundedCorners(25)
        ).into(binding.imageView4)

        item.datosDiarios.let {datosDiarios ->
            adapterHomeentreinirecycler.updateList(datosDiarios!!)
        }
    }

    private fun abrirDialogoConCronometro(
        idxx: Long,
        idejercicio: String,
        i: Int,
        nuevosDatosDiarios: DatosDiarios
    ) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_cronometro, null)
        cronometro = dialogView.findViewById(R.id.cronometro)
        repedialog = dialogView.findViewById(R.id.repe_et)
        pesodialog = dialogView.findViewById(R.id.peso_et)

        // Otros elementos y configuraciones según tus necesidades

        // Crear el diálogo
        dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Aceptar") { _, _ ->
                detenerCronometroYActualizarDatos(idxx,idejercicio,i, nuevosDatosDiarios)
            }
            .setNegativeButton("Cancelar") { _, _ ->
                // Manejar cancelación si es necesario
            }
            .create()

        // Iniciar el cronómetro
        cronometro?.start()

        // Mostrar el diálogo
        dialog?.show()
    }

    private fun detenerCronometroYActualizarDatos(
        idxx: Long,
        idejercicio: String,
        i: Int,
        nuevosDatosDiarios: DatosDiarios
    ) {
        // Obtener el tiempo del cronómetro si es necesario
        val tiempoTranscurrido = cronometro?.text.toString()
        val repes = repedialog?.text.toString()
        val pesos = pesodialog?.text.toString()

        if(repes.isNotBlank() || pesos.isNotBlank()){
            cronometro?.stop()

            nuevosDatosDiarios.timeacabado = tiempoTranscurrido
            nuevosDatosDiarios.repeticiones = repes.toInt()
            nuevosDatosDiarios.peso = pesos
            nuevosDatosDiarios.estado = "finalizado"
            viewModel.actualizarDatosDiariosDeEjercicio(idxx, idejercicio, i, nuevosDatosDiarios,iddiaentre)
            dialog?.dismiss()
        }else{
            toast("rellene todos os campos")
        }
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