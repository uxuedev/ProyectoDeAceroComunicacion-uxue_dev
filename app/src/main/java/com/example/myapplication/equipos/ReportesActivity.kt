package com.example.myapplication.equipos

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.LinearLayout
import android.view.LayoutInflater
import com.example.myapplication.R
import org.json.JSONArray

class ReportesActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var reportsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportes)
        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("TeamTasks", MODE_PRIVATE)
        reportsContainer = findViewById(R.id.reports_container)

        val btnBack = findViewById<ImageView>(R.id.iv_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, TeamsField::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // Configurar navegación al repositorio
        val tvRepositorio = findViewById<TextView>(R.id.tv_repositorio)
        tvRepositorio.setOnClickListener {
            val intent = Intent(this, RepositorioActivity::class.java)
            startActivity(intent)
        }
        val teamDetailActivity = findViewById<TextView>(R.id.tv_tareas)
        teamDetailActivity.setOnClickListener {
            val intent = Intent(this, TeamDetailActivity::class.java)
            startActivity(intent)
        }

        //Cargar y mostrar reportes
        loadReports()

    }
    private fun loadReports() {
        val reportsJson = sharedPreferences.getString("reports", "[]")
        try {
            val reportsArray = JSONArray(reportsJson)

            if (reportsArray.length() == 0) {
                // Mostrar mensaje cuando no hay reportes
                val noReportsView = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, null)
                val textView = noReportsView.findViewById<TextView>(android.R.id.text1)
                textView.text = "No hay reportes disponibles"
                textView.setTextColor(resources.getColor(android.R.color.darker_gray))
                reportsContainer.addView(noReportsView)
                return
            }

            // Mostrar reportes en orden inverso (más recientes primero)
            for (i in reportsArray.length() - 1 downTo 0) {
                val reportJson = reportsArray.getJSONObject(i)
                val action = reportJson.getString("action")
                val taskName = reportJson.getString("taskName")
                val timestamp = reportJson.getString("timestamp")
                val details = reportJson.getString("details")

                createReportView(action, taskName, timestamp, details)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createReportView(action: String, taskName: String, timestamp: String, details: String) {
        val reportView = LayoutInflater.from(this).inflate(R.layout.item_report, null)

        val actionView = reportView.findViewById<TextView>(R.id.report_action)
        val taskNameView = reportView.findViewById<TextView>(R.id.report_task_name)
        val timestampView = reportView.findViewById<TextView>(R.id.report_timestamp)
        val detailsView = reportView.findViewById<TextView>(R.id.report_details)

        // Configurar colores según la acción
        val actionColor = when (action) {
            "Creación" -> "#4CAF50" // Verde
            "Modificación" -> "#FF9800" // Naranja
            "Eliminación" -> "#F44336" // Rojo
            else -> "#FFFFFF" // Blanco por defecto
        }

        actionView.text = action
        actionView.setTextColor(android.graphics.Color.parseColor(actionColor))
        taskNameView.text = taskName
        timestampView.text = timestamp
        detailsView.text = details

        reportsContainer.addView(reportView)
    }

}