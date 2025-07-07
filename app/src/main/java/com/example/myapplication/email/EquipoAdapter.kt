package com.example.myapplication.email

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


class EquipoAdapter(
    private val actividad: Activity,
    private val equipos: List<Equipo>
) : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {

    class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        val mensajeTextView: TextView = itemView.findViewById(R.id.mensajeTextView)
        val editImageView: ImageView = itemView.findViewById(R.id.editImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_equipo, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = equipos[position]
        holder.iconImageView.setImageResource(equipo.iconResId)
        holder.nombreTextView.text = equipo.nombre
        holder.mensajeTextView.text = equipo.mensaje

        holder.editImageView.setOnClickListener {
            val intent = Intent(actividad, EditarEquipoActivity::class.java).apply {
                putExtra("nombre", equipo.nombre)
                putExtra("mensaje", equipo.mensaje)
                putExtra("position", position)
            }
            actividad.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int = equipos.size
}