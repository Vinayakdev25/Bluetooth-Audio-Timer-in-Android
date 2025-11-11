package com.example.bluetoothtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<UsageSession, HistoryAdapter.HistoryVH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<UsageSession>() {
            override fun areItemsTheSame(oldItem: UsageSession, newItem: UsageSession) = oldItem.timestamp == newItem.timestamp
            override fun areContentsTheSame(oldItem: UsageSession, newItem: UsageSession) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryVH(v)
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.bind(getItem(position))
    }

    class HistoryVH(view: View) : RecyclerView.ViewHolder(view) {
        private val dateTv: TextView = view.findViewById(R.id.dateTv)
        private val minsTv: TextView = view.findViewById(R.id.minsTv)
        private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        fun bind(s: UsageSession) {
            dateTv.text = sdf.format(Date(s.timestamp))
            minsTv.text = "${'$'}{s.minutes} mins"
        }
    }
}
