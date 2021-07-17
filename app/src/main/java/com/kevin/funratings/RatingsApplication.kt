package com.kevin.funratings

import android.app.Application
import com.kevin.funratings.api.CardService
import com.kevin.funratings.room.RatingsDatabase
import com.kevin.funratings.room.RatingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RatingsApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { RatingsDatabase.getDatabase(this, applicationScope) }
    val repository by lazy {RatingsRepository(database.ratingsDao())}
    val cardRepository by lazy {CardService.cardRepository}
}
/*
class WordsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}
 */