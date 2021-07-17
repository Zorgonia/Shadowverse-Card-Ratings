package com.kevin.funratings.stats

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.funratings.databinding.RecyclerHomeCardBinding
import com.kevin.funratings.databinding.RecyclerStatsItemBinding
import com.kevin.funratings.home.CardDisplayAdapter
import com.kevin.funratings.model.HomeCard
import com.kevin.funratings.room.Ratings

class StatsAdapter(
    cards: List<Ratings>
) : RecyclerView.Adapter<StatsAdapter.StatsDisplayViewHolder>() {

    var cards: List<Ratings> = cards
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class StatsDisplayViewHolder(val binding: RecyclerStatsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.rating = cards[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsDisplayViewHolder {
        return StatsDisplayViewHolder(RecyclerStatsItemBinding.inflate(parent.context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: StatsDisplayViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}