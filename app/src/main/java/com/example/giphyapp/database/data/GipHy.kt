package com.example.giphyapp.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "giphy")
data class GipHy(
    @ColumnInfo(name = "id_gif") val idGif: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
