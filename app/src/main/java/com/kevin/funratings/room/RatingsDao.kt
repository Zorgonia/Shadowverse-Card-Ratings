package com.kevin.funratings.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RatingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rating: Ratings)

    @Query("SELECT * FROM rating_table where id = :id")
    fun getRating(id: String): LiveData<List<Ratings>>

    @Query("DELETE FROM rating_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM rating_table")
    fun getAll(): LiveData<List<Ratings>>
/*
@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}
 */
}