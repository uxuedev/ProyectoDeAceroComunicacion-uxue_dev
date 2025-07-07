package com.example.myapplication.equipos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.os.NetworkOnMainThreadException
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.util.Log
import com.example.myapplication.R

import com.example.myapplication.notas.NotasActivity
import com.example.myapplication.email.MainActivity


class TeamsField : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.field_teams)
        Log.d("MainActivity", "onCreate iniciado")


        // Ir a la pantalla de notas al hacer click en el icono de notas
        val icNotas = findViewById<ImageView>(R.id.ic_write_bottom)
        icNotas.setOnClickListener {
            val intent = Intent(this, NotasActivity::class.java)
            startActivity(intent)
        }

        // Ir a la pantalla de notas al hacer click en el icono de notas
        val icEmail = findViewById<ImageView>(R.id.ic_email_bottom)
        icEmail.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }




        // Listener para el botón "+" (agregar equipo)
        val ivAdd = findViewById<ImageView>(R.id.iv_add)
        val teamList = findViewById<LinearLayout>(R.id.team_list)
        ivAdd.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_team, null)
            val etTeamName = dialogView.findViewById<EditText>(R.id.et_team_name)
            val iconUsert = dialogView.findViewById<ImageView>(R.id.icon_usert)
            val iconCheck = dialogView.findViewById<ImageView>(R.id.icon_check)
            val iconFlag = dialogView.findViewById<ImageView>(R.id.icon_flag)

            var selectedIcon = R.drawable.ic_usert
            iconUsert.alpha = 1.0f
            iconCheck.alpha = 0.5f
            iconFlag.alpha = 0.5f

            iconUsert.setOnClickListener {
                selectedIcon = R.drawable.ic_usert
                iconUsert.alpha = 1.0f
                iconCheck.alpha = 0.5f
                iconFlag.alpha = 0.5f
            }
            iconCheck.setOnClickListener {
                selectedIcon = R.drawable.ic_check
                iconUsert.alpha = 0.5f
                iconCheck.alpha = 1.0f
                iconFlag.alpha = 0.5f
            }
            iconFlag.setOnClickListener {
                selectedIcon = R.drawable.ic_flag
                iconUsert.alpha = 0.5f
                iconCheck.alpha = 0.5f
                iconFlag.alpha = 1.0f
            }


            AlertDialog.Builder(this)
                .setTitle("Nuevo equipo")
                .setView(dialogView)
                .setPositiveButton("Agregar") { dialog, _ ->
                    val name = etTeamName.text.toString().trim()
                    if (name.isNotEmpty()) {
                        // Crear la vista del equipo dinámicamente con el diseño de tarjeta
                        val item = LayoutInflater.from(this).inflate(R.layout.item_team, null)
                        val iconView = item.findViewById<ImageView>(R.id.team_icon)
                        val nameView = item.findViewById<TextView>(R.id.team_name)
                        iconView.setImageResource(selectedIcon)
                        nameView.text = name

                        // Agregar espaciado adicional al equipo
                        val layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(16, 8, 16, 8)
                        item.layoutParams = layoutParams


                        teamList.addView(item)
                        item.setOnClickListener {
                            Log.d("MainActivity", "Equipo dinámico clickeado, abriendo TeamDetailActivity")
                            val intent = Intent(this, TeamDetailActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}