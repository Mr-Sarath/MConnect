package com.app.mconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.mconnect.databinding.ItemComputerBinding
import com.app.mconnect.databinding.ItemHomeBinding
import com.app.mconnect.databinding.ItemKeyboardBinding
import com.app.mconnect.mynetwork.EmployeeResponse
import com.app.mconnect.ui.model.HomeData

class ComputerAdapter(
    private val itemList: List<EmployeeResponse>,
     private val listener: ClickListener,
) :
    RecyclerView.Adapter<ComputerAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: ItemComputerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemComputerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(itemList[position]) {
            holder.binding.tvTitle.text = name
            holder.binding.tvSerial.text = monitorName

            holder.itemView.setOnClickListener {
              listener.onClick(itemList[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

      interface ClickListener {
          fun onClick(employeeResponse: EmployeeResponse)
      }

}