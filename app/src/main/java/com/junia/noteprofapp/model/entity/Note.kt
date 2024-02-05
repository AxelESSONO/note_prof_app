package com.junia.noteprofapp.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "title")
    var title : String,

    @ColumnInfo(name = "text")
    var text : String
)