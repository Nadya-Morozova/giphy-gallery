package com.example.giphyapp.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocklist")
data class BlockedGipHy(
    @ColumnInfo(name = "id_gif") val idGif: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
