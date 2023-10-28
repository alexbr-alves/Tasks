package com.alexprojects.tasks.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alexprojects.tasks.R
import com.alexprojects.tasks.databinding.ActivityTaskFormBinding
import com.alexprojects.tasks.service.model.PriorityModel
import com.alexprojects.tasks.service.model.TaskModel
import com.alexprojects.tasks.service.repository.TaskRepositorY
import com.alexprojects.tasks.viewmodel.TaskFormViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class TaskFormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: TaskFormViewModel
    private lateinit var binding: ActivityTaskFormBinding
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/mm/yyyy")
    private var listPriority: List<PriorityModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)
        binding = ActivityTaskFormBinding.inflate(layoutInflater)

        // Eventos

        binding.buttonDate.setOnClickListener { handleDate() }
        binding.buttonSave.setOnClickListener { handleSave() }


        viewModel.loadPriorities()


        // Layout
        setContentView(binding.root)

        observe()
    }


    override fun onDateSet(v: DatePicker, year: Int, month: Int, dayofMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayofMonth)

        val dueDate = dateFormat.format(calendar.time)
        binding.buttonDate.text = dueDate
    }

    private fun handleDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayofMonth = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, dayofMonth).show()
    }

    private fun observe() {
        handleSpinner()
    }

    private fun handleSpinner() {
        viewModel.priorityList.observe(this) {
            listPriority = it
            val list = mutableListOf<String>()
            for (item in it) {
                list.add(item.description)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
            binding.spinnerPriority.adapter = adapter
        }
    }

    private fun handleSave() {
        val task = TaskModel().apply {
            this.id = 0
            this.description = binding.editDescription.text.toString()
            var index = binding.spinnerPriority.selectedItemPosition
            this.priority = listPriority[index].id
            this.complete = binding.checkComplete.isChecked

            this.dueDate = binding.buttonDate.text.toString()
        }
        if (task.description != "" && task.dueDate != "") {
            viewModel.save(task)
            finish()
        } else {
            Toast.makeText(this, R.string.task_create_error, Toast.LENGTH_SHORT).show()
        }
    }

}