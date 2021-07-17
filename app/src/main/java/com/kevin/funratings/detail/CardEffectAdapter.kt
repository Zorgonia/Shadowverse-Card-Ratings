package com.kevin.funratings.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.funratings.databinding.RecyclerCardContentBinding
import com.kevin.funratings.model.CardEffect

class CardEffectAdapter(
    cards: List<CardEffect>
) : RecyclerView.Adapter<CardEffectAdapter.CardEffectViewHolder>() {

    var cards: List<CardEffect> = cards
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class CardEffectViewHolder(val binding: RecyclerCardContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.cardEffect = cards[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEffectViewHolder {
        return CardEffectViewHolder(RecyclerCardContentBinding.inflate(parent.context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CardEffectViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}