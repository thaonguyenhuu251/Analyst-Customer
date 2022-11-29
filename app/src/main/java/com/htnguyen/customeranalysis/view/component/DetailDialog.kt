package com.htnguyen.customeranalysis.view.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.databinding.DetailDialogBinding
import com.htnguyen.customeranalysis.databinding.LayoutBottomsheetSortBinding
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.ultils.FileUtils

class DetailDialog : DialogFragment() {
    private lateinit var binding: DetailDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialog)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments?.getBundle(Constants.THEMES)!!

        val id = arg.getLong(Constants.CUSTOMER_ID, System.currentTimeMillis())
        val nameCustomer = arg.getString(Constants.CUSTOMER_NAME)
        val gender = arg.getBoolean(Constants.CUSTOMER_GENDER, false)
        val married = arg.getBoolean(Constants.CUSTOMER_MARRIED, false)
        val dependent = arg.getBoolean(Constants.CUSTOMER_DEPENDENT, false)
        val education = arg.getBoolean(Constants.CUSTOMER_EDUCATION, false)
        val employed = arg.getBoolean(Constants.CUSTOMER_EMPLOYED, false)
        val creditHistory = arg.getFloat(Constants.CUSTOMER_CREDIT, 0F)
        val propertyArea = arg.getInt(Constants.CUSTOMER_PROPERTY, 0)
        val totalIncome = arg.getFloat(Constants.CUSTOMER_TOTAL_INCOME, 0F)
        val incomeLog = arg.getFloat(Constants.CUSTOMER_INCOME_LOG, 0F)
        val emi = arg.getFloat(Constants.CUSTOMER_EMI, 0F)
        val balanceIncome = arg.getFloat(Constants.CUSTOMER_BALANCE_INCOME, 0F)
        val result = arg.getInt(Constants.CUSTOMER_RESULT, 0)

        binding.txtName.text = nameCustomer
        binding.txtGender.text = if (gender) getString(R.string.male) else getString(R.string.female)
        binding.txtMarried.text = if (married) getString(R.string.yes) else getString(R.string.no)
        binding.txtDepedent.text = if (dependent) getString(R.string.yes) else getString(R.string.no)

        binding.txtEducation.text = if (education) getString(R.string.graduate) else getString(R.string.not)
        binding.txtEmployed.text = if (employed) getString(R.string.yes) else getString(R.string.no)

        binding.txtCredit.text = creditHistory.toString()
        binding.txtProperty.text = propertyArea.toString()
        binding.txtTotalIncome.text = totalIncome.toString()

        binding.txtIncomeLog.text = incomeLog.toString()
        binding.txtEMI.text = emi.toString()
        binding.txtBalance.text = balanceIncome.toString()
    }

    interface OnClickSort {
        fun onClick(sortType: String)
    }

    companion object {
        const val TAG = "DetailDialog"

    }

}