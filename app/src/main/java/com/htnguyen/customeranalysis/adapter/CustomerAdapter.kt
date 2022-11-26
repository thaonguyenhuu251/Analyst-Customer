package com.htnguyen.customeranalysis.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.model.DataCustomer
import java.text.SimpleDateFormat

class CustomerAdapter : ListAdapter<DataCustomer, CustomerAdapter.WorkHolder>(DiffCallback()) {
    lateinit var context: Context

    class WorkHolder(view: View) : RecyclerView.ViewHolder(view)

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

    }

    class DiffCallback : DiffUtil.ItemCallback<DataCustomer>() {
        override fun areItemsTheSame(oldItem: DataCustomer, newItem: DataCustomer) =
            oldItem.idCustomer == newItem.idCustomer

        override fun areContentsTheSame(oldItem: DataCustomer, newItem: DataCustomer) =
            oldItem == newItem
    }

}
