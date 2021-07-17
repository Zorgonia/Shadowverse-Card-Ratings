package com.kevin.funratings.deckimport

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.kevin.funratings.R
import com.kevin.funratings.RatingsApplication
import com.kevin.funratings.databinding.FragmentDeckImportBinding
import com.kevin.funratings.detail.CardDetailFragment

class DeckImportFragment : Fragment() {

    private val chosenLocale: String by lazy {
        requireArguments().getString(LOCALE) ?: "ja"
    }

    private lateinit var binding: FragmentDeckImportBinding
    private val viewModel : DeckImportViewModel by viewModels { DeckImportViewModelFactory((requireActivity().application as RatingsApplication).cardRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deck_import, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpButtons()
    }

    private fun setUpButtons() {
        binding.btnDeckCodeConfirm.setOnClickListener {
            val text = binding.etDeckCodeInput.text.toString()
            if (text.length != 4) {
                binding.tvStatusText.text = resources.getText(R.string.deck_code_length_error)
                return@setOnClickListener
            }
            viewModel.getDeckLink(text)
            viewModel.lastDeckCode = text
        }


    }

    private fun setUpObservers() {
        NetworkDeckParser.locale = chosenLocale
        viewModel.deckImportData().observe(viewLifecycleOwner) { data ->
            binding.lifecycleOwner = viewLifecycleOwner
            if (data == null || !NetworkDeckParser.dataIsValid(data)) {
                toggleVisibility(true)
                binding.tvStatusText.text = resources.getString(R.string.deck_code_error,viewModel.lastDeckCode)
                return@observe
            }
            toggleVisibility(false)
            binding.tvStatusText.text = resources.getString(R.string.deck_code_success, viewModel.lastDeckCode)

            Log.d("database", "hi $data")
            val link = NetworkDeckParser.getDeckLink(data)

            binding.btnCopyDeckLink.setOnClickListener {
                val clipboard: ClipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("label", link))
                Toast.makeText(context, "Copied deck link!", Toast.LENGTH_SHORT).show()
            }

            binding.btnShareDeckLink.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, link)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            binding.btnOpenDeckLink.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(requireContext(), Uri.parse(link))
            }

            binding.btnOpenDeckbuilderLink.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(requireContext(), Uri.parse(NetworkDeckParser.getDeckBuilderLink(data)))
            }
        }
    }

    private fun toggleVisibility(toHide: Boolean) {
        val btns = listOf(binding.btnCopyDeckLink, binding.btnShareDeckLink, binding.btnOpenDeckLink, binding.btnOpenDeckbuilderLink)
        for (btn in btns) {
            btn.visibility = if (toHide) View.INVISIBLE else View.VISIBLE
        }
    }

    companion object {
        const val LOCALE = "locale"

        @JvmStatic
        fun newInstance(locale: String) =
            DeckImportFragment().apply {
                arguments = Bundle().apply {
                    putString(LOCALE, locale)
                }
            }

    }
}