package com.example.myapplication.equipos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

class RepositorioActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositorio)

        // Configurar el botón de regreso
        val btnBack = findViewById<ImageView>(R.id.iv_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, TeamsField::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        val teamDetailActivity = findViewById<TextView>(R.id.tv_tareas)
        teamDetailActivity.setOnClickListener {
            val intent = Intent(this, TeamDetailActivity::class.java)
            startActivity(intent)
        }

        val tvreportes = findViewById<TextView>(R.id.tv_reportes)
        tvreportes.setOnClickListener {
            val intent = Intent(this, ReportesActivity::class.java)
            startActivity(intent)
        }

    }
}