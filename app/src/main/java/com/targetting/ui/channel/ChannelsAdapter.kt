package com.targetting.ui.channel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.targetting.R
import com.targetting.extensions.setOnClickListenerThrottled
import com.targetting.persistence.models.Channel

class ChannelsAdapter(
    val data: List<Channel>,
    val listener: OnSelectListener?
) : RecyclerView.Adapter<ChannelsAdapter.ViewHolder>() {

    interface OnSelectListener {
        fun onSelect(channel: Channel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvChannel: TextView = itemView.findViewById(R.id.tvChannel)
        private val cvContainer: View = itemView.findViewById(R.id.cvContainer)
        private val cgTargets: ChipGroup = itemView.findViewById(R.id.cgTargets)
        private val itemLayout: View = itemView.findViewById(R.id.clItem)

        fun bind(item: Channel) {
            with(itemView) {
                tvChannel.text = item.name

                cgTargets.removeAllViews()
                item.targets.forEach {
                    val chip = Chip(itemView.context).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.wildSand)
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.mineShaft))
                        isEnabled = false
                        isClickable = false
                        isFocusable = false
                    }
                    cgTargets.addView(chip)
                }

                itemLayout.setOnClickListenerThrottled {
                    listener?.onSelect(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_channel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}