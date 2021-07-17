package com.kevin.funratings.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Ratings::class], version = 1, exportSchema = false)
abstract class RatingsDatabase: RoomDatabase() {

    abstract fun ratingsDao(): RatingsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: RatingsDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RatingsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RatingsDatabase::class.java,
                    "ratings_database"
                ).addCallback(RatingDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

    private class RatingDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.ratingsDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: RatingsDao) {
            // Delete all content here.
            wordDao.deleteAll()
            wordDao.insert(Ratings("100011010", "oglbin", 6, "0"))

/*            // Add sample words.
            var word = Word(word ="Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)

            // TODO: Add your own words!*/
        }
    }

}
