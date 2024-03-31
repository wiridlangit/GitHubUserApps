package com.dicoding.restaurantreview.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 2, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {

        private const val DATABASE_NAME = "favorite_users.db"

        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteDatabase
        }
    }
}
