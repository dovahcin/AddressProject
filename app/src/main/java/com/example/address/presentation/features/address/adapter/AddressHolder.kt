package com.example.address.presentation.features.address.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.address.databinding.ItemAddressesBinding
import com.example.address.domain.FullAddressItem

class AddressHolder(private val binding: ItemAddressesBinding) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup) =
            AddressHolder(
                ItemAddressesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    fun bind(address: FullAddressItem) {
        binding.address = address
        binding.executePendingBindings()
    }
}