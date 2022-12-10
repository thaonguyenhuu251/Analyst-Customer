package com.htnguyen.customeranalysis.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity
data class DataCustomer (
    @PrimaryKey
    var idCustomer: Long? = null,
    @ColumnInfo(name = "nameCustomer")
    var nameCustomer: String? = null,
    @ColumnInfo(name = "gender")
    var gender: Boolean? = null,
    @ColumnInfo(name = "married")
    var married: Boolean? = null,
    @ColumnInfo(name = "dependent")
    var dependent: Boolean? = null,
    @ColumnInfo(name = "education")
    var education: Boolean? = null,
    @ColumnInfo(name = "employed")
    var employed: Boolean? = null,

    @ColumnInfo(name = "creditHistory")
    var creditHistory: Float? = null,

    @ColumnInfo(name = "propertyArea")
    var propertyArea: Int? = null,

    @ColumnInfo(name = "totalIncome")
    var totalIncome: Float? = null,

    @ColumnInfo(name = "incomeLog")
    var incomeLog: Float? = null,

    @ColumnInfo(name = "emi")
    var emi: Float? = null,

    @ColumnInfo(name = "balanceIncome")
    var balanceIncome: Float? = null,

    @ColumnInfo(name = "result")
    var result: Int? = null,

    var isClock: Boolean? = false,

    var isPin: Boolean? = false
)


data class CustomerRequest (
    @ColumnInfo(name = "Gender")
    var gender: Int? = 0,
    @ColumnInfo(name = "Married")
    var married: Int? = 0,
    @ColumnInfo(name = "Dependents")
    var dependent: Int? = 0,
    @ColumnInfo(name = "Education")
    var education: Int? = 0,
    @ColumnInfo(name = "Self_Employed")
    var employed: Int? = 0,

    @ColumnInfo(name = "Credit_History")
    var creditHistory: Float? = null,

    @ColumnInfo(name = "Property_Area")
    var propertyArea: Int? = null,

    @ColumnInfo(name = "Total_Income")
    var totalIncome: Float? = null,

    @ColumnInfo(name = "Total_Income_log")
    var incomeLog: Float? = null,

    @ColumnInfo(name = "EMI")
    var emi: Float? = null,

    @ColumnInfo(name = "Balance_Income")
    var balanceIncome: Float? = null
)

data class CustomerResponse (
    @ColumnInfo(name = "result")
    var result: String,
)
