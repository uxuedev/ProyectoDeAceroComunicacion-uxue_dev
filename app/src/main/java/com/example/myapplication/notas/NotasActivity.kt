package com.example.myapplication.notas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.myapplication.R
import com.example.myapplication.email.MainActivity
import com.example.myapplication.equipos.TeamsField

class NotasActivity : AppCompatActivity() {
    private lateinit var notasAdapter: NotasAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNotas)
        val fabEdit = findViewById<FloatingActionButton>(R.id.fabEdit)

        // Datos de ejemplo
        val notas = listOf(
            Nota("Revisión de presupuesto", "Analizar costos detallados"),
            Nota("Proyecto A", "Plan de trabajo inicial")
        )

        // Ir a la pantalla de notas al hacer click en el icono de notas
        val ichome = findViewById<ImageView>(R.id.ic_home_bottom)
        ichome.setOnClickListener {
            val intent = Intent(this, TeamsField::class.java)
            startActivity(intent)
        }

        // Ir a la pantalla de notas al hacer click en el icono de notas
        val icEmail = findViewById<ImageView>(R.id.ic_email_bottom)
        icEmail.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        notasAdapter = NotasAdapter(notas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = notasAdapter

        fabEdit.setOnClickListener {
            Toast.makeText(this, "Función de editar próximamente", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notas, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                Toast.makeText(this, "Función de agregar nota próximamente", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

// Modelo de nota
data class Nota(val titulo: String, val descripcion: String)