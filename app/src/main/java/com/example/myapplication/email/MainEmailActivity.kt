package com.example.myapplication.email

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.equipos.TeamsField
import com.example.myapplication.notas.NotasActivity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EquipoAdapter
    private lateinit var equipos: MutableList<Equipo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_main)

        equipos = mutableListOf(
            Equipo("Equipo 1", "Marcela mandó un reporte", android.R.drawable.sym_def_app_icon),
            Equipo("Equipo 2", "Andrés mandó un reporte", android.R.drawable.checkbox_on_background),
            Equipo("Equipo 3", "Carmen mandó un reporte", android.R.drawable.ic_delete)
        )

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EquipoAdapter(this, equipos)
        recyclerView.adapter = adapter

        findViewById<ImageView>(R.id.ic_write_bottom).setOnClickListener {
            startActivity(Intent(this, NotasActivity::class.java))
        }

        findViewById<ImageView>(R.id.ic_home_bottom).setOnClickListener {
            startActivity(Intent(this, TeamsField::class.java))
        }

        findViewById<LinearLayout>(R.id.Redactar).setOnClickListener {
            startActivity(Intent(this, RedactarActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val position = data.getIntExtra("position", -1)
            val nuevoNombre = data.getStringExtra("nuevoNombre") ?: ""
            val nuevoMensaje = data.getStringExtra("nuevoMensaje") ?: ""

            if (position != -1 && position in equipos.indices) {
                val equipoOriginal = equipos[position]
                equipos[position] = equipoOriginal.copy(
                    nombre = nuevoNombre,
                    mensaje = nuevoMensaje
                )
                adapter.notifyItemChanged(position)
            }
        }
    }
}