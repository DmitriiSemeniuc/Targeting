package com.targetting.ui.target

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.targetting.R
import com.targetting.extensions.setOnClickListenerThrottled
import com.targetting.persistence.models.Target

class TargetAdapter(val data: List<Target>, val listener: OnSelectListener?) :
    RecyclerView.Adapter<TargetAdapter.ViewHolder>() {

    interface OnSelectListener {
        fun onSelect(selected: Boolean)
    }

    private val selected: MutableSet<Int> = mutableSetOf()

    private fun isSelected(position: Int): Boolean {
        return selected.contains(position)
    }

    fun getSelected(): List<Target> {
        return data.filterIndexed { index, _ ->
            selected.contains(index)
        }
    }

    private fun select(position: Int) {
        selected.add(position)
    }

    private fun unSelect(position: Int) {
        selected.remove(position)
    }

    fun reset() {
        selected.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvGroup: TextView = itemView.findViewById(R.id.tvGroup)
        private val ivSelected: ImageView = itemView.findViewById(R.id.ivSelected)
        private val itemLayout: View = itemView.findViewById(R.id.clItem)
        private val cgChannels: ChipGroup = itemView.findViewById(R.id.cgChannels)

        fun bind(item: Target) {
            with(itemView) {
                tvGroup.text = item.group

                val itemSelected = isSelected(adapterPosition)

                val groupTextColor = if (itemSelected) R.color.white else R.color.mineShaft
                val bgColor = if (itemSelected) R.color.cornflowerBlue else R.color.white

                tvGroup.setTextColor(ContextCompat.getColor(this.context, groupTextColor))

                ivSelected.visibility = if (itemSelected) View.VISIBLE else View.GONE

                itemLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context,
                        bgColor
                    )
                )

                cgChannels.removeAllViews()
                item.channels.forEach {
                    val chip = Chip(itemView.context).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.wildSand)
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.mineShaft))
                        isEnabled = false
                        isFocusable = false
                        isClickable = false
                    }
                    cgChannels.addView(chip)
                }

                itemLayout.setOnClickListener {
                    if (isSelected(adapterPosition)) {
                        unSelect(adapterPosition)
                    } else {
                        select(adapterPosition)
                    }
                    listener?.onSelect(selected.toList().isNotEmpty())
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_target, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}