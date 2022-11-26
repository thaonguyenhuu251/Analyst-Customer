package com.htnguyen.customeranalysis.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.adapter.CustomerAdapter
import com.htnguyen.customeranalysis.adapter.CustomerAdapter.DiffCallback
import com.htnguyen.customeranalysis.database.CustomerRoomDatabaseClass
import com.htnguyen.customeranalysis.databinding.FragmentListCurrentBinding
import com.htnguyen.customeranalysis.model.DataCustomer
import com.htnguyen.customeranalysis.view.component.BottomSheetSort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class ListCurrentFragment : Fragment() {

    lateinit var customerAdapter: CustomerAdapter
    private lateinit var binding: FragmentListCurrentBinding
    private var listCustomer = mutableListOf<DataCustomer>()
    private val customerDatabase by lazy { CustomerRoomDatabaseClass.getDataBase(requireContext()).customerDao() }

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
                    binding.tvNumberFile.text = customerList.size.toString() + " Records"
                } else {
                    binding.recyclerview.visibility = View.GONE
                    binding.imgFile.visibility = View.VISIBLE
                }
            }
        }
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

            val txtDelete = holder.itemView.findViewById<TextView>(R.id.txtDelete)
            txtId.text = currentItem.idCustomer.toString()
            txtName.text = currentItem.nameCustomer.toString()
            txtTime.text = SimpleDateFormat(context.getString(R.string.work_day)).format(currentItem.idCustomer)
            holder.itemView.setOnClickListener {

            }

            imageMore.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    linearMore.visibility = View.VISIBLE
                } else {
                    linearMore.visibility = View.GONE
                }
            }

            txtDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    customerDatabase.deleteCustomer(currentItem)
                }
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


