package com.slaytertv.firegym.ui.view.home.entrena

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.DatosDiarios

import com.slaytertv.firegym.databinding.ItemRecyclerdatosdiariosBinding

class HomeExerciseParteCRecyclerAdapter(
    val onItemClicked: (Int, DatosDiarios) -> Unit
): RecyclerView.Adapter<HomeExerciseParteCRecyclerAdapter.MyViewHolder>(){
    private var list: MutableList<DatosDiarios> = arrayListOf()

    inner class MyViewHolder(val binding: ItemRecyclerdatosdiariosBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:DatosDiarios){
            binding.set.text = item.series.toString()
            binding.repe.setText(item.repeticiones.toString())
            binding.peso.setText(item.peso.toString())
            binding.time.text = item.timeacabado

            binding.estado.setOnClickListener {
                onItemClicked.invoke(adapterPosition,item)
            }

            when(item.estado){
                "pendiente" -> {
                    binding.estado.setColorFilter(Color.RED)
                }
                "finalizado" -> {
                    binding.estado.setColorFilter(Color.GREEN)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemRecyclerdatosdiariosBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(list: List<DatosDiarios>){
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}