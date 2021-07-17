package com.kevin.funratings.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.funratings.databinding.RecyclerHomeCardBinding
import com.kevin.funratings.model.HomeCard

class CardDisplayAdapter(
    cards: List<HomeCard>,
    val onClick: (HomeCard) -> Unit,
    val rows: Int
) : RecyclerView.Adapter<CardDisplayAdapter.CardDisplayViewHolder>() {

    var cards: List<HomeCard> = cards
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CardDisplayViewHolder(val binding: RecyclerHomeCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.homeCard = cards[position]
            binding.rows = rows
            //need to find a way to fix the text centering issue
            binding.ivImageCard.setOnClickListener { onClick(cards[position]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDisplayViewHolder {
        return CardDisplayViewHolder(RecyclerHomeCardBinding.inflate(parent.context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CardDisplayViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}