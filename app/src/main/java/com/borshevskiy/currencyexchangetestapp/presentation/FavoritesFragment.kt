package com.borshevskiy.currencyexchangetestapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.borshevskiy.currencyexchangetestapp.R
import com.borshevskiy.currencyexchangetestapp.databinding.FragmentFavoritesBinding
import com.borshevskiy.currencyexchangetestapp.presentation.adapter.FavCurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { FavCurrencyAdapter(mainViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.rvCurrencyList.adapter = mAdapter
        readDatabase()
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                mainViewModel.getFavoriteCurrencies(parent.getItemAtPosition(position).toString(), "") }
        binding.filterFab.setOnClickListener {
            findNavController().navigate(FavoritesFragmentDirections.actionFavoritesScreenToFilterFragment("FAVORITES")) }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readFavoriteCurrencies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    val namesList = mutableListOf<String>()
                    database.forEach { currency -> namesList.add(currency.name) }
                    binding.autoCompleteTextView.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, namesList))
                    mAdapter.submitList(database)
                } else mainViewModel.getCurrencies("")
            }
        }
    }
}