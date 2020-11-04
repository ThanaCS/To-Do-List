package com.thanaa.to_do_list.data.repository

import androidx.lifecycle.LiveData
import com.thanaa.to_do_list.data.TodoDao
import com.thanaa.to_do_list.data.models.TodoData

class TodoRepository(private val todoDao: TodoDao) {
    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()
    val sortByNewDate: LiveData<List<TodoData>> = todoDao.sortByNewDate()
    val sortByOldDate: LiveData<List<TodoData>> = todoDao.sortByOldDate()
    val sortByTitle: LiveData<List<TodoData>> = todoDao.sortByTitle()
    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }

    suspend fun updateData(todoData: TodoData) {
        todoDao.updateDate(todoData)
    }

    suspend fun deleteItem(todoData: TodoData) {
        todoDao.deleteItem(todoData)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }
}