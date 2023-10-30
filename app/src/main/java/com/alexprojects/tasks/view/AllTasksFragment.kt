package com.alexprojects.tasks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexprojects.tasks.databinding.FragmentAllTasksBinding
import com.alexprojects.tasks.service.listener.TaskListener
import com.alexprojects.tasks.view.adapter.TaskAdapter
import com.alexprojects.tasks.viewmodel.TaskListViewModel

class AllTasksFragment : Fragment() {

    private lateinit var viewModel: TaskListViewModel
    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!
    private val adapter= TaskAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[TaskListViewModel::class.java]
        _binding = FragmentAllTasksBinding.inflate(inflater, container, false)

        binding.recyclerAllTasks.layoutManager = LinearLayoutManager(context)
        binding.recyclerAllTasks.adapter = adapter
        setupListener()
        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.list()
    }

    private fun observe() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.updateTasks(it)
        }
        viewModel.delete.observe(viewLifecycleOwner) {
            if (!it.status()) {
                Toast.makeText(context, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListener() {
        val listener = object : TaskListener {
            override fun onListClick(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onDeleteClick(id: Int) {
                viewModel.delete(id)
            }

            override fun onCompleteClick(id: Int) {
                TODO("Not yet implemented")
            }

            override fun onUndoClick(id: Int) {
                TODO("Not yet implemented")
            }

        }

        adapter.attachListener(listener)
    }
}