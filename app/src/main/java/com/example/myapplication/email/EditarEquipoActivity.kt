package com.example.myapplication.email

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class EditarEquipoActivity : AppCompatActivity() {

    private lateinit var editNombre: EditText
    private lateinit var editMensaje: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("EditarEquipo", "onCreate ejecutado")
        setContentView(R.layout.activity_editar_equipo)

        editNombre = findViewById(R.id.editNombre)
        editMensaje = findViewById(R.id.editMensaje)
        btnGuardar = findViewById(R.id.btnGuardar)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val mensaje = intent.getStringExtra("mensaje") ?: ""
        val position = intent.getIntExtra("position", -1)

        editNombre.setText(nombre)
        editMensaje.setText(mensaje)

        btnGuardar.setOnClickListener {
            val intent = Intent().apply {
                putExtra("nuevoNombre", editNombre.text.toString())
                putExtra("nuevoMensaje", editMensaje.text.toString())
                putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
