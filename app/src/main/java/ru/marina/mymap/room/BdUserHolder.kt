package ru.marina_w.my_map.room

import android.content.Context
import androidx.room.Room
import java.lang.IllegalStateException

class BdUserHolder {
    private var database: AppDatabase? = null

    fun init(context: Context) {
        if (database!= null) return
        database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "word_database"
        ).build()


    }
    fun getDatabase(): AppDatabase {
        return database ?: throw IllegalStateException("Необходимо инициализировать БД")
    }

    companion object {
        private var INSTANCE: BdUserHolder? = null
        fun getInstance(): BdUserHolder {
            return synchronized(this) {
                val currentInstance = INSTANCE ?: BdUserHolder()
                INSTANCE = currentInstance
                currentInstance
            }
        }
    }
}