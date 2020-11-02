package com.thanaa.to_do_list.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thanaa.to_do_list.data.TodoDatabase
import com.thanaa.to_do_list.data.models.TodoData
import com.thanaa.to_do_list.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(
        application
    ).toDoDao()
    private val repository: TodoRepository
    private val getAllData: LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }

    fun insertData(todoDate: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(todoDate)
        }
    }
}