package com.thanaa.to_do_list.fragment

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thanaa.to_do_list.data.models.TodoData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
    fun checkIfDatabaseEmpty(todoData: List<TodoData>) {
        emptyDatabase.value = todoData.isEmpty()
    }

    fun verifyDataFormUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())


    }
}