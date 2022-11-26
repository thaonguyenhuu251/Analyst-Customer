package com.htnguyen.customeranalysis.database

import androidx.room.*
import com.htnguyen.customeranalysis.model.DataCustomer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCustomer(dataCustomer: DataCustomer)

    @Query("select * from dataCustomer")
    fun getCustomer(): Flow<List<DataCustomer>>

    @Delete
    fun deleteCustomer(dataCustomer: DataCustomer)

    @Update
    fun updateCustomer(dataCustomer: DataCustomer)

}