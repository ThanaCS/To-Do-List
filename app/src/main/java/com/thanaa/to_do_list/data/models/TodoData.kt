package com.thanaa.to_do_list.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_table")
data class TodoData(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var title: String,
        var description: String,
        var date: Date = Date(),
        var isCompleted: Boolean
)