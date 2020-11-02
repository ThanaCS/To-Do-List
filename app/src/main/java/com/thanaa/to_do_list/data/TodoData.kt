package com.thanaa.to_do_list.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val description: String,
    val date:Date,
    val isCompleted:Boolean
)