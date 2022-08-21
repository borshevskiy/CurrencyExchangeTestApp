package com.borshevskiy.currencyexchangetestapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.borshevskiy.currencyexchangetestapp.databinding.FragmentFavoritesBinding
import com.borshevskiy.currencyexchangetestapp.presentation.adapter.CurrencyAdapter
import com.borshevskiy.currencyexchangetestapp.presentation.adapter.FavCurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint

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
        mainViewModel.favoriteCurrenciesList.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}