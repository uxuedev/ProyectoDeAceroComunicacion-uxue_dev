package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.myapplication.equipos.TeamsField

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos Firebase
        auth = FirebaseAuth.getInstance()

        // Conectamos vistas
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerButton = findViewById<Button>(R.id.btn_register)

        // Botón de REGISTRO
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: ""
                            val db = FirebaseDatabase.getInstance().getReference("Users")
                            db.child(userId).child("email").setValue(email)
                            Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                            // Después del registro exitoso, navegar a TeamsField
                            navigateToTeamsField()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Correo inválido o contraseña menor a 6 caracteres", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de LOGIN
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                            // Después del login exitoso, navegar a TeamsField
                            navigateToTeamsField()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToTeamsField() {
        val intent = Intent(this, TeamsField::class.java)
        startActivity(intent)
        finish() // Cerrar MainActivity para que no se pueda volver atrás
    }
}