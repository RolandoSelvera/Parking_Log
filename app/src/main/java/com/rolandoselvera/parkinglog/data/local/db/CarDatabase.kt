package com.rolandoselvera.parkinglog.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rolandoselvera.parkinglog.data.models.Car

@Database(entities = [Car::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {

    // La base de datos necesita saber sobre el DAO. Dentro del cuerpo de la clase, declara una
    // función abstracta que muestre el ItemDao. Puedes tener varios DAO.
    abstract fun resultCarDao(): CarDao

    companion object {

        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "parkinglog_db"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                return instance
            }
        }
    }
}