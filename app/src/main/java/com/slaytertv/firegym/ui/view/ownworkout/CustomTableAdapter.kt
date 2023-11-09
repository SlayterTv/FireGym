package com.slaytertv.firegym.ui.view.ownworkout

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TableRow
import android.widget.TextView
import com.slaytertv.firegym.R
import com.slaytertv.firegym.data.model.DiaEntrenamiento

class CustomTableAdapter(
    private val context: Context,
    private val tableData: Array<Array<DiaEntrenamiento>>,
    private val headerData: Array<String>
) : BaseAdapter() {
    override fun getCount(): Int {
        return tableData.size + 1 // Agrega 1 para la fila de encabezado
    }

    override fun getItem(position: Int): Any {
        return if (position == 0) {
            headerData
        } else {
            tableData[position - 1]
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Aquí debes crear una vista para cada celda en la tabla
        val tableRow = TableRow(context)
        val row = tableData[position]
        val encabezadorow = headerData[position]


        if (row is Array<*>) {
            // Esta es la fila de encabezado
            val headerRow = TableRow(context)
            for (colTitle in row) {
                val textView = TextView(context)
                textView.text = colTitle as? String ?: ""
                textView.gravity = Gravity.CENTER
                // Personaliza la apariencia de los TextView según tus necesidades
                headerRow.addView(textView)
            }
            return headerRow
        } else {
            // Esta es una fila de datos
            // ... (resto del código para mostrar los datos)
        }


        for (diaEntrenamiento in row) {
            val parteCuerpoNombres = diaEntrenamiento.partesCuerpo.map { it.nombre }
            val nombresConcatenados2 = parteCuerpoNombres.joinToString(",").replace(" ", "")

            val elementos = nombresConcatenados2.split(",")
            for (elemento in elementos) {
                val x = elemento.trim().replace(" ", "").lowercase()

                val textView = TextView(context)
                textView.gravity = Gravity.CENTER
                textView.setPadding(8, 8, 8, 8)
                textView.setBackgroundResource(R.drawable.table_cell_background)
                textView.setBackgroundColor(Color.RED)
                textView.text = x

                textView.setOnClickListener {
                    // Abre el diálogo con los ejercicios para el elemento 'x'
                    // openExerciseDialog(x)
                    println("$position")
                 //   viewModelExerciseList.getExercisesByCategory(x)
                }

                val params = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(4, 4, 4, 4)
                textView.layoutParams = params

                tableRow.addView(textView)
            }
        }

        return tableRow
    }
}
