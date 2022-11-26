package com.htnguyen.customeranalysis.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.htnguyen.customeranalysis.R
import com.htnguyen.customeranalysis.ultils.FileUtils
import com.htnguyen.customeranalysis.ultils.PreferencesSettings


class LanguageAdapter : ListAdapter<String, LanguageAdapter.LanguageHolder>(DiffCallback()) {
    lateinit var context: Context

    class LanguageHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        context = parent.context
        return LanguageHolder(v)
    }

    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        val currentItem = getItem(position)
        val txtLanguage = holder.itemView.findViewById<TextView>(R.id.txtLanguage)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imgLanguage)
        val radioButton = holder.itemView.findViewById<RadioButton>(R.id.rbLanguage)

        txtLanguage.text = FileUtils.getStringLanguage(currentItem)

        Glide.with(context)
            .load("")
            .error(AppCompatResources.getDrawable(context, FileUtils.getLanguages(currentItem)))
            .into(imageView)

        radioButton.isChecked = PreferencesSettings.getLanguage(context) == currentItem

        radioButton.setOnCheckedChangeListener { compoundButton, isCheck ->
            if (isCheck) {
                PreferencesSettings.setLanguage(context, currentItem)
                notifyItemRangeChanged(0, 16)
            }
        }

        holder.itemView.setOnClickListener {
            PreferencesSettings.setLanguage(context, currentItem)
            notifyItemRangeChanged(0, 16)
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }
}

