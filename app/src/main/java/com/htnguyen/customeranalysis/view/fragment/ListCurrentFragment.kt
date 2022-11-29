package com.htnguyen.customeranalysis.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.adapter.CustomerAdapter
import com.htnguyen.customeranalysis.database.CustomerRoomDatabaseClass
import com.htnguyen.customeranalysis.databinding.FragmentListCurrentBinding
import com.htnguyen.customeranalysis.model.DataCustomer
import com.htnguyen.customeranalysis.ultils.Constants
import com.htnguyen.customeranalysis.view.activity.AddDataActivity
import com.htnguyen.customeranalysis.view.component.BottomSheetSort
import com.htnguyen.customeranalysis.view.component.DetailDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ListCurrentFragment : Fragment() {

    lateinit var customerAdapter: CustomerAdapter
    private lateinit var binding: FragmentListCurrentBinding
    private var listCustomer = mutableListOf<DataCustomer>()
    private val customerDatabase by lazy { CustomerRoomDatabaseClass.getDataBase(requireContext()).customerDao() }

    private val editCustomerResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val id =
                    result.data?.getLongExtra(Constants.CUSTOMER_ID, System.currentTimeMillis())
                val nameCustomer = result.data?.getStringExtra(Constants.CUSTOMER_NAME)
                val gender = result.data?.getBooleanExtra(Constants.CUSTOMER_GENDER, false)
                val married = result.data?.getBooleanExtra(Constants.CUSTOMER_MARRIED, false)
                val dependent = result.data?.getBooleanExtra(Constants.CUSTOMER_DEPENDENT, false)
                val education = result.data?.getBooleanExtra(Constants.CUSTOMER_EDUCATION, false)
                val employed = result.data?.getBooleanExtra(Constants.CUSTOMER_EMPLOYED, false)


                val creditHistory = result.data?.getFloatExtra(Constants.CUSTOMER_CREDIT, 0F)
                val propertyArea = result.data?.getIntExtra(Constants.CUSTOMER_PROPERTY, 0)
                val totalIncome = result.data?.getFloatExtra(Constants.CUSTOMER_TOTAL_INCOME, 0F)
                val incomeLog = result.data?.getFloatExtra(Constants.CUSTOMER_INCOME_LOG, 0F)
                val emi = result.data?.getFloatExtra(Constants.CUSTOMER_EMI, 0F)
                val balanceIncome =
                    result.data?.getFloatExtra(Constants.CUSTOMER_BALANCE_INCOME, 0F)

                val result = result.data?.getIntExtra(Constants.CUSTOMER_RESULT, 0)

                val editCustomer = DataCustomer(
                    id,
                    nameCustomer,
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
                    balanceIncome,
                    result
                )
                lifecycleScope.launch {
                    customerDatabase.updateCustomer(editCustomer)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeWorks()
        initView()
    }

    private fun initView() {
        binding.tvSort.setOnClickListener {
            val bottomSheetSort = BottomSheetSort()
            bottomSheetSort.show(childFragmentManager, BottomSheetSort.TAG)
        }
    }

    private fun setRecyclerView() {
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerview.setHasFixedSize(true)
        customerAdapter = CustomerAdapter()
        binding.recyclerview.adapter = customerAdapter
    }

    private fun observeWorks() {
        lifecycleScope.launch {
            customerDatabase.getCustomer().collect { customerList ->
                if (customerList.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.imgFile.visibility = View.GONE
                    listCustomer = customerList.toMutableList()
                    customerAdapter.submitList(customerList)
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
                binding.tvNumberFile.text = customerList.size.toString() + " Records"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observeWorks()
    }

    inner class CustomerAdapter : ListAdapter<DataCustomer, CustomerAdapter.WorkHolder>(DiffCustomerCallback()) {
        lateinit var context: Context

        inner class WorkHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            context = parent.context
            return WorkHolder(v)
        }

        @SuppressLint("SimpleDateFormat")
        override fun onBindViewHolder(holder: WorkHolder, position: Int) {
            val currentItem = getItem(position)
            val txtName = holder.itemView.findViewById<TextView>(R.id.txtName)
            val txtId = holder.itemView.findViewById<TextView>(R.id.txtId)
            val txtTime = holder.itemView.findViewById<TextView>(R.id.txtTime)
            val imageMore = holder.itemView.findViewById<CheckBox>(R.id.imageView)
            val linearMore = holder.itemView.findViewById<LinearLayout>(R.id.lnMore)
            val linearName = holder.itemView.findViewById<LinearLayout>(R.id.lnName)
            val txtEdit = holder.itemView.findViewById<TextView>(R.id.txtEdit)

            val txtDelete = holder.itemView.findViewById<TextView>(R.id.txtDelete)
            txtId.text = currentItem.idCustomer.toString()
            txtName.text = currentItem.nameCustomer.toString()
            txtTime.text = SimpleDateFormat(context.getString(R.string.work_day)).format(currentItem.idCustomer)

            imageMore.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    linearMore.visibility = View.VISIBLE
                } else {
                    linearMore.visibility = View.GONE
                }
            }

            txtEdit.setOnClickListener {
                val intent = Intent(requireContext(), AddDataActivity::class.java)
                intent.putExtra(Constants.CUSTOMER_ID, currentItem.idCustomer ?: System.currentTimeMillis())
                intent.putExtra(Constants.CUSTOMER_NAME, currentItem.nameCustomer)
                intent.putExtra(Constants.CUSTOMER_GENDER, currentItem.gender ?: false)
                intent.putExtra(Constants.CUSTOMER_MARRIED, currentItem.married ?: false)
                intent.putExtra(Constants.CUSTOMER_DEPENDENT, currentItem.dependent ?: false)
                intent.putExtra(Constants.CUSTOMER_EMPLOYED, currentItem.employed ?: false)
                intent.putExtra(Constants.CUSTOMER_EDUCATION, currentItem.education ?: false)

                intent.putExtra(Constants.CUSTOMER_CREDIT, currentItem.creditHistory ?: 0F)
                intent.putExtra(Constants.CUSTOMER_PROPERTY, currentItem.propertyArea ?: 0)
                intent.putExtra(Constants.CUSTOMER_TOTAL_INCOME, currentItem.totalIncome ?: 0F)
                intent.putExtra(Constants.CUSTOMER_INCOME_LOG, currentItem.incomeLog ?: 0F)
                intent.putExtra(Constants.CUSTOMER_EMI, currentItem.emi ?: 0F)
                intent.putExtra(Constants.CUSTOMER_BALANCE_INCOME, currentItem.balanceIncome ?: 0F)

                intent.putExtra(Constants.CUSTOMER_RESULT, currentItem.result ?: 0)
                editCustomerResultLauncher.launch(intent)
            }

            txtDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    customerDatabase.deleteCustomer(currentItem)
                }
            }

            linearMore.setOnClickListener {
                linearMore.visibility = View.GONE
            }



            linearName.setOnClickListener {
                linearMore.visibility = View.GONE
                val detailDialog = DetailDialog()

                val data = Bundle(1)
                data.putLong(Constants.CUSTOMER_ID, currentItem.idCustomer ?: System.currentTimeMillis())
                data.putString(Constants.CUSTOMER_NAME, currentItem.nameCustomer)
                data.putBoolean(Constants.CUSTOMER_GENDER, currentItem.gender ?: false)
                data.putBoolean(Constants.CUSTOMER_MARRIED, currentItem.married ?: false)
                data.putBoolean(Constants.CUSTOMER_DEPENDENT, currentItem.dependent ?: false)
                data.putBoolean(Constants.CUSTOMER_EMPLOYED, currentItem.employed ?: false)
                data.putBoolean(Constants.CUSTOMER_EDUCATION, currentItem.education ?: false)

                data.putFloat(Constants.CUSTOMER_CREDIT, currentItem.creditHistory ?: 0F)
                data.putInt(Constants.CUSTOMER_PROPERTY, currentItem.propertyArea ?: 0)
                data.putFloat(Constants.CUSTOMER_TOTAL_INCOME, currentItem.totalIncome ?: 0F)
                data.putFloat(Constants.CUSTOMER_INCOME_LOG, currentItem.incomeLog ?: 0F)
                data.putFloat(Constants.CUSTOMER_EMI, currentItem.emi ?: 0F)
                data.putFloat(Constants.CUSTOMER_BALANCE_INCOME, currentItem.balanceIncome ?: 0F)

                data.putInt(Constants.CUSTOMER_RESULT, currentItem.result ?: 0)
                data.putBundle(Constants.THEMES, data)
                detailDialog.arguments = data

                detailDialog.show(childFragmentManager, DetailDialog.TAG)
            }

        }
    }

    class DiffCustomerCallback : DiffUtil.ItemCallback<DataCustomer>() {
        override fun areItemsTheSame(oldItem: DataCustomer, newItem: DataCustomer) =
            oldItem.idCustomer == newItem.idCustomer

        override fun areContentsTheSame(oldItem: DataCustomer, newItem: DataCustomer) =
            oldItem == newItem
    }
}


