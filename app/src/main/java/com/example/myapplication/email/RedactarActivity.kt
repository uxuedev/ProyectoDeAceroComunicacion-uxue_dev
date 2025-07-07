package com.example.myapplication.email


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class RedactarActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redactar)

        val para = findViewById<EditText>(R.id.editPara)
        val asunto = findViewById<EditText>(R.id.editAsunto)
        val mensaje = findViewById<EditText>(R.id.editMensaje)
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)

        btnEnviar.setOnClickListener {
            val destinatario = para.text.toString()
            val asuntoTexto = asunto.text.toString()
            val cuerpo = mensaje.text.toString()

            Toast.makeText(this, "Correo enviado a $destinatario", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
