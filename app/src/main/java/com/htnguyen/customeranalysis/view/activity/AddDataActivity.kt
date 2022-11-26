package com.htnguyen.customeranalysis.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.databinding.ActivityAddDataBinding
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.ultils.FileUtils.hideKeyboard
import java.util.*
class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        initView()
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
                onBackPressed()

            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.do_not_blank), Toast.LENGTH_SHORT).show()
            }
        }
    }
}