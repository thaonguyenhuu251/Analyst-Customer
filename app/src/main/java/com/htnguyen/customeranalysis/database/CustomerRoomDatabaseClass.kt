package com.htnguyen.customeranalysis.database

import android.content.Context
import androidx.room.*
import com.htnguyen.customeranalysis.model.DataCustomer

@Database(entities = [DataCustomer::class], version = 1)
abstract class CustomerRoomDatabaseClass : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    companion object {
        @Volatile
        private var INSTANCE: CustomerRoomDatabaseClass? = null
        fun getDataBase(context: Context): CustomerRoomDatabaseClass {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDataBase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDataBase(context: Context): CustomerRoomDatabaseClass {
            return Room.databaseBuilder(
                context.applicationContext,
                CustomerRoomDatabaseClass::class.java,
                "customer_database"
            ).allowMainThreadQueries().build()
        }
    }
}