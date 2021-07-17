package com.kevin.funratings.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.funratings.R
import com.kevin.funratings.RatingsApplication
import com.kevin.funratings.databinding.FragmentStatsBinding
import com.kevin.funratings.detail.CardDetailFragment
import com.kevin.funratings.main.MainActivity
import com.kevin.funratings.main.MainViewModel
import com.kevin.funratings.main.MainViewModelFactory

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private val viewModel : StatsViewModel by viewModels { StatsViewModelFactory((requireActivity().application as RatingsApplication).repository) }


    private val chosenLocale: String by lazy {
        requireArguments().getString(CardDetailFragment.LOCALE) ?: "ja"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        binding.rvAllRatings.layoutManager = LinearLayoutManager(context)
    }

    private fun setUpObservers() {
        viewModel.allRatings().observe(viewLifecycleOwner) {
            binding.rvAllRatings.apply {
                adapter = StatsAdapter(it)
            }
        }
    }

    companion object {
        const val LOCALE = "LOCALE"
        @JvmStatic
        fun newInstance(locale: String) =
            StatsFragment().apply {
                arguments = Bundle().apply {
                    putString(LOCALE, locale)
                }
            }
    }
}