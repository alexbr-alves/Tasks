package com.alexprojects.tasks.service.listener

interface TaskListener {


    fun onListClick(id: Int)

    fun onDeleteClick(id: Int)

    fun onCompleteClick(id: Int)

    fun onUndoClick(id: Int)

}