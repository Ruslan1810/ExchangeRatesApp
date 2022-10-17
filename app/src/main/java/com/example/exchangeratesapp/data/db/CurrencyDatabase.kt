package com.example.exchangeratesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.exchangeratesapp.data.db.model.CurrencyDB


@Database(entities = [CurrencyDB::class], version = 2)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun dao(): CurrencyDao


    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null
        private const val DB_NAME = "currency_database"

        private val MIGRATION_1_2: Migration = object : Migration(2, 1) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Employee ADD COLUMN favorite INTEGER DEFAULT 0 NOT NULL")
            }
        }

        @Synchronized
        fun getInstance(context: Context): CurrencyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

