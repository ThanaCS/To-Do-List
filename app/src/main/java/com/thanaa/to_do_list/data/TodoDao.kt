package com.thanaa.to_do_list.data

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData():LiveData<List<TodoDao>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todoData: TodoData)
}