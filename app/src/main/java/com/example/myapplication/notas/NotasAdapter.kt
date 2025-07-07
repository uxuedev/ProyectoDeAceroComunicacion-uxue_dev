package com.example.myapplication.notas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R


class NotasAdapter(private val notas: List<Nota>) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.titulo.text = nota.titulo
        holder.descripcion.text = nota.descripcion
    }

    override fun getItemCount() = notas.size

    class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvTituloNota)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcionNota)
        val card: CardView = view.findViewById(R.id.cardNota)
    }
}