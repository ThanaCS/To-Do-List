package com.thanaa.to_do_list.data.repository

import androidx.lifecycle.LiveData
import com.thanaa.to_do_list.data.TodoDao
import com.thanaa.to_do_list.data.models.TodoData

class TodoRepository(private val todoDao: TodoDao) {
    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()
    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: TodoData) {
        todoDao.updateDate(todoData)
    }
}