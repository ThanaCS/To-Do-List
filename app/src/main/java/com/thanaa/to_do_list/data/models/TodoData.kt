package com.thanaa.to_do_list.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "todo_table")
@Parcelize
data class TodoData(
        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var title: String,
        var description: String,
        var date: Date = Date(),
        var isCompleted: Boolean
) : Parcelable