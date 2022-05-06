package com.example.address.presentation.features.address.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.address.domain.FullAddressItem

class AddressAdapter(private val items: MutableList<FullAddressItem> = mutableListOf()) :
    RecyclerView.Adapter<AddressHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressHolder =
        AddressHolder.create(parent)


    override fun onBindViewHolder(holder: AddressHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    fun update(address: MutableList<FullAddressItem>) {
        val oldSize = itemCount
        this.items.addAll(address)
        notifyItemRangeInserted(oldSize, address.size)
    }
}