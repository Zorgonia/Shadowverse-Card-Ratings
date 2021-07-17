package com.kevin.funratings.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RatingsRepository(private val ratingsDao: RatingsDao) {

    val allRatings: LiveData<List<Ratings>> = ratingsDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(rating: Ratings) {
        ratingsDao.insert(rating)
    }

    @WorkerThread
    fun getRating(id: String): LiveData<List<Ratings>> {
        return ratingsDao.getRating(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        ratingsDao.deleteAll()
    }
}