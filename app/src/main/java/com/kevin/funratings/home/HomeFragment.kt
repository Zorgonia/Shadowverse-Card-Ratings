package com.kevin.funratings.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kevin.funratings.R
import com.kevin.funratings.databinding.FragmentHomeBinding
import com.kevin.funratings.main.MainActivity

class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentHomeBinding
    private var flags = mutableListOf(true, true)
    private lateinit var viewModel: HomeFragmentViewModel

    private fun setUpObservers() {
        flags = mutableListOf(true, true)
        viewModel.hompageData().observe(viewLifecycleOwner) {
            binding.spnOptions.apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DataHelpers.parseClasses(it))
                onItemSelectedListener = this@HomeFragment
            }

            binding.spnLocaleOptions.apply {
                adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, DataHelpers.getLocales(it))
                onItemSelectedListener = this@HomeFragment
            }
        }

        viewModel.updateData().observe(viewLifecycleOwner) {
            val cards = DataHelpers.parseCardStrings(DataHelpers.getCardStrings(it))
            Log.d("hello", "$cards")
            binding.rvCardRecycler.apply {
                adapter = CardDisplayAdapter(cards, { data ->
                    (activity as MainActivity).onItemClick(
                        data.id, viewModel.getLocale(), viewModel.clanNum.toString()
                    )
                }, 2)
            }

            (binding.spnOptions.adapter as ArrayAdapter<String>).apply {
                clear()
                addAll(DataHelpers.parseClasses(it))
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)

        binding.rvCardRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        setUpObservers()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("why are we refreshing", "${parent?.id == binding.spnLocaleOptions.id}")
        when (parent?.id) {
            binding.spnOptions.id -> {
                //onitemselected is called when the menu is first loaded so prevent from updating,
                if (flags[0]) {
                    flags[0] = false
                    //if the viewmodel already exists then change the selection to what it is
                    binding.spnOptions.setSelection(viewModel.clanNum)
                    return
                }
                viewModel.changeClanData(position.toString(), position)
            }
            binding.spnLocaleOptions.id -> {
                Log.d("inside locale options", "hi $flags")
                if (flags[1]) {
                    flags[1] = false
                    binding.spnLocaleOptions.setSelection(viewModel.localeNum)
                    return
                }
                viewModel.changeLocale((view as TextView).text.toString(), position)
                (requireActivity() as MainActivity).updateLocale(view.text.toString())
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }

}