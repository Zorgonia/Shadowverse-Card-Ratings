package com.kevin.funratings.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.funratings.R
import com.kevin.funratings.RatingsApplication
import com.kevin.funratings.databinding.FragmentCardDetailBinding
import com.kevin.funratings.home.CardDisplayAdapter
import com.kevin.funratings.main.MainActivity
import com.kevin.funratings.model.FollowerCardDetail

class CardDetailFragment : Fragment() {

    private val chosenLocale: String by lazy {
        requireArguments().getString(LOCALE) ?: "ja"
    }
    private val cardId: String by lazy {
        requireArguments().getString(CARD_ID) ?: "116814010"
    }
    private val clanNum: String by lazy {
        requireArguments().getString(CLAN_NUM) ?: "8"
    }

    private lateinit var binding: FragmentCardDetailBinding
    private val viewModel : CardFragmentViewModel by viewModels { CardFragmentViewModelFactory(cardId, chosenLocale, clanNum, (requireActivity().application as RatingsApplication).repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        binding.rbRatings.setOnRatingBarChangeListener { _, rating, fromUser ->
            if(fromUser) {
                viewModel.insert((rating * 2).toInt())
                Log.d("hello", "${binding.viewModel!!.state().value} hihi ${binding.ivCardPicture.visibility} xd")
                Toast.makeText(context, "rating is $rating", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpObservers() {
        viewModel.cardData().observe(viewLifecycleOwner) {
            Log.d("cool", "${CardDetailHelpers.extractCardInfo(it, chosenLocale)}")
            val data = CardDetailHelpers.extractCardInfo(it, chosenLocale)
            data.cardEffect.effect = data.cardEffect.effect.replace("<br>", "\n")
            data.cardEffect.description = data.cardEffect.description.replace("<br>", "\n")
            if (data is FollowerCardDetail) {
                data.evoCardEffect.effect = data.evoCardEffect.effect.replace("<br>", "\n")
                data.evoCardEffect.description = data.evoCardEffect.description.replace("<br>", "\n")
                binding.followData = data
                binding.rvCardEffects.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = CardEffectAdapter(listOf(data.cardEffect, data.evoCardEffect))
                }
            } else {
                binding.rvCardEffects.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = CardEffectAdapter(listOf(data.cardEffect))
                }
            }
            if (data.relatedCards.isNotEmpty()) {
                binding.rvRelated.apply {
                    layoutManager = GridLayoutManager(context, 3)
                    adapter = CardDisplayAdapter(data.relatedCards,{ data ->
                        (activity as MainActivity).onItemClick(
                            data.id, chosenLocale
                        )}, 3)
                }
            }
            viewModel.parsedData = data
            binding.data = data
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }

        viewModel.cardRating().observe(viewLifecycleOwner) {
            Log.d("lamo", "$it nicer")

            if (it.isNotEmpty()) {
                binding.rbRatings.rating = it.first().rating.toFloat() / 2
            } else {
                binding.rbRatings.rating = 0f
            }
        }
    }

    companion object {
        const val LOCALE = "locale"
        const val CARD_ID = "CARD_ID"
        const val CLAN_NUM = "CLAN_NUM"

        @JvmStatic
        fun newInstance(locale: String, cardId: String, clanNum: String) =
            CardDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(LOCALE, locale)
                    putString(CARD_ID, cardId)
                    putString(CLAN_NUM, clanNum)
                }
            }

    }
}