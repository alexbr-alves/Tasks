package com.alexprojects.tasks.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.alexprojects.tasks.R
import com.alexprojects.tasks.databinding.RowTaskListBinding
import com.alexprojects.tasks.service.listener.TaskListener
import com.alexprojects.tasks.service.model.TaskModel

class TaskViewHolder(private val itemBinding: RowTaskListBinding, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemBinding.root) {

    /**
     * Atribui valores aos elementos de interface e também eventos
     */
    fun bindData(task: TaskModel) {

        itemBinding.textDescription.text = task.description
        itemBinding.textPriority.text = task.priority.toString()
        itemBinding.textDueDate.text = task.dueDate

        // Eventos
        // itemBinding.textDescription.setOnClickListener { listener.onListClick(task.id) }
        // itemBinding.imageTask.setOnClickListener { }

        itemBinding.textDescription.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_de_tarefa)
                .setMessage(R.string.remover_tarefa)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    // listener.onDeleteClick(task.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()
            true
        }

    }
}