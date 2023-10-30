package com.alexprojects.tasks.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.alexprojects.tasks.R
import com.alexprojects.tasks.databinding.RowTaskListBinding
import com.alexprojects.tasks.service.listener.TaskListener
import com.alexprojects.tasks.service.model.TaskModel
import java.text.SimpleDateFormat

class TaskViewHolder(private val itemBinding: RowTaskListBinding, val listener: TaskListener) :
    RecyclerView.ViewHolder(itemBinding.root) {


    fun bindData(task: TaskModel) {
        setupUI(task)
        dialogDeliteTesk(task)
    }

    private fun setupUI(task: TaskModel) = itemBinding.run {
        textDescription.text = task.description
        textPriority.text = task.priorityDescription
        val date = SimpleDateFormat("yyyy-MM-dd").parse(task.dueDate)
        textDueDate.text =  SimpleDateFormat("dd/MM/yyyy").format(date)

        if (task.complete) {
            imageTask.setImageResource(R.drawable.ic_done)
        } else {
            imageTask.setImageResource(R.drawable.ic_todo)
        }

        textDescription.setOnClickListener { listener.onListClick(task.id) }

        imageTask.setOnClickListener {
           if (task.complete) {
               listener.onUndoClick(task.id)
           } else {
               listener.onCompleteClick(task.id)
           }
        }
    }

    private fun dialogDeliteTesk(task: TaskModel) {
        itemBinding.textDescription.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_de_tarefa)
                .setMessage(R.string.remover_tarefa)
                .setPositiveButton(R.string.sim) { dialog, which ->
                    listener.onDeleteClick(task.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()
            true
        }
    }
}