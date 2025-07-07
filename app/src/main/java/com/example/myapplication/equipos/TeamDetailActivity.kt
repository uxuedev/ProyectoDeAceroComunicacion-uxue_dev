package com.example.myapplication.equipos
import androidx.appcompat.app.AlertDialog
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class TeamDetailActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskList: LinearLayout
    private val tasks = mutableListOf<Task>()

    data class Task(
        val name: String,
        val time: String,
        val id: String
    )

    data class Report(
        val action: String,
        val taskName: String,
        val timestamp: String,
        val details: String
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("TeamTasks", MODE_PRIVATE)
        taskList = findViewById(R.id.task_list)

        val btnBack = findViewById<ImageView>(R.id.iv_back)
        btnBack.setOnClickListener {
            val intent = Intent(this, TeamsField::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // Configurar navegación entre pantallas
        val tvReportes = findViewById<TextView>(R.id.tv_reportes)
        val tvRepositorio = findViewById<TextView>(R.id.tv_repositorio)
        val tvTareas = findViewById<TextView>(R.id.tv_tareas)

        tvReportes.setOnClickListener {
            val intent = Intent(this, ReportesActivity::class.java)
            startActivity(intent)
        }

        tvRepositorio.setOnClickListener {
            val intent = Intent(this, RepositorioActivity::class.java)
            startActivity(intent)
        }

        tvTareas.setOnClickListener {
            val intent = Intent(this, TeamDetailActivity::class.java)
            startActivity(intent)
        }

        // Cargar tareas guardadas
        loadTasks()

        // Funcionalidad de tareas
        try {
            val ivAdd = findViewById<ImageView>(R.id.btn_add_task)

            ivAdd.setOnClickListener {
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
                val etTaskName = dialogView.findViewById<EditText>(R.id.et_task_name)
                val etTaskTime = dialogView.findViewById<EditText>(R.id.et_task_time)

                AlertDialog.Builder(this)
                    .setTitle("Nueva tarea")
                    .setView(dialogView)
                    .setPositiveButton("Agregar") { dialog, _ ->
                        val name = etTaskName.text.toString().trim()
                        val time = etTaskTime.text.toString().trim()
                        if (name.isNotEmpty()) {
                            addTask(name, time)
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        } catch (e: Exception) {
            Log.e("TeamDetailActivity", "Error al configurar funcionalidad de tareas: ${e.message}")
            e.printStackTrace()
        }
    }

    private fun addTask(name: String, time: String) {
        val task = Task(name, time, System.currentTimeMillis().toString())
        tasks.add(task)
        createTaskView(task)
        saveTasks()
        // Generar reporte de creación

        val details = if (time.isNotEmpty()) "Hora: 4time" else "Sin hora especificada"
        addReport("Creacion", name, details)
    }

    private fun createTaskView(task: Task) {
        val item = LayoutInflater.from(this).inflate(R.layout.item_task, null)
        val iconView = item.findViewById<ImageView>(R.id.task_icon)
        val nameView = item.findViewById<TextView>(R.id.task_name)
        val timeView = item.findViewById<TextView>(R.id.task_time)
        val editButton = item.findViewById<ImageView>(R.id.task_edit)

        // Usar icono fijo para todas las tareas
        iconView.setImageResource(R.drawable.ic_task)
        nameView.text = task.name
        timeView.text = if (task.time.isNotEmpty()) task.time else "Sin hora especificada"

        // Listener para editar la tarea
        editButton.setOnClickListener {
            showEditTaskDialog(item, task)
        }

        taskList.addView(item)
    }

    private fun showEditTaskDialog(taskItem: android.view.View, task: Task) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val etTaskName = dialogView.findViewById<EditText>(R.id.et_task_name)
        val etTaskTime = dialogView.findViewById<EditText>(R.id.et_task_time)

        // Prellenar con los valores actuales
        etTaskName.setText(task.name)
        etTaskTime.setText(task.time)

        AlertDialog.Builder(this)
            .setTitle("Editar tarea")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                val name = etTaskName.text.toString().trim()
                val time = etTaskTime.text.toString().trim()
                if (name.isNotEmpty()) {
                    val nameView = taskItem.findViewById<TextView>(R.id.task_name)
                    val timeView = taskItem.findViewById<TextView>(R.id.task_time)

                    val oldName = task.name
                    val oldTime = task.time

                    nameView.text = name
                    timeView.text = if (time.isNotEmpty()) time else "Sin hora especificada"

                    // Actualizar la tarea en la lista
                    val index = tasks.indexOfFirst { it.id == task.id }
                    if (index != -1) {
                        tasks[index] = Task(name, time, task.id)
                        saveTasks()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .setNeutralButton("Eliminar") { dialog, _ ->
                val index = tasks.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    val taskName = task.name
                    tasks.removeAt(index)
                    taskList.removeView(taskItem)
                    saveTasks()

                    //Generar reporte de eliminación
                    addReport("Elimiación", taskName, "Tarea eliminada del equipo")
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun saveTasks() {
        val jsonArray = JSONArray()
        for (task in tasks) {
            val jsonObject = JSONObject()
            jsonObject.put("name", task.name)
            jsonObject.put("time", task.time)
            jsonObject.put("id", task.id)
            jsonArray.put(jsonObject)
        }
        sharedPreferences.edit().putString("tasks", jsonArray.toString()).apply()
    }

    private fun loadTasks() {
        val tasksJson = sharedPreferences.getString("tasks", "[]")
        try {
            val jsonArray = JSONArray(tasksJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val task = Task(
                    jsonObject.getString("name"),
                    jsonObject.getString("time"),
                    jsonObject.getString("id")
                )
                tasks.add(task)
                createTaskView(task)
            }
        } catch (e: Exception) {
            Log.e("TeamDetailActivity", "Error al cargar tareas: ${e.message}")
        }
    }

    private fun addReport(action: String, taskName: String, details: String) {
        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val report = Report(action, taskName, timestamp, details)

        // Obtener reportes existentes
        val reportsJson = sharedPreferences.getString("reports", "[]")
        val reportsArray = JSONArray(reportsJson)

        // Agregar nuevo reporte
        val reportJson = JSONObject()
        reportJson.put("action", report.action)
        reportJson.put("taskName", report.taskName)
        reportJson.put("timestamp", report.timestamp)
        reportJson.put("details", report.details)

        reportsArray.put(reportJson)

        // Guardar reportes (mantener solo los últimos 50)
        if (reportsArray.length() > 50) {
            val newArray = JSONArray()
            for (i in reportsArray.length() - 50 until reportsArray.length()) {
                newArray.put(reportsArray.get(i))
            }
            sharedPreferences.edit().putString("reports", newArray.toString()).apply()
        } else {
            sharedPreferences.edit().putString("reports", reportsArray.toString()).apply()
        }
    }
}