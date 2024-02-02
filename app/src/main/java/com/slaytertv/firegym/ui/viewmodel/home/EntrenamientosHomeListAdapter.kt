package com.slaytertv.firegym.ui.viewmodel.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.databinding.ItemEntrenamientosBinding


class EntrenamientosHomeListAdapter(
    val onItemSeleccion: (Int, CalendarioEntrenamientoEntity) -> Unit
) : RecyclerView.Adapter<EntrenamientosHomeListAdapter.MyViewHolder>() {
    private var list: MutableList<CalendarioEntrenamientoEntity> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntrenamientosHomeListAdapter.MyViewHolder {
        val itemView = ItemEntrenamientosBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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

    inner class MyViewHolder(val binding: ItemEntrenamientosBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item:CalendarioEntrenamientoEntity){
            binding.nomentrenamiento.text = item.nomrutina
            binding.inicloseentrenamiento.setOnClickListener{
                onItemSeleccion.invoke(adapterPosition,item)
            }
        }
    }
}