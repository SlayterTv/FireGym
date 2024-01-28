package com.slaytertv.firegym.ui.view.ownworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slaytertv.firegym.data.model.CalendarioEntrenamientoEntity
import com.slaytertv.firegym.databinding.ItemEntrenamientosBinding


class EntrenamientosListAdapter(
    val onItemEdit: (Int, CalendarioEntrenamientoEntity) -> Unit,
    val onItemElimina: (Int, CalendarioEntrenamientoEntity) -> Unit,
    val onItemSeleccion: (Int, CalendarioEntrenamientoEntity) -> Unit
) :RecyclerView.Adapter<EntrenamientosListAdapter.MyViewHolder>() {
    private var list: MutableList<CalendarioEntrenamientoEntity> = arrayListOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = ItemEntrenamientosBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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
            binding.editarentrenamiento.setOnClickListener{
                onItemEdit.invoke(adapterPosition,item)
            }
            binding.eliminarentrenamiento.setOnClickListener{
                onItemElimina.invoke(adapterPosition,item)
            }
            binding.inicloseentrenamiento.setOnClickListener{
                onItemSeleccion.invoke(adapterPosition,item)
            }
            when(item.estado){
                "pendiente" -> {
                    binding.inicloseentrenamiento.text = "Pendiente"
                    binding.inicloseentrenamiento.setBackgroundColor(Color.GRAY)
                }
                "actualmente" -> {
                    binding.inicloseentrenamiento.text = "En proceso"
                    binding.inicloseentrenamiento.setBackgroundColor(Color.GREEN)
                }
                "finalizado" -> {
                    binding.inicloseentrenamiento.text = "Finalizado"
                    binding.inicloseentrenamiento.setBackgroundColor(Color.GRAY)
                }
            }
        }
    }

}