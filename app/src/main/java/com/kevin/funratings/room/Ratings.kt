package com.kevin.funratings.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName="rating_table")
data class Ratings(@PrimaryKey @ColumnInfo(name="id") val id: String,
                   val name: String,
                   val rating: Int,
                   val clan: String) {
    /*@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name="word") val word: String) {
}
     */
}