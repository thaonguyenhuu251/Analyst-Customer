package com.htnguyen.customeranalysis.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


abstract class BaseListAdapter<T> : RecyclerView.Adapter<ViewHolder>() {

    private var items: List<T> = ArrayList()

    abstract fun areItemsTheSame(old: T, new: T): Boolean

    open fun areContentsTheSame(old: T, new: T): Boolean = old == new

    /**
     * Layout ID
     */
    abstract fun getLayoutId(position: Int): Int

    /**
     * Inflate layout
     */
    open fun onCreateView(parent: ViewGroup, layout: Int): View {
        val inflater = LayoutInflater.from(parent.context)
        return inflater.inflate(layout, parent, false)
    }

    /**
     * Initialize
     */
    abstract fun onBindViewHolder(holder: ViewHolder, item: T)

    /**
     * Update
     */
    abstract fun onBindViewHolder(holder: ViewHolder, item: T, payload: Any?)

    /**
     * Submit
     */
    fun submit(items: List<T>) {
        /*val diff = DiffResult(this.items, items, ::areItemsTheSame, ::areContentsTheSame)
        this.items = items
        diff.dispatchUpdatesTo(this)*/
    }


    abstract class DataBinding<T, V : ViewDataBinding> : RecyclerView.Adapter<DataBinding.DBViewHolder<V>>() {

        var items: List<T> = ArrayList()
            private set

        abstract fun areItemsTheSame(old: T, new: T): Boolean

        open fun areContentsTheSame(old: T, new: T): Boolean = old == new

        /**
         * Layout ID
         */
        abstract fun getLayoutId(position: Int): Int

        /**
         * Inflate layout
         */
        open fun onCreateView(parent: ViewGroup, layout: Int): V {
            val inflater = LayoutInflater.from(parent.context)
            return DataBindingUtil.inflate(inflater, layout, parent, false)
        }

        /**
         * Initialize
         */
        abstract fun onBindViewHolder(holder: DBViewHolder<V>, item: T)

        /**
         * Update
         */
        abstract fun onBindViewHolder(holder: DBViewHolder<V>, item: T, payload: Any?)

        /**
         * Submit
         */
        fun submit(items: List<T>) {
            /*val diff = DiffResult(this.items, items, ::areItemsTheSame, ::areContentsTheSame)
            this.items = items
            diff.dispatchUpdatesTo(this)*/
        }


        class DBViewHolder<B : ViewDataBinding>(val binding: B) : ViewHolder(binding.root)


        final override fun getItemCount(): Int = items.size

        final override fun getItemViewType(position: Int): Int = getLayoutId(position)

        final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBViewHolder<V> =
            DBViewHolder(onCreateView(parent, viewType))

        final override fun onBindViewHolder(holder: DBViewHolder<V>, position: Int) {
            onBindViewHolder(holder, items[position])
        }

        final override fun onBindViewHolder(holder: DBViewHolder<V>, position: Int, payloads: MutableList<*>) {
            if (payloads.size <= 0) return onBindViewHolder(holder, position)
            onBindViewHolder(holder, items[position], payloads[0])
        }

    }


    final override fun getItemCount(): Int = items.size

    final override fun getItemViewType(position: Int): Int = getLayoutId(position)

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        object : ViewHolder(onCreateView(parent, viewType)) {}

    final override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindViewHolder(holder, items[position])
    }

    final override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<*>) {
        if (payloads.size <= 0) return onBindViewHolder(holder, position)
        onBindViewHolder(holder, items[position], payloads[0])
    }

}