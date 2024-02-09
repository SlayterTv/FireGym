package com.slaytertv.firegym.ui.viewmodel.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.databinding.ItemEntrenamientohomeBinding


class EntrenamientosHomeListAdapter(
    val onItemSeleccion: (Int, CalendarioEntrenamientoEntity) -> Unit
) : RecyclerView.Adapter<EntrenamientosHomeListAdapter.MyViewHolder>() {
    private var list: MutableList<CalendarioEntrenamientoEntity> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntrenamientosHomeListAdapter.MyViewHolder {
        val itemView = ItemEntrenamientohomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: EntrenamientosHomeListAdapter.MyViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.bind(item)
    }

    fun updateList(list: MutableList<CalendarioEntrenamientoEntity>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemEntrenamientohomeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:CalendarioEntrenamientoEntity){
            binding.nomentrenamiento.text = item.nomrutina
            when(item.estado){
                "pendiente" -> {
                    binding.inicloseentrenamiento.setImageResource(R.drawable.baseline_check_box_outline_blank_24)
                    binding.inicloseentrenamiento.setBackgroundColor(Color.TRANSPARENT)
                }
                "actualmente" -> {
                    binding.inicloseentrenamiento.setImageResource(R.drawable.baseline_check_box_24)
                    binding.inicloseentrenamiento.setBackgroundColor(Color.GREEN)
                }
                "finalizado" -> {
                    binding.inicloseentrenamiento.setImageResource(R.drawable.baseline_check_box_outline_blank_24)
                    binding.inicloseentrenamiento.setBackgroundColor(Color.RED)
                }
            }
            binding.inicloseentrenamiento.setOnClickListener{
                onItemSeleccion.invoke(adapterPosition,item)
            }
        }
    }
}