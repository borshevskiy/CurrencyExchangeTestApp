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
import com.borshevskiy.currencyexchangetestapp.databinding.FragmentPopularBinding
import com.borshevskiy.currencyexchangetestapp.presentation.adapter.CurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { CurrencyAdapter(mainViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        binding.rvCurrencyList.adapter = mAdapter
        readDatabase()
        binding.autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                mainViewModel.getCurrencies(parent.getItemAtPosition(position).toString()) }
        binding.filterFab.setOnClickListener {
            findNavController().navigate(PopularFragmentDirections.actionPopularScreenToFilterFragment("POPULAR")) }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readCurrencies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    val namesList = mutableListOf<String>()
                    database.forEach { currency -> namesList.add(currency.name) }
                    binding.autoCompleteTextView.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, namesList))
                    Log.d("TEST", database.toString())
                    mAdapter.submitList(database)
                } else mainViewModel.getCurrencies("")
            }
        }
    }
}