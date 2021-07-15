package com.targetting.ui.campaign

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
import com.targetting.persistence.models.Campaign
import java.text.DecimalFormat

class CampaignAdapter(
    val data: List<Campaign>,
    val listener: OnSelectListener?
) : RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {

    val decimalFormatted = DecimalFormat("##0")

    interface OnSelectListener {
        fun onSelect(selected: Campaign?)
    }

    private var selected: Int? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val divider: View = itemView.findViewById(R.id.divider)
        private val tvFeeLabel: TextView = itemView.findViewById(R.id.tvFeeLabel)
        private val tvFee: TextView = itemView.findViewById(R.id.tvFee)
        private val itemLayout: View = itemView.findViewById(R.id.clItem)
        private val cgDetails: ChipGroup = itemView.findViewById(R.id.cgDetails)
        private val ivCheck: ImageView = itemView.findViewById(R.id.ivCheck)

        fun bind(item: Campaign) {
            with(itemView) {
                val itemSelected = selected == adapterPosition
                val itemBackgroundColor =
                    if (itemSelected) R.color.cornflowerBlue else R.color.white
                val textColor = if (itemSelected) R.color.white else R.color.mineShaft
                val dividerColor = if (itemSelected) R.color.white_60 else R.color.mineShaft_60

                val fee = "${decimalFormatted.format(item.fee)} ${item.currency}"
                tvFee.text = fee

                itemLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        this.context, itemBackgroundColor
                    )
                )

                tvFeeLabel.setTextColor(ContextCompat.getColor(this.context, textColor))
                tvFee.setTextColor(ContextCompat.getColor(this.context, textColor))
                divider.setBackgroundColor(ContextCompat.getColor(this.context, dividerColor))

                ivCheck.visibility = if (itemSelected) View.VISIBLE else View.INVISIBLE

                cgDetails.removeAllViews()
                item.details.forEach {
                    val chip = Chip(itemView.context).apply {
                        text = it
                        setChipBackgroundColorResource(R.color.wildSand)
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.mineShaft))
                        isEnabled = false
                        isFocusable = false
                        isClickable = false
                    }
                    cgDetails.addView(chip)
                }

                itemLayout.setOnClickListenerThrottled {
                    if (adapterPosition == selected) {
                        selected = null
                        listener?.onSelect(null)
                        notifyItemChanged(adapterPosition)
                    } else {
                        selected = adapterPosition
                        listener?.onSelect(item)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_campaign, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun reset() {
        selected = null
        notifyDataSetChanged()
    }
}