package com.htnguyen.customeranalysis.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.databinding.ActivityAddDataBinding
import com.htnguyen.customeranalysis.interfaces.ApiInterface
import com.htnguyen.customeranalysis.model.CustomerRequest
import com.htnguyen.customeranalysis.model.CustomerResponse
import com.htnguyen.customeranalysis.service.ServiceBuilder
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.ultils.FileUtils.hideKeyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        getData()
        initView()
    }

    private fun getData() {
        val id = intent.getLongExtra(Constants.CUSTOMER_ID, System.currentTimeMillis())
        val nameCustomer = intent.getStringExtra(Constants.CUSTOMER_NAME)
        val gender = intent.getBooleanExtra(Constants.CUSTOMER_GENDER, false)
        val married = intent.getBooleanExtra(Constants.CUSTOMER_MARRIED, false)
        val dependent = intent.getBooleanExtra(Constants.CUSTOMER_DEPENDENT, false)
        val education = intent.getBooleanExtra(Constants.CUSTOMER_EDUCATION, false)
        val employed = intent.getBooleanExtra(Constants.CUSTOMER_EMPLOYED, false)


        val creditHistory = intent.getFloatExtra(Constants.CUSTOMER_CREDIT, 0F)
        val propertyArea = intent.getIntExtra(Constants.CUSTOMER_PROPERTY, 0)
        val totalIncome = intent.getFloatExtra(Constants.CUSTOMER_TOTAL_INCOME, 0F)
        val incomeLog = intent.getFloatExtra(Constants.CUSTOMER_INCOME_LOG, 0F)
        val emi = intent.getFloatExtra(Constants.CUSTOMER_EMI, 0F)
        val balanceIncome = intent.getFloatExtra(Constants.CUSTOMER_BALANCE_INCOME, 0F)

        val result = intent.getIntExtra(Constants.CUSTOMER_RESULT, 0)

        binding.edtInputName.setText(nameCustomer)


        binding.edtProperty.setText(propertyArea.toString())
        binding.edtEmi.setText(emi.toString())
        binding.edtBalance.setText(balanceIncome.toString())
        binding.edtCredit.setText(creditHistory.toString())
        binding.edtTotalIncome.setText(totalIncome.toString())
        binding.edtIncomeLog.setText(incomeLog.toString())
    }

    private fun initView() {
        binding.root.setOnClickListener { hideKeyboard(this) }
        binding.txtBack.setOnClickListener { onBackPressed() }

        binding.imgSave.setOnClickListener {
            try {
                val id = intent.getLongExtra(Constants.CUSTOMER_ID, System.currentTimeMillis())
                val nameCustomer = binding.edtInputName.text.toString()
                var gender: Boolean = true
                var married: Boolean = true
                var dependent: Boolean = true
                var education: Boolean = true
                var employed: Boolean = true

                binding.rgGender.setOnCheckedChangeListener { group, checkedId ->
                    gender = checkedId == R.id.rbMale
                }

                binding.rgMarried.setOnCheckedChangeListener { group, checkedId ->
                    married = checkedId == R.id.rbMarriedYes
                }

                binding.rgDependent.setOnCheckedChangeListener { group, checkedId ->
                    dependent = checkedId == R.id.rbDependentYes
                }

                binding.rgEducation.setOnCheckedChangeListener { group, checkedId ->
                    education = checkedId == R.id.rbEducation
                }

                binding.rgEmploy.setOnCheckedChangeListener { group, checkedId ->
                    employed = checkedId == R.id.rbEmployYes
                }

                val creditHistory = binding.edtCredit.text.toString().toFloat()
                val propertyArea = binding.edtProperty.text.toString().toInt()
                val totalIncome = binding.edtTotalIncome.text.toString().toFloat()
                val incomeLog = binding.edtIncomeLog.text.toString().toFloat()
                val emi = binding.edtEmi.text.toString().toFloat()
                val balanceIncome = binding.edtBalance.text.toString().toFloat()

                val result = 0
                val data = Intent()
                data.putExtra(Constants.CUSTOMER_ID, id)
                data.putExtra(Constants.CUSTOMER_NAME, nameCustomer)
                data.putExtra(Constants.CUSTOMER_GENDER, gender)
                data.putExtra(Constants.CUSTOMER_MARRIED, married)
                data.putExtra(Constants.CUSTOMER_DEPENDENT, dependent)
                data.putExtra(Constants.CUSTOMER_EMPLOYED, employed)
                data.putExtra(Constants.CUSTOMER_EDUCATION, education)

                data.putExtra(Constants.CUSTOMER_CREDIT, creditHistory)
                data.putExtra(Constants.CUSTOMER_PROPERTY, propertyArea)
                data.putExtra(Constants.CUSTOMER_TOTAL_INCOME, totalIncome)
                data.putExtra(Constants.CUSTOMER_INCOME_LOG, incomeLog)
                data.putExtra(Constants.CUSTOMER_EMI, emi)
                data.putExtra(Constants.CUSTOMER_BALANCE_INCOME, balanceIncome)

                data.putExtra(Constants.CUSTOMER_RESULT, result)
                setResult(Activity.RESULT_OK, data)
                binding.edtInputName.setText("")
                binding.edtCredit.setText("")
                binding.edtTotalIncome.setText("")
                binding.edtIncomeLog.setText("")
                binding.edtEmi.setText("")
                binding.edtBalance.setText("")
                binding.edtProperty.setText("")

                val requestModel = CustomerRequest(
                    gender,
                    married,
                    dependent,
                    education,
                    employed,
                    creditHistory,
                    propertyArea,
                    totalIncome,
                    incomeLog,
                    emi,
                    balanceIncome
                )

                val response = ServiceBuilder.buildService(ApiInterface::class.java)
                response.sendReq(requestModel).enqueue(
                    object : Callback<CustomerResponse> {
                        override fun onResponse(
                            call: Call<CustomerResponse>,
                            response: Response<CustomerResponse>
                        ) {
                            Toast.makeText(this@AddDataActivity,response.message().toString(),Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                            Toast.makeText(this@AddDataActivity,t.toString(),Toast.LENGTH_LONG).show()
                        }

                    }
                )

                onBackPressed()

            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.do_not_blank), Toast.LENGTH_SHORT).show()
            }
        }
    }
}