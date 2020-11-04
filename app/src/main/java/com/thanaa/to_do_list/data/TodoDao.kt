package com.thanaa.to_do_list.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thanaa.to_do_list.data.models.TodoData

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todoData: TodoData)

    @Update
    suspend fun updateDate(todoData: TodoData)

    @Delete
    suspend fun deleteItem(todoData: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_table ORDER BY date DESC")
    fun sortByNewDate(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY date ASC")
    fun sortByOldDate(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY title ASC")
    fun sortByTitle(): LiveData<List<TodoData>>

}