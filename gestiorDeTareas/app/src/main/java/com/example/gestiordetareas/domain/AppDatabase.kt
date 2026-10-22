package com.example.gestiordetareas.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestiordetareas.data.Tarea
import com.example.gestiordetareas.data.TareasDao

@Database(entities = [Tarea::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun tareaDao(): TareasDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tareas_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}